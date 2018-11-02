package com.example.testing.cniao5shop;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.testing.cniao5shop.fragment.FragmentFactory;
import com.example.testing.cniao5shop.widget.CnToolbar;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp_content;
    private TabLayout tab_list;
    private String[] arr;
    private MyAdapter myAdapter;
    private CnToolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        TabLayout.Tab home = tab_list.getTabAt(0);
        TabLayout.Tab hot = tab_list.getTabAt(1);
        TabLayout.Tab category = tab_list.getTabAt(2);
        TabLayout.Tab cart = tab_list.getTabAt(3);
        TabLayout.Tab mine = tab_list.getTabAt(4);
        home.setIcon(getResources().getDrawable(R.drawable.selector_icon_home));
        hot.setIcon(getResources().getDrawable(R.drawable.selector_icon_hot));
        category.setIcon(getResources().getDrawable(R.drawable.selector_icon_category));
        cart.setIcon(getResources().getDrawable(R.drawable.selector_icon_cart));
        mine.setIcon(getResources().getDrawable(R.drawable.selector_icon_mine));
    }

    private void initData() {
        arr=new String[]{"主页","热卖","分类","购物车","我的"};
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        tab_list = (TabLayout) findViewById(R.id.tab_list);
         toolbar = (CnToolbar)findViewById(R.id.toolbar);
        tab_list.setTabTextColors(Color.BLACK,Color.RED);
         myAdapter = new MyAdapter(getSupportFragmentManager());
         vp_content.setAdapter(myAdapter);
         tab_list.setupWithViewPager(vp_content);
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        public CharSequence getPageTitle(int position) {
            return arr[position];
        }

        @Override
        public int getCount() {
            return arr.length;
        }
    }
}
