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
  
  
