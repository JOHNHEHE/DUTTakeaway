# 大工外卖
这是本学期移动开发课程我们组完成的大作业。参考饿了吗、美团外卖开发的一款外卖app,使用了Bmob作为后台。

## 如何使用
1.由于使用Bmob后端云，你需要注册Bmob账号：[Bmob传送门](https://www.bmob.cn/login)，并创建应用；
2.为了方便使用，我导出了我们所使用的的初始化数据，请参考项目根目录下**data**中各个压缩包的名字创建表（其中_User为应用默认存在的表），在Bmob后台中对应表中直接导入压缩包中CSV文件即可；
3.在Android Studio中打开本项目，在目录下找到LoginActivity和StoreActivity两个java文件并填入你的Application Key（在Bmob后台中查看），运行本项目即可；

```java
Bmob.initialize(this, "此处输入你的Application Key");
```

## 项目展示
**系统设计图**
![系统设计图](https://img-blog.csdnimg.cn/202001131823462.png)
**部分运行界面截图**
![欢迎界面](https://img-blog.csdnimg.cn/2020011317594993.png)
*欢迎界面*
![主页](https://img-blog.csdnimg.cn/20200113180206936.png)
*主页*
![点餐界面](https://img-blog.csdnimg.cn/20200113180346994.png)
*点餐界面*
![订单页面](https://img-blog.csdnimg.cn/20200113180525480.png)
*订单页面*
![我的页面](https://img-blog.csdnimg.cn/20200113180632520.png)
*我的页面*

## 写在最后
由于时间和水平有限，并没有使用一些主流的架构，所用到的图片资源也保存在了本地。希望能有帮助_(:з」∠)_

