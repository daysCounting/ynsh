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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import Fragments.TabFragmentOne;

/**
 * Created by root on 15-2-16.
 */
public class GetInformation extends ActionBarActivity {
    private TextView Tv;
    private Button bt2;
    private Button photo;
    private ImageView imgV2;
    private String use;
    private File picture;
    private final int tPhoto = 1;
    private final int cutPhoto = 2;

    private static String localTempImageFileName = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引入布局文件;
        setContentView(R.layout.activity_information);
        Intent intent = getIntent();
        use = intent.getStringExtra("Name");

        Tv = (TextView) findViewById(R.id.textView3);

        Tv.setText("您现在在:" + intent.getStringExtra("LocalAddress") +
                        "  Longitude: " + intent.getDoubleExtra("Longitude", -1) +
                        "  Latitude: " + intent.getDoubleExtra("Latitude", -1) +
                        "       Name: " + intent.getStringExtra("Name")

        );

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
        picture = new File(
                "/sdcard/Pictures/test/" + use + ".jpg");
        if (picture.exists()) {
            Bitmap bitmap1 = BitmapFactory.decodeFile("/sdcard/Pictures/test/" + use + ".jpg");
            imgV2.setImageBitmap(bitmap1);
        }

    }

    View.OnClickListener takePhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/Pictures/test/", use + ".jpg")));
            startActivityForResult(intent2, tPhoto);
//            String status = Environment.getExternalStorageState();
//            if (status.equals(Environment.MEDIA_MOUNTED)) {
//
//                    localTempImageFileName = "";
//                    localTempImageFileName = String.valueOf((new Date())
//                            .getTime()) + ".jpg";
//                    File filePath =new File("/sdcard/Pictures/test/");
//                    if (!filePath.exists()) {
//                        filePath.mkdirs();
//                    }
//                    Intent intent = new Intent(
//                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    File f = new File(filePath, localTempImageFileName);
//                    // localTempImgDir和localTempImageFileName是自己定义的名字
//                    Uri u = Uri.fromFile(f);
//                    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
//                    startActivityForResult(intent, tPhoto);
//
//            }
            TabFragmentOne tabFragmentTemp = new TabFragmentOne();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case tPhoto: {
                    // 设置文件保存路径
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                     BitmapFactory.decodeFile("/sdcard/Pictures/test/" + use + ".jpg", options);
                    options.inSampleSize = computeSampleSize(options, -1, 800* 600);
                    options.inJustDecodeBounds = false;
                    Bitmap  bitmap1 = BitmapFactory.decodeFile("/sdcard/Pictures/test/" + use + ".jpg", options);
                    BufferedOutputStream stream = null;
                    try {
                        stream = new BufferedOutputStream(
                                new FileOutputStream(picture));
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imgV2.setImageBitmap(bitmap1);
//                          startPhotoZoom(Uri.fromFile(picture));

                }
                break;
                case cutPhoto:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                        imgV2.setImageBitmap(photo); //把图片显示在ImageView控件上
                    }
                    break;


            }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }


    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
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
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);

        startActivityForResult(intent, cutPhoto);
    }

}
