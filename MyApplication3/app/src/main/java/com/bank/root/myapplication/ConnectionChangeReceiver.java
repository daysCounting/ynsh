package com.bank.root.myapplication;

/**
 * Created by root on 15-3-9.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author daysCounting
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    private static boolean net;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Toast.makeText(context,
                    "亲，网络连了么？", Toast.LENGTH_LONG)
                    .show();
            net = false;
            //改变背景或者 处理网络的全局变量
        } else {
           net =true;
            //改变背景或者 处理网络的全局变量
        }
    }
    public static boolean isNet(){
        return  net;
    }
}