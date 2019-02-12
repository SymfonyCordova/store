# store
javaweb电子商城案例 面向切面编程 利用注解加本地线程方式实现事务管理
```
 用户注册(发送激活邮件/JS前台实现数据校验/验证码)
 用户激活
 用户登陆(记住用户名/30天自动登陆)
 用户注销
 添加商品(文件上传)
 查看商品列表
 查看商品详情
 加入购物车(Cookie *session 数据库)
 增删改查购物车
 生成订单(多表设计)
 订单查询(多表查询)
 订单删除(事务管理/注解+本地线程+动态代理实现事务管理,AOP--面向切面编程)
 在线支付(体验调用第三方接口实现特定功能)
 销售榜单下载(利用程序生成excel数据/文件下载)
 全站乱码过滤器
 权限过滤器
```
# 表结构

```sql
    用户: id 用户名 密码 昵称 邮箱 角色 激活状态 激活码 注册时间
    商品：id 商品名称 商品单价 商品种类 商品库存数量 图片url 描述信息
    订单: id 订单金额 收货地址 支付状态 下单时间 用户编号(外键)
    订单项: 订单id 商品id 购买数量
    
    用户 1 -- * 订单
    商品 * -- * 订单
    
    create database estore;
    用户：
      create user estore identified by 'estore';    
    授权：
      grant all on estore.* to estore;
    use estore;
    create table users (
      id int primary key auto_increment,
      username varchar(40),   # 用户名
      password varchar(100),  # 密码
      nickname varchar(40),   # 昵称
      email varchar(100),     # 邮箱
      role varchar(100) ,     # 角色
      state int ,             # 激活状态
      activecode varchar(100),# 激活码
      updatetime timestamp    # 注册时间
    );
    create table products(
      id varchar(100) primary key ,
      name varchar(40),         # 商品名称
      price double,             # 商品单价
      category varchar(40),     # 商品种类
      pnum int ,                # 商品库存数量
      imgurl varchar(255),      # 图片url
      description varchar(255)  # 描述信息
    );
    create table orders(
      id varchar(100) primary key,
      money double,               # 订单金额
      receiverinfo varchar(255),  # 收货地址
      paystate int,               # 支付状态
      ordertime timestamp,        # 下单时间
      user_id int ,               # 用户编号(外键)
      foreign key(user_id) references users(id)
    );
    create table orderitem(
      order_id varchar(100),
      product_id varchar(100),
      buynum int ,
      primary key(order_id,product_id), #联合主键,两列的值加在一起作为这张表的主键使用
      foreign key(order_id) references orders(id),
      foreign key(product_id) references products(id)
    );
```

# javaee经典三层架构+工厂类实现解耦
```
    包结构:
        com.zler.web
        com.zler.service
        com.zler.service.impl
        com.zler.dao
        com.zler.dao.impl
        com.zler.tool
        com.zler.domain
        com.zler.factory
        com.zler.exception
        com.zler.test
        com.zler.filter
        com.zler.listener
    导入第三方jar包
        *junit
        JSTL
        beanutils
        mysql驱动
        c3po
        dbutils
        commns-fileupload

    配置文件
        c3p0-config.xml
        config.properties
        
    配置虚拟主机:
        在tomcat/conf/server.xml中配置
            <Host name="www.estore.com" appBase="/xxx/xxx/xxx/store">
                <Context path="" docBase="/xxx/xxx/xxx/store/WebRoot" />
            </Host>
        在Hosts文件中配置
            127.0.0.1	www.estore.com
                    	
    前置开发:
        工厂类实现解耦:
        全站乱码解决
        ~权限控制
        javabean
        工具类
```

# 业务

