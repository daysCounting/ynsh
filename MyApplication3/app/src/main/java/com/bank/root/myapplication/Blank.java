package com.bank.root.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import com.baidu.mapapi.SDKInitializer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import Fragments.TabFragmentFour;
import Fragments.TabFragmentOne;
import Fragments.TabFragmentThree;
import Fragments.TabFragmentTwo;

public class Blank extends ActionBarActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
    private ConnectionChangeReceiver myReceiver;


    private TabFragmentOne tabFragmentOne;
    private TabFragmentTwo tabFragmentTwo;
    private TabFragmentThree tabFragmentThree;
    private TabFragmentFour tabFragmentFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引入布局文件;
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_blank);
        setOverflowButtonAlways();
        registerReceiver();
        initView();
        initDatas();
        mViewPager.setAdapter(mAdapter);
        initEvent();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager1);
        ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
        mTabIndicators.add(one);
        ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
        mTabIndicators.add(two);
        ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
        mTabIndicators.add(three);
        ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
        mTabIndicators.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f, 0);

    }

    private void initDatas() {
        //Fragment one ;
        tabFragmentOne = new TabFragmentOne();
//            Bundle bundle = new Bundle();
//            bundle.putString(TabFragmentOne.TITLE, mTitles[0]);
//            tabFragmentOne.setArguments(bundle);

        mTabs.add(tabFragmentOne);
        tabFragmentTwo = new TabFragmentTwo();
        mTabs.add(tabFragmentTwo);
        tabFragmentThree = new TabFragmentThree();
        mTabs.add(tabFragmentThree);
        tabFragmentFour = new TabFragmentFour();
        mTabs.add(tabFragmentFour);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_blank, menu);
        return true;
    }

    ;
//    public void showPopup(View v) {
//        PopupMenu popup = new PopupMenu(this, v);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_map, popup.getMenu());
//        popup.show();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private void setOverflowButtonAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKey = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return super.onMenuOpened(featureId, menu);
    }

    private void initEvent() {

        mViewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        clickTab(v);

    }

    /**
     * 点击Tab按钮
     *
     * @param v
     */
    private void clickTab(View v) {
        resetOtherTabs();

        switch (v.getId()) {
            case R.id.id_indicator_one:
                mTabIndicators.get(0).setIconAlpha(1.0f, 0);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_two:
                mTabIndicators.get(1).setIconAlpha(1.0f, 0);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_three:
                mTabIndicators.get(2).setIconAlpha(1.0f, 0);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_indicator_four:
                mTabIndicators.get(3).setIconAlpha(1.0f, 0);
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0, -1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithText left = mTabIndicators.get(position);
            ChangeColorIconWithText right = mTabIndicators.get(position + 1);
            resetOtherTabs();
            left.setIconAlpha(1 - positionOffset, 1);
            right.setIconAlpha(positionOffset, 0);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectionChangeReceiver();
        this.registerReceiver(myReceiver, filter);
    }

    private void unregisterReceiver() {
        this.unregisterReceiver(myReceiver);
    }

    public TabFragmentOne getTabFragmentOne() {
        return tabFragmentOne;
    }

}
