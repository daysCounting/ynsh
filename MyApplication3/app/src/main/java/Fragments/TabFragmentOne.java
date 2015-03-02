package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bank.root.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabFragmentOne extends Fragment {
    MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private boolean isFirstIn = true;
    private Context context;
    private double Latitude;
    private double Longitude;
    private ImageButton bt;
    private ListView listView1;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> list = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        this.context = getActivity().getApplicationContext();
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initLocation();
        dealMap();
        bt = (ImageButton) view.findViewById(R.id.imageButton);
        bt.setOnClickListener(Listener1);

        listView1 = (ListView) view.findViewById(R.id.listView);
        getSimpleAdapter();
        listView1.setAdapter(simpleAdapter);
        return view;
    }

    private void getSimpleAdapter() {
        list.clear();
        Map map = new HashMap<String, Object>();
        map.put("title", "find");
        map.put("content", "Content " + 1);
        list.add(map);
        for (int i = 1; i < 20; i++) {
            Map map2 = new HashMap<String, Object>();
            map2.put("title", null);
            map2.put("content", "Content " + i);
            list.add(map2);
        }


        simpleAdapter = new SimpleAdapter(getActivity().getApplicationContext(), list, R.layout.fragment_one_listview
                , new String[]{"title", "content"}, new int[]{R.id.textTitle, R.id.textContent});


    }

    public void dealMap() {
        int childCount = mMapView.getChildCount();
        View zoom = null;
        for (int i = 0; i < childCount; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                zoom = child;
                break;
            }
        }
        zoom.setVisibility(View.GONE);
        int count = mMapView.getChildCount();
        View scale = null;
        for (int i = 0; i < count; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                scale = child;
                break;
            }
        }
        scale.setVisibility(View.GONE);

        // 隐藏指南针
//        mUiSettings = mBaiduMap.getUiSettings();
//        mUiSettings.setCompassEnabled(true);
        // 删除百度地图logo
        mMapView.removeViewAt(1);
    }


    View.OnClickListener Listener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LatLng latlng = new LatLng(Latitude, Longitude);
            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
            mBaiduMap.setMapStatus(msu);
            msu = MapStatusUpdateFactory.newLatLng(latlng);
            mBaiduMap.animateMapStatus(msu);


        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();

    }

    @Override
    public void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(bdLocation.getRadius())//
                    .latitude(bdLocation.getLatitude())//
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(data);

            Latitude = bdLocation.getLatitude();
            Longitude = bdLocation.getLongitude();
            if (isFirstIn) {

                LatLng latlng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latlng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;

                Toast.makeText(context, "你现在在: " + bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
                // tv5.setText(bdLocation.getAddrStr());
            }
        }
    }
}
