
# Chatoyant View Android Library

* [Intro](#intro)
* [How to use](#how-to-use)
    * [add - Jcenter](#jcenter)
    * [add - Jitpack](#jitpack)
    * [Setup](#setup)
    * [Demo](#demo)
* [Components](#components)
    * [ChatoyantButton](#Chatoyant-Button)
    * [ChatoyantTextView](#Chatoyant-TextView)
    * [ChatoyantSurface](#Chatoyant-Surface)
    * [Create your own widget](#create-your-own-widget)

## Intro 
#### Chatoyant View Android Library its a set of Views with dynamical background changing depends on device position in space.

##### Interact with the user in new way, highlight important design elements with rotation glance.

##### No overdraws, no additional permissions.
##### No Sensor wasting (disabled during onPause). Super customizable. 

##### Surface, buttons, text types of views available  
##### Highlight your views with slight shining while users rotating their phones.

<img src="https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/readme/angle.gif" width="800" />


## How to use

If you want use this library, you only have to download this project or ChatoyantView module and copypaste files to your project.
Raw release library you can find in **releases** branch. App with sample you can find in **main** branch

If you prefer it, you can use the gradle dependency, you have to add these lines in your build.gradle file:

## Jcenter

```xml

dependencies {
    implementation 'com.afkoders.chatoyantview:1.0.2'
}
```
OR

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
	        implementation 'com.github.Kalevych:Chatoyant-View-Android:1.0.2'
	}
```

## Setup

You can simply addd a chatoyant view to your xml file next way:

```xml
<com.afkoders.chatoyantview.chatoyant_view.ChatoyantSurface
            android:id="@+id/csChatoyantBottomExample"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            <!-- optional tags -->
            app:animation_velocity="0.5"
            app:corners_radius="16dp"
            app:is_relative_dimension="true"
            app:shine_angle="0"
            app:shine_width="0.25"
            app:shine_color="@color/halfWhite"
            app:surface_background_color="@color/gold"
            app:tile_mode="MIRROR" />
```


<pre>
    <b>app:animation_velocity="0.5"</b>
</pre>

will increase or decrease speed of shining. (Default value = 1)

<pre>
    <b>app:is_relative_dimension="true"</b>
</pre>

means if coordinates of shader for your view will depend on screen size or it's own (view) size. 
To make all the views on the screen moving synchroniously keep this value set up as true. (Default value = true)

<pre>
    <b>app:shine_angle="0"</b>
</pre>
sets angle of pivot centre for shining line. In degrees (max 360). (Default value = 0)

<pre>
    <b>app:shine_width="0.25"</b>
</pre>
Represents percent of screen(or view, see is_relative_dimension) will be dallocated as area for shining unit. From 0 to 1. (Default value = 0.5)

<pre>
    <b>app:tile_mode="MIRROR"</b>
</pre>
Mode for shader spreading. Could be CLAMP, REPEAT, MIRROR (Default value = CLAMP, but try MIRROR)


all the parameter also could be set up programmatically. 

#### Few important additions:

>
> <b>Each ChatoyantView has method ''setBitmapForBackground()'' which could allow to pass your own Bitmap as dynamical background for Shader.</b></br>
> 
> <b>Call clipFromViews(innerViewId) for chatoyantSurface if it contains any views inside itself. It will clip these areas from canvas, 
> help you to avoid overdraws and make your app as much optimized as its possible!</b>
>

## Demo

<img src="https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/readme/bitmap.gif"  width="800" />
<img src="https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/readme/mode.gif"  width="800" />

## Components

## Chatoyant Button

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

## Chatoyant TextView

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

<img src="https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/readme/button.gif"  width="400" />

## Chatoyant Surface

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

**Tip:
Call clipFromViews(innerViewId) for chatoyantSurface if it contains any views inside itself. 
It will clip these areas from canvas. 
It'll help you to avoid overdraws and make your app as much optimized as its possible!**
<pre>
chatoyantSurfaceExample.clipFromViews(binding.cardInsideCahtoyantSurface)
</pre>

## Create your own widget
TBD

<img src="https://github.com/Kalevych/Chatoyant-View-Android/blob/demo-materials/app/src/main/readme/overall.gif"  width="400" />

