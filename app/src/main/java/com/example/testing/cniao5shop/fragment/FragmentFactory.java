package com.example.testing.cniao5shop.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;

public class FragmentFactory {

    private static HashMap<Integer,Fragment> mFragmentMap=new HashMap<Integer, Fragment>();
    public static Fragment createFragment(int pos){
        Fragment fragment=mFragmentMap.get(pos);
        if (fragment==null){
            switch (pos){
                case 0:
                    fragment=new HomeFragment();
                    break;
                case 1:
                    fragment=new HotFragment();
                    break;
                case 2:
                    fragment=new CategoryFragment();
                    break;
                case 3:
                    fragment=new CartFragment();
                    break;
                case 4:
                    fragment=new MineFragment();
                    break;
            }
            mFragmentMap.put(pos,fragment);//保存在集合中
        }
        return fragment;
    }
}
