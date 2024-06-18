# Accounts-App
[![Author: yunuow](https://img.shields.io/badge/author-yunuow-cc0000)](https://github.com/Razirp) [![License](https://img.shields.io/github/license/Razirp/Le-Vent-Chat)](https://github.com/Razirp/Le-Vent-Chat/blob/main/LICENSE) [![Language: Java](https://img.shields.io/badge/Language-Java-blue)](https://cppreference.com/) [![Built with Qt 5.12](https://img.shields.io/badge/Built%20with-android_studio%204.1.3-70c961?logo=android)](https://www.qt.io/qt-5-12) [![GitHub release](https://img.shields.io/github/v/release/yunuow/Accounts-App?color=660099)](https://github.com/Razirp/Le-Vent-Chat/releases) ![HitCount](https://img.shields.io/endpoint?url=https%3A%2F%2Fhits.dwyl.com%2Fyunuow%2FAccounts-App.json%3Fcolor%3Dff9900) [![GitHub stars](https://img.shields.io/github/stars/yunuow/Accounts-App)](https://github.com/yunuow/Accounts-App)

> 一个基于 Android Studio 4.1.3 的记账本APP源码与打包APK文件。
> 
A source code and packaged APK file for an accounting book APP based on Android Studio 4.1.3

**运行环境**：8.0 以上版本 Android 系统的手机或模拟机

**部署方法**：直接安装 记账本.apk 即可，无需 Server 端应用的部署

## 功能展示：

### 1.主界面

（1）本月支出和本月收入

![image-20240618154229033](.\pic\image-20240618154229033.png)

上图所示为主界面视图（mainActivity），其中，左图是仅设置了预算金额的视 图，右图为添加了一部分记账信息的视图。

在主界面上，首先你可以看到本月支出，本月收入信息，他们能计算并显示 本月的支出总额和收入总额。

（2）预算剩余及提醒

![image-20240618154443093](.\pic\image-20240618154443093.png)

预算剩余界面内容是根据你所设定的预算金额减去本月支出得到的，同时下 面有相应的提示语句，提醒你要控制住自己的钱包。

如上图所示，当点击红框区域时，会弹出“设置预算金额 ”对话框，可以向 其中输入具体金额，也可以点击叉号退出。（注：当无输入点击确定或填入小于 等于 0 的数时，会弹出报错信息“输入不能为空！”或“预算金额必须大于 0”）。

同时，为了兼备提醒功能，在预算下面有一个 text 显示，会根据预算金额的 剩余比例显示不同的提醒，如下图：

![image-20240618154532424](.\pic\image-20240618154532424.png)

![image-20240618154556791](.\pic\image-20240618154556791.png)

（3）本日收入和本日支出，及主界面的删除功能

本日支出和本日收入显示的是今日支出总额和收入总额，同时还给出了 今日的具体日期，方便查看账本信息。

如果想要删除账本信息，可以通过长按显示的账本信息，会弹出“提示 信息 ”：确定删除此条记录？点击确定后，该条账本信息会被永久删除。如下图所示：

![image-20240618154625559](.\pic\image-20240618154625559.png)

二、记账界面（RecordActivity）

记账界面分为支出和收入两个界面，可以通过滑动互相切换。如下图所示:

![image-20240618154748943](.\pic\image-20240618154748943.png)

当点击账本中所显示的不同图标时，账本的类型也随着变化，被选中的图标 将由灰色变为淡红色

当点击下图红框区域时，将弹出“添加备注”对话框，可以向其中添加备注 （不超过 80 字），点击取消将失去全部，点击确定将成功修改备注（注：备注默 认附带时间信息，为方便之后查找）。如下图所示:

当点击下图红框区域时，将弹出含有日历和待输入时间的对话框，可以在日 历中选择具体日期，然后输入具体时间，点击取消将失去本次修改，点击确定将 成功修改时间（注：时间一栏可以空缺不填，将默认为 00:00）。如下图所示:

![image-20240618154815464](.\pic\image-20240618154815464.png)

![image-20240618154843520](.\pic\image-20240618154843520.png)

三、搜索页面（SearchActivity）

当点击主页面的搜索图标后，将进入搜索页面。 点击搜素页面的回退图标后，将返回主页面。

搜索页面的搜索框内有默认提示信息：请输入搜索信息（备注信息或日期）。

当没有填入搜索内容或者搜索内容检索不到时，页面会显示“数据为空，无 此记录”以及相关图标。（注：当搜索内容为空时，会弹出报错信息“输入内容 不能为空！”）

当成功搜索到相关信息时，将会呈现在搜索界面上。这时如果长按账本信息， 会弹出“提示信息”：确定删除此条信息？如果点击确定，那么此条信息将被删 除，返回主界面也会发现此条账本信息已被删除。

如下图所示：

![image-20240618154902353](.\pic\image-20240618154902353.png)
