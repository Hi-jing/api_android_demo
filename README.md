# Android网络请求框架okhttp请求接口案例

## 前言

前一篇《Servlet接收ajax请求返回json数据案例》写好的接口（地址：<https://github.com/QQ2505728250/api_demo>），在android移动端怎么共用呢？本篇主要使用网络请求框架okhttp进行网络请求，请求服务端同一套接口。主要技术有：okhttp、gson、ListView。

操作前提：《Servlet接收ajax请求返回json数据案例》的案例项目部署上tomcat服务器、更新项目里的统一请求根路径（cmd -> ipconfig获取你的ip）、添加依赖（okhttp、gson）


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633aea7e8adfc?w=554&h=127&f=png&s=29965)


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633b0163dc7ca?w=554&h=182&f=png&s=56861)

 

项目代码（包含文档）地址：<https://github.com/QQ2505728250/api_android_demo>

 

## 效果图：



![](https://user-gold-cdn.xitu.io/2019/6/17/16b633c0100f8467?w=285&h=192&f=png&s=9440)



![](https://user-gold-cdn.xitu.io/2019/6/17/16b633c33af804cd?w=260&h=188&f=png&s=8617)


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633bada271b3d?w=270&h=228&f=png&s=7446)

 

### 1、okhttp简介

#### (1) 简单介绍

一个处理网络请求的开源项目,是安卓端最火热的轻量级框架。

Github地址：<https://github.com/square/okhttp>

 

### 2、okhttp的同步请求

#### (1) get同步请求初始化数据


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633c5ee691923?w=554&h=220&f=png&s=49988)

#### (2) 异步delete请求删除ListView中数据


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633cc7e9f8f4d?w=554&h=289&f=png&s=68740)

#### (3) Put异步 更新数据


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633ca77419633?w=554&h=307&f=png&s=78710)

#### (4) 异步Post添加数据


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633cfa391596c?w=554&h=301&f=png&s=66946)

### 3、主要工具类介绍（讲下前篇没有用到的）

#### (1) GsonUtil工具类的jsonArr2ObjectList方法：将josn对象数组直接 转成对象 集合。

![](https://user-gold-cdn.xitu.io/2019/6/17/16b633d189ce8c0a?w=554&h=344&f=png&s=58100)

![](https://user-gold-cdn.xitu.io/2019/6/17/16b633d2f4d67b18?w=554&h=89&f=png&s=27877)


 

 

 

 

 

为了方便浏览学习，本文内容以文章形式发布到**专业知识分享小程序（个人毕业论文作品），**微信扫一扫直接阅读

**专业知识分享小程序：面向高校学生、老师、以及在职中的校友，以专业为基础，针对性地给在校大学生传授知识，职业方向指引，学习资源共享、学习规划。**

**会陆续发布与专业相关的知识或一些求职经验等。欢迎加入，一起学习，一起成长！**

致歉：可能专业知识分享小程序中图片看不清楚，无法放大，目前正在修复组件中...


![](https://user-gold-cdn.xitu.io/2019/6/17/16b633d5abd3f422?w=271&h=272&f=png&s=71650)
 
