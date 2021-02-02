
# Chatoyant View Android Library

  <h3>New type of user experience which allow you to highlight your views with slight shining while users rotating their phones.
  Screens interacts with user through gyroscope. No permissions. No overdraws. No Sensor wasting (disabled during onPause). Super customizable. All types of views available</h3>


![Sample app gif](https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/res/readme/overall.gif width=300)

* [How to use](#how-to-use)
    * [add - Jcenter](#jcenter)
    * [add - Jitpack](#jitpack)
    * [Setup](#setup)
    * [Demo](#demo)
* [Components](#components)
    * [ChatoyantButton](#ChatoyantButton)
    * [ChatoyantTextView](#ChatoyantTextView)
    * [ChatoyantSurface](#ChatoyantSurface)
    * [Create your own widget](#create-your-own-widget)


## How to use

If you want use this library, you only have to download this project or ChatoyantView module and copypaste files to your project.

If you prefer it, you can use the gradle dependency, you have to add these lines in your build.gradle file:

## Jcenter

```xml
repositories {
    jcenter()
}

dependencies {
    compile 'com.afkoders.chatoyantview:1.0.2'
}
```

## Jitpack

root build.gradle:
```xml
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

dependencies section:
```xml
	dependencies {
	        implementation 'com.github.Kalevych:Chatoyant-View-Android:Tag'
	}
```

## Setup

You can simply addd a chatoyant view to your xml file next way:

```xml
<com.afkoders.chatoyantview.chatoyant_view.ChatoyantSurface
            android:id="@+id/csChatoyantBottomExample"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:animation_velocity="0.5"
            app:corners_radius="16dp"
            app:is_relative_dimension="true"
            app:shine_angle="0"
            app:shine_width="0.25"
            app:shine_color="@color/halfWhite"
            app:surface_background_color="@color/gold"
            app:tile_mode="MIRROR" />
```


>```xml
>    app:animation_velocity="0.5"
>```
> will increase speed of shining activity. Default value = 1
>
>```xml
>    app:is_relative_dimension="true"
>```
> means if coordinates of shader for your view will depend on screen size or it's own (view) size. To make all the views on the screen moving synchroniously keep 
> this value set up as true
>
>```xml
>    app:shine_angle="0"
>```
> sets angle of pivot centre for shining line. In degrees (max 360) 
>
>```xml
>    app:shine_width="0.25"
>```
> from 0 to 1. Represents percent of screen(or view, see is_relative_dimension) will be dallocated as area for shining unit
>
>```xml
>    app:tile_mode="MIRROR"
>```
> mode for shader spreading. Could be CLAMP, REPEAT, MIRROR
>

all the parameter also could be set up programmatically. Few important additions:

- each ChatoyantView has method ''setBitmapForBackground()'' which could pass your own Bitmap as dynamical background for Shader.
- call clipFromViews(innerViewId) for chatoyantSurface if it contains any views inside itself. It will clip these areas from canvas, 
  help you to avoid overdraws and make your app as much optimized as its possible!

## Demo

[![Sample app gif](https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/res/readme/angle.gif)]
[![Sample app gif](https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/res/readme/bitmap.gif)]
[![Sample app gif](https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/res/readme/mode.gif)]

## Components

### ChatoyantButton


![chato button](images/chato_button.png)
```xml
 <com.afkoders.chatoyantview.chatoyant_view.ChatoyantButton
                android:id="@+id/chatoyantButton"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="BUY RIGHT NOW"
                android:textSize="20sp"
                app:backgroundTint="@color/gold"
                app:tile_mode="MIRROR" />
```

### ChatoyantTextView

![chato textView](images/chato_tv.png)
```xml
   <com.afkoders.chatoyantview.chatoyant_view.ChatoyantTextView
                android:id="@+id/tvTitleInner"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:lineSpacingExtra="8sp"
                android:text="@string/chatoyant_text"
                android:textColor="@color/gold"
                android:textSize="30sp"
                android:textStyle="bold"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:tile_mode="MIRROR" />
```

### ChatoyantSurface

![Chato surface](images/chato_surface.png)

>call clipFromViews(innerViewId) for chatoyantSurface if it contains any views inside itself. 
>It will clip these areas from canvas. It'll help you 
>to avoid overdraws and make your app as much optimized as its possible!

```xml
<com.afkoders.chatoyantview.chatoyant_view.ChatoyantSurface
            android:id="@+id/csChatoyantBottomExample"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:animation_velocity="0.5"
            app:corners_radius="16dp"
            app:is_relative_dimension="true"
            app:shine_angle="0"
            app:shine_width="0.25"
            app:shine_color="@color/halfWhite"
            app:surface_background_color="@color/gold"
            app:tile_mode="MIRROR" />
```

## Create your own widget
TBD
