### AndroidManifest 说明

#### 目录结构
- [android:configChanges属性](#1.0.0)
- [更新日志](#2.0.0)
- [应用截图](#3.0.0)
- [下载地址](#4.0.0)
- [接口文档说明](#5.0.0)
- [项目中使用到的三方库说明](#6.0.0)
- [项目反馈](#7.0.0)
- [参考资料](#8.0.0)
- [Issuse Me](#9.0.0)

<h4 id="1.0.0"> 1.android:configChanges属性</h4>

```
android中的组件Activity在manifest.xml文件中可以指定参数android：ConfigChanges，用于捕获手机状态的改变。
在Activity中添加了android:configChanges属性，在当所指定属性(Configuration Changes)发生改变时，通知程
序调用onConfigurationChanged()函数。
设置方法：将下列字段用“|”符号分隔开，例如：“locale|navigation|orientation”

android:configChanges=["mcc", "mnc", "locale",
"touchscreen", "keyboard", "keyboardHidden",
"navigation", "screenLayout", "fontScale", "uiMode",
"orientation", "screenSize", "smallestScreenSize"]
mcc:The IMSI mobile country code (MCC) has changed — a SIM has been detected and updated the MCC.
    IMSI(国际移动用户识别码)发生改变，检测到SIM卡，或者更新MCC,移动国家号码，由三位数字组成，每个国家都有自己独立的MCC，可以识别手机用户所属国家。
mnc:The IMSI mobile network code (MNC) has changed — a SIM has been detected and updated the MNC.
    IMSI网络发生改变,检测到SIM卡，或者更新MCC,移动网号，在一个国家或者地区中，用于区分手机用户的服务商。
    其中mcc和mnc理论上不可能发生变化
locale:The locale has changed — the user has selected a new language that text should be displayed in.
    语言发生改变，用户选择了一个新的语言，文字应该重新显示
touchscreen:The touchscreen has changed. (This should never normally happen.)
    触摸屏发生改变，这通常是不应该发生的
keyboard:The keyboard type has changed — for example, the user has plugged in an external keyboard.
    键盘类型发生改变，例如，用户使用了外部键盘
keyboardHidden:The keyboard accessibility has changed — for example, the user has revealed the hardware keyboard.
    键盘发生改变，例如，用户使用了硬件键盘
navigation:The navigation type (trackball/dpad) has changed. (This should never normally happen.)
    导航发生改变，（这通常不应该发生） 举例：连接蓝牙键盘，连接后确实导致了navigation的类型发生变化。因为连接蓝牙键盘后，我可以使用方向键来navigate了
screenLayout：The screen layout has changed — this might be caused by a different display being activated.
    屏幕的布局发生改变，这可能导致激活不同的显示
fontScale：The font scaling factor has changed — the user has selected a new global font size.
    全局字体大小缩放发生改变
uiMode: 用户的模式发生了变化
orientation：The screen orientation has changed — that is, the user has rotated the device.
    设备旋转，横向显示和竖向显示模式切换。
screenSize: 屏幕大小改变了
smallestScreenSize: 屏幕的物理大小改变了，如：连接到一个外部的屏幕上

对android:configChanges属性，一般认为有以下几点：
1、不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
2、设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
3、设置Activity的android:configChanges="orientation|keyboardHidden"时，切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
但是，自从Android 3.2（API 13），在设置Activity的android:configChanges="orientation|keyboardHidden"后，
还是一样会重新调用各个生命周期的。因为screen size也开始跟着设备的横竖切换而改变。所以，在AndroidManifest.xml里
设置的MiniSdkVersion和 TargetSdkVersion属性大于等于13的情况下，如果你想阻止程序在运行时重新加载Activity，除了
设置"orientation"，你还必须设置"ScreenSize"。
解决方法：
AndroidManifest.xml中设置android:configChanges="orientation|screenSize"

//当指定了android:configChanges="orientation"后,方向改变时onConfigurationChanged被调用,并且activity不再销毁重建
@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    switch (newConfig.orientation) {
        case Configuration.ORIENTATION_PORTRAIT://竖屏
            Log.i(TAG,"竖屏");
            break;
        case Configuration.ORIENTATION_LANDSCAPE://横屏
            Log.i(TAG,"横屏");
        default:
            break;
    }
}
```