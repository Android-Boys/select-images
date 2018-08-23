package qzlj.xinyi.net.cn.testselectimages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import xinyi.com.imagepicker.PickImages;

public class MainActivity extends AppCompatActivity {

    PickImages mPickImages;
    PickImages mPickImages1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPickImages = findViewById(R.id.mPickImages);
        mPickImages1=findViewById(R.id.mPickImages1);
        List<String>d=new ArrayList<>();
        d.add("http://img.zcool.cn/community/01b34f58eee017a8012049efcfaf50.jpg@1280w_1l_2o_100sh.jpg");
        d.add("http://img.sccnn.com/bimg/338/24556.jpg");
        d.add("http://img.zcool.cn/community/019c2958a2b760a801219c77a9d27f.jpg");
        mPickImages.setDefaluteImage(R.mipmap.ic_launcher)
                .setPreadLoadImage(R.mipmap.ic_launcher)
                .setNumColumns(4)
                .setErrorImage(R.mipmap.ic_launcher)
                .setDataSource(d)
                .builder();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPickImages.onActivityResult(requestCode, resultCode, data);
        mPickImages1.onActivityResult(requestCode, resultCode, data);
    }
}
