package com.bank.root.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by root on 15-2-16.
 */
public class Second extends ActionBarActivity {
    private CheckBox ck1;
    private CheckBox ck2;
    private String a = "";
    private String b = "";
    private RadioGroup RG;
    private TextView Tv;
    private Button bt2;
    private Button photo;
    private ImageView imgV2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引入布局文件;
        setContentView(R.layout.activity_second);

        ck1 = (CheckBox) findViewById(R.id.checkBox);
        ck2 = (CheckBox) findViewById(R.id.checkBox2);
        ck1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                a += (isChecked ? ck1.getText().toString() : "") + " ";
            }
        });

        ck2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                a += (isChecked ? ck2.getText().toString() : "") + " ";
            }
        });

        RG = (RadioGroup) findViewById(R.id.randioGroup);

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        b = "in 0~10";
                        break;
                    case R.id.radioButton2:
                        b = "in 11~20";
                        break;
                    case R.id.radioButton3:
                        b = "in above 20";
                    default:
                        break;
                }
            }
        });


        Tv = (TextView) findViewById(R.id.textView3);
        Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data1 = new Intent();
                data1.putExtra("data", a + b);
                setResult(2, data1);
                finish();

            }
        });

        bt2 = (Button) findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.litreily.activity_map"));
            }
        });

        photo = (Button) findViewById(R.id.button4);
        imgV2 = (ImageView) findViewById(R.id.imageView2);
        // get SD path
        photo.setOnClickListener(takePhoto);

    }
    View.OnClickListener takePhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/Pictures/test", "temp.jpg")));
            startActivityForResult(intent,1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // 设置文件保存路径
            File picture = new File(
                    "/sdcard/Pictures/test/temp.jpg");
               Bitmap bitmap1 = BitmapFactory.decodeFile("/sdcard/Pictures/test/temp.jpg");
            imgV2.setImageBitmap(bitmap1);
         //   startPhotoZoom(Uri.fromFile(picture));
        }
        if (requestCode == 3) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                imgV2.setImageBitmap(photo); //把图片显示在ImageView控件上
            }

        }

    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

}
