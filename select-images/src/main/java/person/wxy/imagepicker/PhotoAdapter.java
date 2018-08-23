package person.wxy.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.List;
import java.util.UUID;

import xinyi.com.selectmore.R;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int IMAGE_PICKER = 1994;
    public static final int IMAGE_BIGPHOTO = 1995;
    private List<ImageModel> dataSource;
    private int preadLoadImage;
    private int maxImages;
    private int errorImage;

    private PickImages pickImages;

    public PhotoAdapter(List<ImageModel> dataSource, int preadLoadImage, int maxImages, int errorImage,PickImages  pickImages) {
        this.dataSource = dataSource;
        this.preadLoadImage = preadLoadImage;
        this.maxImages = maxImages;
        this.errorImage = errorImage;
        this.pickImages=pickImages;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建不同的 ViewHolder
        View view = View.inflate(parent.getContext(), R.layout.item_image, null);
        return new MoreSelectItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ImageModel t = dataSource.get(dataSource.size() - position - 1);
        final MoreSelectItemHolder moreSelectItemHolder = (MoreSelectItemHolder) holder;
        moreSelectItemHolder.tagImageView.setVisibility(t.isFromNet() == false? View.VISIBLE : View.GONE);
        moreSelectItemHolder.iconImageView.setImageResource(preadLoadImage);
        if (t.getPath() == null || t.getPath().equals("")) {
            moreSelectItemHolder.tagImageView.setVisibility(View.GONE);
        } else {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(preadLoadImage)
                    .error(errorImage);
            Glide.with(moreSelectItemHolder.iconImageView.getContext())
                    .load(t.getPath())
                    .apply(options)
                    .into(moreSelectItemHolder.iconImageView);
        }

        moreSelectItemHolder.iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=null;
                pickImages.setTag(UUID.randomUUID().toString());
                if (t.getPath() == null || t.getPath().equals("")) {
                    //添加新的照片
                    ImagePicker.getInstance().setSelectLimit(maxImages - dataSource.size() + 1);
                    intent= new Intent(moreSelectItemHolder.iconImageView.getContext(), ImageGridActivity.class);
                    ((Activity) moreSelectItemHolder.iconImageView.getContext()).startActivityForResult(intent, IMAGE_PICKER);
                } else {
                    //查看大图
                    intent= new Intent(moreSelectItemHolder.iconImageView.getContext(), ImageActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("isFromNet",t.isFromNet());
                    intent.putExtra("select_more_data", com.alibaba.fastjson.JSONObject.toJSONString(dataSource));
                    ((Activity) moreSelectItemHolder.iconImageView.getContext()).startActivityForResult(intent, IMAGE_BIGPHOTO);
                }
            }
        });
        moreSelectItemHolder.tagImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSource.remove(dataSource.size() - position - 1);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (dataSource.size() <= maxImages) {
            return dataSource.size();
        } else {
            return maxImages;
        }
    }


    private class MoreSelectItemHolder extends RecyclerView.ViewHolder {

        public ImageView tagImageView;
        public ImageView iconImageView;

        public MoreSelectItemHolder(View itemView) {
            super(itemView);
            tagImageView = itemView.findViewById(R.id.tagPhoto);
            iconImageView = itemView.findViewById(R.id.ivPhoto);
        }
    }

}
