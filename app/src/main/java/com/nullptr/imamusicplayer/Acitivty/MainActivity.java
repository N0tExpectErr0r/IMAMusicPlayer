package com.nullptr.imamusicplayer.Acitivty;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nullptr.imamusicplayer.Fragment.AlbumFragment;
import com.nullptr.imamusicplayer.Fragment.MusicListFragment;
import com.nullptr.imamusicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar("IMA音乐");   //初始化ActionBar

        initTab();

    }


    public void initTab(){
        MusicListFragment mMusicListFragment = MusicListFragment.newInstance(null);
        mFragments.add(mMusicListFragment);
        AlbumFragment albumFragment = AlbumFragment.newInstance();
        mFragments.add(albumFragment);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

        mTabLayout = (TabLayout)findViewById(R.id.Tab);

        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    public Toolbar initToolbar(CharSequence title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        return toolbar;
    }
}
