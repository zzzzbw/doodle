# Doodle

[![Build Status](https://www.travis-ci.org/zzzzbw/doodle.svg?branch=master)](https://www.travis-ci.org/zzzzbw/doodle)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.zzzzbw/doodle/badge.svg)](https://search.maven.org/artifact/com.github.zzzzbw/doodle-core/0.1/jar)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 简介

*doodle*是一个简易的Java MVC框架，它提供了类似于*spring* 的Bean容器、IOC、AOP、MVC等功能

代码简洁、轻量，适合用于参考学习spring

* 一行代码即可启动服务，内置Tomcat容器
* `DispatcherServlet`请求逻辑处理采用责任链模式，方便增加修改规则
* 不使用XML配置，完全注解开发
* IOC容器，解决循环依赖注入问题
* 支持多AOP增强，切点支持aspectj表达式

Http请求访问设计图：

![](https://raw.githubusercontent.com/zzzzbw/blog_source/master/images/Doodle/doodle-DispatcherServlet.png)

## 示例代码

1. `git clone https://github.com/zzzzbw/doodle.git` 或直接下载代码到本地
2. 将项目导入到IDE中，这是maven工程,确保你已经安装maven
3. 执行`doodle-example/src/main/java`下的`github.zzzzbw.App`类的`main()`方法即可启动Tomcat服务器
  
   ![](https://raw.githubusercontent.com/zzzzbw/blog_source/master/images/Doodle/Snipaste_2018-07-11_20-57-15.png)

4. 浏览器访问`http://localhost:9090/user/list`和`http://localhost:9090/user/detail?id=1`链接

   ![](https://raw.githubusercontent.com/zzzzbw/blog_source/master/images/Doodle/Snipaste_2018-08-13_23-08-30.png)![](https://raw.githubusercontent.com/zzzzbw/blog_source/master/images/Doodle/Snipaste_2018-08-13_23-08-57.png)

5. 通过'Postman'等工具用`post`方式请求到链接`http://localhost:9090/user/add?name=zzzzbw`和`http://localhost:9090/user/delete?id=1`修改数据


## 使用方式

### 引入依赖

创建一个 maven 项目，引入核心依赖

```xml
<dependency>
  <groupId>com.github.zzzzbw</groupId>
  <artifactId>doodle-core</artifactId>
  <version>0.1</version>
</dependency>
```

启动类：

```java
public class App {
    public static void main(String[] args) {
        Doodle.run(App.class, 9090);
    }
}
```

### IOC使用

使用方式与*spring*基本一致，不过只支持类型注入，即如果被注入对象为类，则注入其实例，如果被注入对象为接口，则注入其实现类。

```java
@Component
public class EmailComponent {
    // do something
}
```

为`UserController`注入`EmailComponent`

```java
@Controller
public class UserController {
    @Autowired
    private EmailComponent emailComponent;
    // do something
}
```

### AOP使用

* 增强处理类必须实现`MethodBeforeAdvice`,`AfterReturningAdvice`,`ThrowsAdvice`,`AroundAdvice`中的一个或者多个接口
* 增强处理类被`@Aspect`注解，且有正确的aspectj切点表达式
* (可选)`@Order`注解来决定增强处理类的先后顺序

```java
@Order(1)
@Aspect(pointcut = "@within(Controller)")
public class Aspect1 implements AroundAdvice {
    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        // do something
    }
    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        // do something
    }
    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        // do something
    }
}
```

```java
@Order(2)
@Aspect(pointcut = "@within(Controller)")
public class Aspect2 implements AroundAdvice {
    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        // do something
    }

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        // do something
    }

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        // do something
    }
}
```

上面两个增强处理类`Aspect1`和`Aspect2`，会增强被`@Controller`注解的类下的方法，且顺序为：

Aspect1@before()->Aspect2@before()->method.invoke()->Aspect2@afterReturning()->Aspect1@afterReturning()

### Controller使用

使用几乎与*spring*一致

返回String类型的则返回jsp页面的路径，返回ModelAndView则在其view中设置jsp页面路径，同时可以传一些参数给jsp页面

被`@ResponseBody`注解的则返回的是json格式数据

```java
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getUserList() {
        List<User> list = userService.getUser();
        return new ModelAndView().setView("user_list.jsp").addObject("list", list);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView getUser(@RequestParam(value = "id") long id) {
        User user = userService.getUserById(id);
        return new ModelAndView().setView("user_detail.jsp").addObject("user", user);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result<User> addUser(@RequestParam(value = "name") String name) {
        User user = userService.addUser(name);
        return new Result<>(user, 0, "");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> deleteUser(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return new Result<>("success", 0, "");
    }
}
```

## 约束

*doodle*暂时功能没有完善，在使用过程中有一些约束条件

* 被Bean容器接管的类都有无参的构造方法且构造方法不是私有的 
* `@Component`只应用于类，应用于接口或抽象类将无效 
* 每个`Controller`被`@RequestMapping`标注的方法，其参数必须必须被`@RequestParam`注解，且参数必须是java的基本数据类型或者基本数据的包装类(如int,Integer等)


## 详解

* [从零开始实现一个简易的Java MVC框架(一)--前言](http://zzzzbw.cn/article/8)
* [从零开始实现一个简易的Java MVC框架(二)--实现Bean容器](http://zzzzbw.cn/article/9)
* [从零开始实现一个简易的Java MVC框架(三)--实现IOC](http://zzzzbw.cn/article/10)
* [从零开始实现一个简易的Java MVC框架(四)--实现AOP](http://zzzzbw.cn/article/11)
* [从零开始实现一个简易的Java MVC框架(五)--引入aspectj实现AOP切点](http://zzzzbw.cn/article/12)
* [从零开始实现一个简易的Java MVC框架(六)--加强AOP功能](http://zzzzbw.cn/article/13)
* [从零开始实现一个简易的Java MVC框架(七)--实现MVC](http://zzzzbw.cn/article/14)
* [从零开始实现一个简易的Java MVC框架(八)--制作Starter](http://zzzzbw.cn/article/15)
* [从零开始实现一个简易的Java MVC框架(九)--优化MVC代码](http://zzzzbw.cn/article/16)

## 结语

本项目不仅参考了spring-boot，同时还参考了:

- [blade](https://github.com/lets-blade/blade )
- [latke](https://github.com/b3log/latke)
- [smart-framework](https://gitee.com/huangyong/smart-framework)

这些都是国人写的更轻量而且也很优秀的框架，相比spring-boot来说阅读源码的难度会小一些。感谢这些框架给我提供学习的思路，同时给我们更多的选择。

