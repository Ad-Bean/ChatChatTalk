# ChatChatTalk 运行说明

## 第一组：组员情况

胡文浩

冼子婷

廖雨轩 18322043

## 编译环境

* 操作系统 ：Windows10
* JAVA编辑器：IntelliJ IDEA 2020.4
* JAVA版本 ：javaJDK 15.01 - java15
* 依赖：AnimateFX commons-lang fontawesome jfoenix 

> 编译运行参数与依赖已导入IDEA，若出现编译问题请参照javaFX文档 https://openjfx.io/openjfx-docs/ 或是 CSDNhttps://blog.csdn.net/weixin_43616817/article/details/106668473 进行IDEA的配置

即选用最新版的JAVA SDK，但是由于javaFX无法在低版本java下运行，并且需要单独配置，环境可能比较复杂。其中需要注意的点为，需要在IDEA配置路径变量PATH_TO_FX，值为本机的javaFX路径。Application的运行参数VM options需要设置为：```--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml```

![QQ截图20210103162817](public\PATHTOFX.png)

![QQ截图20210103163155](public\VMOPTIONS.png)