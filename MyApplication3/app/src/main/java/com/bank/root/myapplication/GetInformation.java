package com.bank.root.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.root.myapplication.bean.FormFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Fragments.TabFragmentOne;
import Util.SocketHttpRequester;

/**
 * Created by root on 15-2-16.
 */
public class GetInformation extends ActionBarActivity {
    private TextView Tv;
    private Button bt2;
    private Button photo;
    private ImageView imgV2;
    private String use;
    private double Longitude;
    private double Latitude;
    private Handler handler;
    private Context context;

    private File picture;
    private final int tPhoto = 1;
    private final int cutPhoto = 2;

    //private static String localTempImageFileName = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引入布局文件;
        context = getApplicationContext();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                .build());
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
        bt2.setOnClickListener(uploda);

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

    View.OnClickListener uploda = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            handler = new Handler();
            handler.post(runnable);


        }
    };
    Runnable runnable = new Runnable() {

        public void run() {


            uploadFile();
            //handler.postDelayed(runnable, 50000);
        }

    };

    public void uploadFile() {
        String url1 = "http://10.46.64.4:80/ashx/AppWebServer.ashx";
        URL url = null;
        try {
            url = new URL(url1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String, Object> params = new HashMap<>();
//        params.put("PersonName", use);
//        params.put("TellerCode", "8570780");
//        params.put("Latitude", Latitude);
//        params.put("Longitude", Longitude);
//        params.put("fileName", picture.getName());

        FormFile formfile = new FormFile(picture.getName(), picture, "image", "application/octet-stream");


        try {
            if(SocketHttpRequester.post(url, params, formfile)){
                Toast.makeText(context,"Congratulation",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Sorry,upload failed",Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.i(".....!", e.toString());
            e.printStackTrace();
        }

    }

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
                    options.inSampleSize = computeSampleSize(options, -1, 800 * 600);
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap1 = BitmapFactory.decodeFile("/sdcard/Pictures/test/" + use + ".jpg", options);
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

    //设置压缩图片比例并微调
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

    //根据原图大小设置相关比

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


}
