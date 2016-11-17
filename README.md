# ReboundScrollView
仿IOS ScrollView 支持上拉下拉拉出手机屏幕，回弹

### ScreenShots
![image](https://github.com/cjhandroid/ReboundScrollView/blob/master/app/src/main/assets/ezgif.com-video-to-gif.gif)   
### v1.0.0

ReboundScrollView 基本功能完成：IOS 回弹，但实际运用中这种单纯的回弹是不多的，一般会需要重新订制，建议把源码下下来。

### How to use

#### gradle

```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

  ```
  dependencies {
	        compile 'com.github.cjhandroid:ReboundScrollView:1.0.0'
	}
  ```
  
### Create by xml

```
<cjh.reboundscrollviewlibrary.ReboundScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="cjh.reboundscrollview.MainActivity">
    
    childViews...
    
    </cjh.reboundscrollviewlibrary.ReboundScrollView>
```
        
### Create by code

```
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReboundScrollView reboundScrollView = new ReboundScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int id : resId) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(id);
            linearLayout.addView(imageView);
        }
        reboundScrollView.addView(linearLayout);

        setContentView(reboundScrollView);
    }
```
