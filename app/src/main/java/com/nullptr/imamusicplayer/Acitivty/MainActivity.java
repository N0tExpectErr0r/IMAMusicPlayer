package com.nullptr.imamusicplayer.Acitivty;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nullptr.imamusicplayer.Data.AlbumLab;
import com.nullptr.imamusicplayer.Data.Mp3Lab;
import com.nullptr.imamusicplayer.Fragment.AlbumFragment;
import com.nullptr.imamusicplayer.Fragment.BottomFragment;
import com.nullptr.imamusicplayer.Fragment.MusicListFragment;
import com.nullptr.imamusicplayer.R;
import com.nullptr.imamusicplayer.Receiver.SleepReceiver;
import com.nullptr.imamusicplayer.Service.SleepService;
import com.nullptr.imamusicplayer.Utils.ToastUtile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MusicListFragment mMusicListFragment;
    private AlbumFragment albumFragment;

    private SleepReceiver sleepReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askPermission();
        initToolbar("IMA音乐");   //初始化ActionBar

        sleepReceiver = new SleepReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.nullptr.imamusicplayer.sleep");
        registerReceiver(sleepReceiver, filter);
        initTab();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sleepReceiver);
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
            case R.id.sleep:
                showSleepDialog();
                break;
        }
        return true;
    }

    public void showSleepDialog(){
        AlertDialog.Builder sleepDialog = new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_sleep,null);
        sleepDialog.setTitle("请输入要定时关闭的时间:");
        sleepDialog.setView(dialogView);
        sleepDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int time;
                EditText edit_text = (EditText) dialogView.findViewById(R.id.edit_text);
                if (!edit_text.getText().toString().equals("")) {
                    time = 60 * Integer.parseInt(edit_text.getText().toString());
                }else {
                    time = 0;
                }
                ToastUtile.showText(MainActivity.this,"定时任务已开始，时间为"+time+"分钟");
                Intent intent = new Intent(MainActivity.this,SleepService.class);
                intent.putExtra("sleep_time",time);
                startService(intent);
            }
        });
        sleepDialog.setNegativeButton("取消",null);
        sleepDialog.show();
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