```
1.用户注册
    index.jsp -- 如果用户没有登录,则提示 欢迎光临游客 注册 登录  如果用户已经登录 提示 欢迎回来xxx,注销
    regist.jsp -- 提供注册表单 (验证码/js实现表单校验)
    RegistServlet -- 校验验证码 封装数据校验数据 调用Service注册用户 重定向到主页
    UserService --   注册用户  检验用户名是否已经存在  如果存在则提示 如果不存在则调用dao中添加用户的方法添加用户 发送激活邮件
    UserDao -- 添加客户方法
2.用户激活:用户点击邮箱中的激活链接时触发此Servlet
    ActiveServlet:	获取请求参数中的激活码,调用Service中激活用户的方法,回到主页
    UserSerivce: 激活用户 调用dao根据激活码查找用户 如果找不到用户提示激活码无效 如果用户已经激活过,则提示不要重复激活 检查如果激活码已经超时(24小时)则提示激活码超时要求重新注册此时应该删除此用户记录
    UserDao: 根据激活码查找用户 根据用户id删除用户的方法
3.用户登录(记住用户名 30天内自动登陆)
    index.jsp -- 如果用户没有登录,则提示 欢迎光临游客 注册 登录  如果用户已经登录 提示 欢迎回来xxx,注销
    login.jsp -- 提供用户登录表单输入用户名密码
    LoginServlet -- 1.获取用户名密码,2.调用Service中根据用户名密码查找用户的方法 3.检查用户的激活状态,如果没有激活则提示 4.登录用户重定向到主页
    UserService -- 提供根据用户名 密码查找用户的方法 调用dao中对应方法
    UserDao -- 提供根据用户名密码查找用户的方法
4.用户注销
5.添加商品(文件上传)
    index.jsp -- 提供 添加商品
    addProd.jsp -- 提供添加商品的表单,这个表单应该是文件上传的表单,其中允许上传商品图片
    AddProd -- 实现文件上传,将商品的图片上传到服务器中,并且向数据库的商品表中增加一条记录
    Service -- 添加商品
    Dao -- 添加商品信息的方法	
6.商品列表
    index.jsp -- 提供商品列表
    ProdList -- 调用Service中查询所有商品的方法,查到后存入request域带到页面展示
    prodList.jsp -- 从request域中拿出所有商品做展示
7.查看商品详情
    在商品列表页面中点击图片时,查看商品的详情
    ProdInfoServlet 根据商品id查询商品信息,带到页面显示
    ProdService 提供根据id查询商品的方法
    ProdDao 提供根据id查询商品的方法
8.加入购物车
    在商品的详细信息页面中提供 加入购物车按钮
    AddCartServlet -- cartMap<Product, buynum>
    
```

# 邮件开发
```
1.邮件服务器：
    要在Internet上提供电子邮件功能，必须有专门的电子邮件服务器。
    例如现在Internet很多提供邮件服务的厂商：sina、sohu、163等等他们都有自己的邮件服务器。
    这些服务器类似于现实生活中的邮局，它主要负责接收用户投递过来的邮件，并把邮件投递到邮件接收者的电子邮箱中。
2.电子邮箱：
    电子邮箱（E-mail地址）的获得需要在邮件服务器上进行申请 ，确切地说，电子邮箱其实就是用户在邮件服务器上申请的一个帐户。
    用户在邮件服务器上申请了一个帐号后，邮件服务器就会为这个帐号分配一定的空间，用户从而可以使用这个帐号以及空间，发送电子邮件和保存别人发送过来的电子邮件。
3.邮件传输协议和邮件服务器类型
    SMTP协议(发送邮件)
        用户连上邮件服务器后，要想给它发送一封电子邮件，需要遵循一定的通迅规则，SMTP协议就是用于定义这种通讯规则的。
        因而，通常我们也把处理用户smtp请求（邮件发送请求）的邮件服务器称之为SMTP服务器。(25)
    POP3协议(接收邮件)
        同样，用户若想从邮件服务器管理的电子邮箱中接收一封电子邮件的话，他连上邮件服务器后，也需要遵循一定的通迅格式，
        POP3协议用于定义这种通讯格式。因而，通常我们也把处理用户pop3请求（邮件接收请求）的邮件服务器称之为POP3服务器。(110)
    安装一下EyouMailServer
    演示   
        com_zler@sina.cn zler 
        com_zler@sohu.com zler
        
        一定经服务器上的允许客户端连接打开
        
        ============手动发送邮件==============================
        >telnet localhost 25
        ehlo xxx 
        
        auth login
        
        YWE=
        
        MTIz
        
        mail from:<aa@zler.com>
        
        rcpt to:<bb@zler.com>
        
        Data
        
        from:<aa@zler.com>
        to:<bb@zler.com>
        subject:  测试邮件
        
        xxx 自作主张
        
        .
        
        quit
        
        
        ============手动接收邮件==============================
        >telnet localhost 110
        Trying ::1...
        Connection failed: Connection refused
        Trying 127.0.0.1...
        Connected to localhost.
        Escape character is '^]'.
        +OK ▒▒▒▒▒ʼ▒▒▒▒▒▒▒ 5.2 POP3 Service Ready
        user bb
        +OK welcome here
        pass 123
        +OK
        stat
        +OK 2 679
        list
        +OK 2 679
        1 452
        2 227
        .
        retr 2
        +OK 227 octets
        Return-Path: <aa@zler.com>
        Received: from xxx (unknown [127.0.0.1])
                by zler.com with CMailServer 5.2 SMTP; Sun, 03 Feb 2019 14:31:22 +0800
        from:<aa@zler.com>
        to:<bb@zler.com>
        subject:  测试邮件
        xxx 自▒▒主张
        
        .
        quit
```
