package com.example.jack.musicdemo.collection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.util.PhotoUtil;
import com.example.jack.musicdemo.data.CollectionBean;

import java.io.File;

import static android.os.Build.VERSION_CODES.M;
import static com.example.jack.musicdemo.R.drawable.c;

/**
 * 收藏夹创建界面
 */
public class CollectionCreateActivity extends AppCompatActivity implements View.OnClickListener{
     public static void open(Context context){
         open(context,-1);
     }

    private static void open(Context context, int cid) {
        Intent intent = new Intent(context,CollectionCreateActivity.class);
        intent.putExtra("cid",cid);
        context.startActivity(intent);
    }
    private Toolbar toolbar;
    private ImageView cover;
    private TextView title;
    private EditText desEt;

    private PhotoUtil photoUtil;

    private CollectionBean collectionBean;
    private int cid;
    private boolean hasChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_create);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent() !=null ){
            cid = getIntent().getIntExtra("cid",-1);
            if(cid == -1){
                getSupportActionBar().setTitle(R.string.collection_create_title);
            }
        }

        cover = (ImageView) findViewById(R.id.collection_cover);
        cover.setOnClickListener(this);
        cover.requestFocus();
        title = (TextView) findViewById(R.id.collection_title);
        title.setOnClickListener(this);
        desEt = (EditText) findViewById(R.id.collection_des);
        //设置edit没办法自动获取焦点，就是不会一点开就弹出来，但用户点击在去弹出来
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        photoUtil = new PhotoUtil(this);
        initData();
    }

    private void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //创建歌单的封面的监听
            case R.id.collection_cover:
               new MaterialDialog.Builder(this)
                       .title(R.string.collection_dialog_cover_title)
                       //传入item
                       .items(R.array.collection_cover)
                       .itemsCallback(new MaterialDialog.ListCallback(){
                        //监听item
                           @Override
                           public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position){
                                    case 0:
                                        //开启相机
                                        photoUtil.takePhoto();
                                        break;
                                    case 1:
                                        //开启相册
                                        photoUtil.picPhoto();
                                        break;
                                }
                           }
                       }).show();

                break;
            //创建歌单名字
            case R.id.collection_title:
                  new MaterialDialog.Builder(this)
                          .title(R.string.collection_dialog_name)
                          //参1：最少输入几个字，参2：最多输入几个字，参3:下划线的颜色
                          .inputRangeRes(2,20,R.color.theme_color_PrimaryAccent)
                          .inputType(InputType.TYPE_CLASS_TEXT)
                          .input(collectionBean.getTitle(), "", new MaterialDialog.InputCallback() {
                              @Override
                              public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                  if(!TextUtils.isEmpty(input)){
                                      collectionBean.setTitle(String.valueOf(input));
                                      title.setText(input);
                                      hasChange = true;
                                  }
                              }
                          }).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     switch (requestCode){
         case PhotoUtil.TAKE_PHOTO:
             //拍照的回调
             if(resultCode == RESULT_OK){
                 //参数1：地址 参2、3：设置拍完找照片设置成的大小，参4：有待研究，参5：用于做切图判断
                 photoUtil.cropImageUri(photoUtil.getUri(),200,200,false,PhotoUtil.CROP_PICTURE);
             }
             break;
         case PhotoUtil.CHOOSE_PICTURE:
             //从相册中选择照片的回调
             if(resultCode == RESULT_OK){
                 if(data != null){
                     Uri uri = data.getData();
                     Uri uri1 = Uri.fromFile(new File(PhotoUtil.getPath(this,uri)));
                     photoUtil.cropImageUri(uri1,200,200,false,PhotoUtil.CROP_PICTURE);
                 }
             }
             break;
         case PhotoUtil.CROP_PICTURE:
            //拍照切图的回调
             if(resultCode == RESULT_OK){
                 //获取图片地址并解析
                 Bitmap map = photoUtil.decodeUriAsBitmap(photoUtil.getUri());
                 Drawable drawable = new BitmapDrawable(map);
                 //设置大小
                 float w = drawable.getIntrinsicWidth();
                 float H = drawable.getIntrinsicWidth();
                 if (w < 50.0 || H < 50.0) {
                     //头像太小
//                        showSnackBar(cover, R.string.collection_edit_cover_small);
                     return;
                 }
                 hasChange = true;
                 cover.setImageBitmap(map);//设置图片
                 //将图片的地址存入 实体类
                 collectionBean.setCoverUrl(photoUtil.getUri().getPath());
             }
             break;
     }
    }

    //未完
}
