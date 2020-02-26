# 基于simple-robot与LEMOC插件的Java开发框架
[![](https://img.shields.io/badge/simple--robot-core-green)](https://github.com/ForteScarlet/simple-robot-core)  [![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core) [![](https://img.shields.io/maven-central/v/io.github.ForteScarlet.simple-robot-core/component-forlemoc)](https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot-core/component-forlemoc)  [![](https://img.shields.io/badge/%E7%9C%8B%E4%BA%91%E6%96%87%E6%A1%A3-doc-green)](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc)  [![](https://img.shields.io/badge/QQ%E7%BE%A4-782930037-blue)](https://jq.qq.com/?_wv=1027&k=57ynqB1)

> 如果需要获得更好的阅读体验，请前往 [原文档](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/) -> `组件-酷Q-LEMOC` -> `快速开始` 处阅读
> 或尝试直接进入[快速开始](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/1131277)


**点击项目右上角的`star`展示完整文档**


# 快速开始

*****

<br>


## 一、**安装**

### 1\. **下载并安装 酷Q**

前往酷Q[官方下载地址](https://cqp.cc/t/23253)下载酷Q应用，并安装（启动一次），然后关闭。

<br>


### 2\. **下载并安装 LEMOC插件**
**①.** 前往LEMOC的 [社区帖子](https://cqp.cc/forum.php?mod=viewthread&tid=29722&highlight=lemoc) 下载最新版本。
**②.** 将下载好的`.cpk`格式文件移动至`酷Q`根目录下的`/app`文件夹下。
**③.** 启动一次酷Q程序，右键酷Q标志，选择：`应用 > 应用管理`，如图所示：
![](https://i.vgy.me/QpgBpK.png)
然后将会出现应用管理界面，选择LEMOC插件并选择启用，如图所示：
![](https://i.vgy.me/PE08MH.png)

>[success] LEMOC的配置界面很简单（但是有可能会因为兼容性等BUG导致显示不全）：

![](https://i.vgy.me/gtmLhZ.png)


### 3\. **创建Java项目**

你可以使用一切支持的方式来自动构建项目，以下将会举几个例子：

>[info] 版本号请自行替换为 Maven仓库中的最新版本：[![](https://img.shields.io/maven-central/v/io.github.ForteScarlet.simple-robot-core/component-forlemoc)](https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot-core/component-forlemoc)

#### **①. Maven**

```xml
<dependency>
  <groupId>io.github.ForteScarlet.simple-robot-core</groupId>
  <artifactId>component-forlemoc</artifactId>
  <version>${version}</version>
</dependency>
```

#### **②. Gradle**

```
implementation 'io.github.ForteScarlet.simple-robot-core:component-forlemoc:${version}'
```

#### **③. Grape**

```
@Grapes(
  @Grab(group='io.github.ForteScarlet.simple-robot-core', module='component-forlemoc', version='${version}')
)
```


## **4\. 配置**

有两个地方需要你进行配置：

* **酷Q的`LEMOC`**
* **你需要启动的Java程序**


从Java的配置开始吧。

>[warning] 由于目前文件配置尚比较繁琐，便暂时先只介绍代码配置的方式。如果对目前的较为繁琐的文件配置有兴趣，请在了解代码配置的情况下查看 [文件配置方式](./CQHTTPAPI%E6%96%87%E4%BB%B6%E9%85%8D%E7%BD%AE.md)

首先，创建一个类，实现`com.forte.qqrobot.component.forlemoc.LemocApp`接口，并实现接口中的`before`与`after`方法。

>[info] 现在假定你这个类叫做 **`RunApp`** , 方便后续的代称。当然，它实际上叫做什么都无所谓。


可以发现，`before`方法中存在一个叫做`LinkConfiguration`的参数，我们就要通过这个参数对象进行配置。
这个`LinkConfiguration`中比较核心的几项配置内容为：
```java
// 获取连接用的IP地址，即Lemoc所在的IP地址，默认为localhost 
setLinkIp(String linkIp);
// 连接端口号，默认为25303 
setPort(Integer port);
// 设置机器人的QQ号
setLocalQQCode(String qqcode);
// 设置机器人的昵称
setLocalQQNick(String nick);
```
>[success] 一共只有这么几个配置项，再回去看看LEMOC的配置界面，你大概已经知道了该如何对应了吧~

是的，只要使设置的连接端口号与LEMOC的设置相同，再填上LEMOC所在的IP地址即可。



## **5\. 运行**

历尽千辛万苦，终于到了这一步。
首先，新建一个`main`方法在任意地方。
写下以下代码，并且别忘了那个`RunApp`实际上代表了什么：

```java
LemocApplication application = new LemocApplication();
// 启动
application.run(new RunApp());
```

>[success] 如果这时候控制台有提示"连接成功"且没有报错等信息，则说明ws连接成功了。LEMOC组件框架暂时不支持自动获取机器人的信息，所以需要使用者在配置`LinkConfiguration`的时候手动配置。

那么能不能监听到消息呢？写一些代码来测试一下吧。

## **6\. 第一个监听器**

### **①. 新建一个类**

>[info] 尽量在`RunApp`的同级目录或者子级目录下创建。
> 创建好之后，在这个类上标注一个注解：`@com.forte.qqrobot.anno.depend.Beans`, 即`@Beans`

### **②. 写一个监听私信的方法**

我们写一个监听私信消息满足正则：`hello.*`的私信消息监听函数，且当我们收到消息后，复读。
完整代码如下：

```java
@Beans
public class TestListener {

    @Listen(MsgGetTypes.privateMsg)
    @Filter("hello.*")
    public void testListen1(PrivateMsg msg, MsgSender sender) {
        System.out.println(msg);
        // 以下三种方法均可
，效果相同
        sender.SENDER.sendPrivateMsg(msg, msg.getMsg());
//        sender.SENDER.sendPrivateMsg(msg.getQQ(), msg.getMsg());
//        sender.SENDER.sendPrivateMsg(msg.getQQCode(), msg.getMsg());
    }
}
```

### **③. 在来一次**

这次我们再启动一次，如果发现启动日志中成功加载了这个监听函数，那就试着给你的机器人发送一句`hello world`吧。
如果它也回复了你一句`hello world`，那么说明至此你已经成功了，探索文档所提供的众多功能并实现你的机器人吧~

## **7\. 失败了？**

如果跟着上述流程完整无误的操作却无法成功，也不要气馁，尝试根据[常见问题汇总](./%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98%E6%B1%87%E6%80%BB.md)进行排查或者加入QQ群`782930037`进行咨询。

