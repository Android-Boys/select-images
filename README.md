# select-images
选择照片，加载网络照片，查看网络图片

### 1.从本地选择图片,包括查看并删除

用法：
```xml
  <xinyi.com.imagepicker.PickImages
        android:id="@+id/mPickImages"
        app:numColumns="4"
        app:itemDefaluteImage="@mipmap/ic_launcher"
        app:maxImages="9"
        app:errorImage="@mipmap/ic_launcher"
        app:preadLoadImage="@mipmap/ic_launcher"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
  ```
  
  获取选择的本地图片的path：mPickImages.getImagePaths();
  
  
  ### 2.加载网络图片,包括查看大图
  
  ``` java
   mPickImages.setErrorImage(R.mipmap.ic_launcher)
                .setDefaluteImage(R.mipmap.ic_launcher)
                .setPreadLoadImage(R.mipmap.ic_launcher)
                .setMaxImages(5)
                .setNumColumns(3)
                .setDataSource(d)
                .builder();
   ```
   获取选择的本地图片的path：mPickImages.getImagePaths();
   
   
   框架目前支持的属性：
   
