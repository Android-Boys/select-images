package person.wxy.imagepicker;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.lzy.imagepicker.loader.ImageLoader;

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        showImage(path, imageView);
    }

    private void showImage(String path, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        showImage(path, imageView);
    }


    @Override
    public void clearMemoryCache() {

    }
}
