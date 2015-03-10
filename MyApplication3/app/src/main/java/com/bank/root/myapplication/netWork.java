package com.bank.root.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by root on 15-3-9.
 */
public class netWork {
    public static Boolean net =false ;


    public static boolean netIsUseful(Context context) {
//        final Handler mHandler = new Handler();
        final Context ctx = context;
//               mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                // 在此处添加执行的代码
//
//                if (ctx != null) {
//                    ConnectivityManager mConnectivityManager = (ConnectivityManager) ctx
//                            .getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//                    if (mNetworkInfo != null) {
//                        net = mNetworkInfo.isAvailable();
//                    }
//                }
//                net = false;
//                mHandler.postDelayed(this, 2000);// 50是延时时长
//            }
//        });
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(2000);
                    if (ctx != null) {
                    ConnectivityManager mConnectivityManager = (ConnectivityManager) ctx
                            .getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                    if (mNetworkInfo != null) {
                     net = mNetworkInfo.isAvailable();
                    }
                }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
            return net;

    }


}
