## SQL注入及解决

##1、小结

- ### MVC架构：model   view  controller

  - model：数据模型层(dao层)
  - view：视图层
  - controller：控制层

- ### 包：

  - org.lanqiao.entity
  - org.lanqiao.dao
    - impl
  - org.lanqial.utils
  - org.lanqiao.test

- ### jdbcUtis: 工具类    

  - 方法一般都是静态的：使用类中方法时，无需创建该类对象，可直接通过：类名.  的形式调用该方法

  - 静态只能使用静态

  - 与数据库连接的属性:都定义为静态的

    - dirverClassName：类加载器，放在静态代码块中随着类的加载而加载，无需调用
    - url
    - user
    - password

  - 其中所用静态方法

    - getConnection()：获取连接
    - releaseSource(ResultSet res ,Statement statement ,Connection connection)：释放连接

  - jdbc代码重构：为使数据库使用更加灵活，便于切换数据库及实施，将连接数据库的属性写在配置文件中：xxx.perproties

    - 其配置都是以：键值对的形式存在
    - 键名 = 键值(不需要: 分号、冒号)

  - 对于属性文件的读取加载

    - 首先加载文件，得到一个InputStream 输入流对象，对于文件的读取，都是以IO流进行操作

      ```java
      InputStream in = Utils.class.getClassLoad().getResourceAsStream("属性配置文件名");
      ```

    - 创建 Properties对象

      ```java
      Properties properties = new Properties();
      ```

    - 将IO流加载进来

      ```java
      properties.load(in);
      ```

    - 通过get方法获取健所对应的值

      ```java
      properties.getProperties("属性文件中的键");
      ```

    - 当当

  - 当当

- ### 当当
##2、SQL注入问题及解决

- ### 使用Statement对象存在SQL注入问题，Test类中：

  ```java
  public class Test {
      public static void main(String[] args) throws SQLException {
          Scanner scanner = new Scanner(System.in);
          System.out.println("请输入您的用户名：");
          String username = scanner.nextLine();
          System.out.println("请输入您的密码：");
          String password = scanner.nextLine();
          IUser iUser = new UserDaoImpl();
          User user = iUser.getUserByUsernameAndPassword(username,password);
          if (user != null){
              System.out.println("恭喜您登陆成功");
          }else {
              System.out.println("您的账号或密码有误，请重新登陆");
          }
          scanner.close();
      }
  }
  
  ```

  问题如图所示：

  ![ ](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs\52.png)

  ------

  ![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs\53.png)

- ### 使用PreparedStatement对象解决Sql注入

  UserDaoImpl类

  ```java
  @Override
      public User getUserByUsernameAndPassword(String name, String password) throws SQLException {
          Connection connection = JDBCUtils.getConnection();
          //Statement statement = connection.createStatement();
          //String sql = "select * from stu where name = '"+name+"' and password ='"+password+"'";
          String sql = "select * from stu where name = ? and password = ?";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setString(1,name);
          preparedStatement.setString(2,password);
          ResultSet resultSet = preparedStatement.executeQuery();
          User user = null;
          while (resultSet.next()){
              user =new User();
              user.setId(resultSet.getInt("id"));
              user.setName(resultSet.getString("name"));
              user.setAge(resultSet.getInt("age"));
              user.setPassword(resultSet.getString("password"));
              user.setSex(resultSet.getString("sex"));
          }
          return user;
      }
  ```

- ### 当当

## 2、事务