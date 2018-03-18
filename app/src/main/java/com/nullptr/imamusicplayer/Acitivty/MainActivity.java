package com.nullptr.imamusicplayer.Acitivty;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nullptr.imamusicplayer.Data.AlbumLab;
import com.nullptr.imamusicplayer.Data.Mp3Lab;
import com.nullptr.imamusicplayer.Fragment.AlbumFragment;
import com.nullptr.imamusicplayer.Fragment.BottomFragment;
import com.nullptr.imamusicplayer.Fragment.MusicListFragment;
import com.nullptr.imamusicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MusicListFragment mMusicListFragment;
    private AlbumFragment albumFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askPermission();
        initToolbar("IMA音乐");   //初始化ActionBar

        initTab();
    }


    @Override
    protected void onStart() {
        super.onStart();
        BottomFragment bottomFragment = new BottomFragment();
        android.support.v4.app.FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.bottom,bottomFragment);
        transaction.commit();
    }


    public void askPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if (!(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    Toast.makeText(this,"拒绝了权限，无法使用该程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public void initTab(){
        mMusicListFragment = MusicListFragment.newInstance(null);
        mFragments.add(mMusicListFragment);
        albumFragment = AlbumFragment.newInstance();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomFragment.updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_name:
                Mp3Lab.get(this).sort_title();
                AlbumLab.get(this).sort_title();
                mMusicListFragment.notifyChange();
                albumFragment.notifyChange();
                break;
            case R.id.sort_singer:
                Mp3Lab.get(this).sort_artist();
                AlbumLab.get(this).sort_artist();
                mMusicListFragment.notifyChange();
                albumFragment.notifyChange();
                break;
        }
        return true;
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
