package xinyi.com.imagepicker;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import xinyi.com.selectmore.R;

public class PickImages extends LinearLayout {

    private View rootView;
    private int numColumns;//recycleView 列数
    private int maxImages;//最大选取的图片
    private int defaluteImage = 0;//添加图片 资源
    private int errorImage = 0;//加载错误的图片
    private int preadLoadImage = 0;//预加载图片

    private RecyclerView mRecycleView;
    private List<ImageModel> dataList = new ArrayList<>();
    private PhotoAdapter mPhotoAdapter;

    public PickImages(Context context) {
        super(context);
    }

    public PickImages(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PickMoreStyle, 0, 0);
        numColumns = a.getInt(R.styleable.PickMoreStyle_numColumns, 3);
        maxImages = a.getInt(R.styleable.PickMoreStyle_maxImages, 9);
        defaluteImage = a.getResourceId(R.styleable.PickMoreStyle_itemDefaluteImage, R.mipmap.add_photo);
        preadLoadImage = a.getResourceId(R.styleable.PickMoreStyle_preadLoadImage, R.mipmap.ic_launcher);
        errorImage = a.getResourceId(R.styleable.PickMoreStyle_errorImage, R.mipmap.ic_launcher);
        a.recycle();
        init(context);
    }

    public PickImages(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {
        /*this.setTag(this.getId());*/
        rootView = View.inflate(context, R.layout.pick_more, this);
        mRecycleView = rootView.findViewById(R.id.mRecycleView);
        mRecycleView.setLayoutManager(new GridLayoutManager(context, numColumns));

        dataList.add(new ImageModel());//初始化元素
        mRecycleView.setHasFixedSize(true);
        mPhotoAdapter = new PhotoAdapter(dataList, preadLoadImage, maxImages, errorImage,this);
        mRecycleView.setAdapter(mPhotoAdapter);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setMultiMode(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(numColumns);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

    }

    //拍完照片之后的回调
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //tag为null,表示点击的不是这个PickImages，tag不为null 表示点击的是这个对象。之后统一清除tag
        if (getTag()!=null) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (data != null) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    for (ImageItem mImageItem : images
                            ) {
                        ImageModel model = new ImageModel();
                        model.setPath(mImageItem.path);
                        dataList.add(model);
                    }
                    mPhotoAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mRecycleView.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == PhotoAdapter.IMAGE_BIGPHOTO) {
                List<ImageModel> d = JSONObject.parseObject(data.getStringExtra("select_more_data"), new TypeReference<List<ImageModel>>() {
                });

                boolean isFromNet = false;//不是从网络获取的数据
                if (dataList != null && dataList.size() > 0) {
                    isFromNet = dataList.get(0).isFromNet();
                }
                dataList.clear();
                if (!isFromNet) {
                    dataList.add(new ImageModel());
                }
                if (d != null) {
                    dataList.addAll(d);
                }
                mPhotoAdapter.notifyDataSetChanged();
            }
        }
        setTag(null);
    }

    public PickImages setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        mRecycleView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
        return this;
    }

    public PickImages setMaxImages(int maxImages) {
        this.maxImages = maxImages;
        ImagePicker.getInstance().setSelectLimit(maxImages);
        return this;
    }

    public PickImages setDefaluteImage(int defaluteImage) {
        this.defaluteImage = defaluteImage;
        return this;
    }

    public PickImages setErrorImage(int errorImage) {
        this.errorImage = errorImage;
        return this;
    }

    public PickImages setPreadLoadImage(int preadLoadImage) {
        this.preadLoadImage = preadLoadImage;
        return this;
    }

    public PickImages setDataSource(List<String> dataSource) {
        dataList.clear();
        for (String p : dataSource
                ) {
            ImageModel model = new ImageModel();
            if (defaluteImage != 0) {
                model.setAddImageView(defaluteImage);
            }
            model.setFromNet(true);
            model.setPath(p);
            dataList.add(model);
        }
        return this;
    }

    public void builder() {
        mPhotoAdapter = new PhotoAdapter(dataList, preadLoadImage, maxImages, errorImage,this);
        mRecycleView.setAdapter(mPhotoAdapter);
    }

    public List<String> getImagePaths() {
        List<String> paths = new ArrayList<>();
        for (ImageModel model : dataList
                ) {
            paths.add(model.getPath());
        }
        return paths;
    }
}
