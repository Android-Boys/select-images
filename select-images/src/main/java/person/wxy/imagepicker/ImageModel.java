package xinyi.com.imagepicker;

import java.io.Serializable;

import xinyi.com.selectmore.R;

public class ImageModel implements Serializable {

    public String path = "";
    public int addImageView = R.mipmap.add_photo;

    //数据源是 自己选择的  还是网络获取的。默认是自己选择的
    public boolean isFromNet=false;

    public String getPath() {
        return path;
    }

    public ImageModel setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isFromNet() {
        return isFromNet;
    }

    public ImageModel setFromNet(boolean fromNet) {
        isFromNet = fromNet;
        return this;
    }

    public int getAddImageView() {
        return addImageView;
    }

    public ImageModel setAddImageView(int addImageView) {
        this.addImageView = addImageView;
        return this;
    }
}
