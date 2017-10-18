package com.zqw.fastsearch;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WrapContentHeightViewPager mViewPager;
    private TabLayout mTabLayout;
    private EditText et_search;

    private String[] tabTitle;
    private List<TabFragment> tabFragments;
    private String url = null;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        tabTitle = new String[]{"百度","知乎","淘宝","京东","微信","微博","豆瓣","网盘","地图","B站","快递"};

        Toolbar toolbat = findViewById(R.id.toolbar);
        setSupportActionBar(toolbat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }




        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.img_menu);
        ab.setDisplayHomeAsUpEnabled(true);


        mViewPager = findViewById(R.id.vp_content);
        mTabLayout = findViewById(R.id.tl_tab);


        et_search = findViewById(R.id.et_search);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = Constant.BAIDU + et_search.getText().toString();
                Toast.makeText(MainActivity.this, et_search.getText().toString(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this, WebActivity.class));
//                if(onClickNetListener != null){
//                    onClickNetListener.getWebNet("百度", et_search.getText().toString());
//                    Log.i("TAG", "回调成功baidu");
//                }


                tabFragments.get(mPosition).getWebViewNet(mTabLayout.getTabAt(mPosition).getText().toString(), et_search.getText().toString());

            }
        });

        setupViewPager();

    }



    /**
     * 设置ViewPager和TabLayout
     */
    private void setupViewPager() {
        tabFragments = new ArrayList<>();

        for(int i=0; i<tabTitle.length; i++){

            mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[i]));    //添加标签
            tabFragments.add(new TabFragment(this, i));     //添加Fragment

        }
        mTabLayout.setOnTabSelectedListener(new MyOnTabSelectedListener());
        //mTabLayout.getTabAt(0).select();
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), tabFragments, tabTitle);
   //     MyPagerFragmentAdapter adapter = new MyPagerFragmentAdapter();
        mTabLayout.setTabsFromPagerAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener());

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


//    private void setAction(){
//
//
//    }
    class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            tabFragments.get(position).getWebViewNet(mTabLayout.getTabAt(position).getText().toString(), et_search.getText().toString());
        }
        @Override
        public void onPageSelected(int position) {
            mPosition = position;

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }

     }

    class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            mViewPager.setCurrentItem(tab.getPosition());
//
//            if(onClickNetListener != null){
//                onClickNetListener.getWebNet(tab.getText().toString(), et_search.getText().toString());
//                Log.i("TAG", "回调成功:" + tab.getText().toString());
//            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    class MyPagerFragmentAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(View container, int position) {

            TabFragment tabFragment = tabFragments.get(position);

            container = tabFragment.getView();

            return container;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private List<TabFragment> tabFragments;
        private String[] tabTitle;



        public MyPagerAdapter(FragmentManager fm, List<TabFragment> tabFragments, String[] tabTitle) {
            super(fm);
            this.tabFragments = tabFragments;
            this.tabTitle = tabTitle;
        }

        @Override
        public Fragment getItem(int position) {
            return (tabFragments == null || tabFragments.size() == 0) ? null : tabFragments.get(position);
        }


        @Override
        public int getCount() {
            return tabFragments == null ? 0 : tabFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }


    public interface OnClickNetListener{
        //点击搜索时回调这个方法
        public void getWebNet(String Title, String searchKey);
    }

    private OnClickNetListener onClickNetListener;

    public void setOnClickNetListener(OnClickNetListener l){
        this.onClickNetListener = l;
    }



}
