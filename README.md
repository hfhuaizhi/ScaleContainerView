# ScaleContainerView
scale container
# how to import
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.hfhuaizhi:ScaleContainerView:1.0.0'
	}
  
 # how to use
 like this
 
     <com.hfhuaizhi.scale_module.CommonScaleContainer
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_gravity="center"
        android:elevation="4dp"
        app:container_bg="#FFFFFF"
        app:container_radius="5dp">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="CommonScaleContainer" />
    </com.hfhuaizhi.scale_module.CommonScaleContainer>
   
  Because CommonScaleContainer is extends from FrameLayout, so you can use all methods that FrameLayout has.
  
# apperance
![image](https://github.com/hfhuaizhi/ScaleContainerView/blob/master/images/Video_20210403_071111_996.gif)
  
# License
MIT License

Copyright (c) 2021 hfhuaizhi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
  
