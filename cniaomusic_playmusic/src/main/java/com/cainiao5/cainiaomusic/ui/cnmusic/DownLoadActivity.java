package com.cainiao5.cainiaomusic.ui.cnmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.CommonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.task.XExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import magicasakura.utils.ThemeUtils;

public class DownLoadActivity extends AppCompatActivity implements XExecutor.OnAllTaskEndListener {


    Toolbar toolbar;
    ViewPager viewPager;
    private OkDownload okDownload;

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, DownLoadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        initOkGo();

        initToolbar();

        initViewPager();

        downloadMusic();

    }

    private void initOkGo() {
        okDownload = OkDownload.getInstance();
        String path = Environment.getExternalStorageDirectory().getPath() + "/download";
        okDownload.setFolder(path);
        okDownload.getThreadPool().setCorePoolSize(5);
        okDownload.addOnAllTaskEndListener(this);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        if (viewPager != null) {
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(2);
        }

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(R.color.text_color, ThemeUtils.getThemeColorStateList(this, R.color.theme_color_primary).getDefaultColor());
        tabLayout.setSelectedTabIndicatorColor(ThemeUtils.getThemeColorStateList(this, R.color.theme_color_primary).getDefaultColor());

        new Thread(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        }).start();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, CommonUtils.getStatusHeight(DownLoadActivity.this), 0, 0);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("下载管理");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(DownloadFragment.newInstance("/storage/emulated/0/remusic", false, null), "单曲");
        adapter.addFragment(new DownloadFragment(), "下载中");
        viewPager.setAdapter(adapter);
    }


    /***
     * 下载音乐
     */
    private void downloadMusic() {
        GetRequest<File> request = OkGo.<File>get("url");
        OkDownload.request("songid",request)
                .save()
                .register(new DownloadListener("songid") {
                    @Override
                    public void onStart(Progress progress) {

                    }

                    @Override
                    public void onProgress(Progress progress) {

                    }

                    @Override
                    public void onError(Progress progress) {

                    }

                    @Override
                    public void onFinish(File file, Progress progress) {

                    }

                    @Override
                    public void onRemove(Progress progress) {

                    }
                });
    }



    //所有任务监听
    @Override
    public void onAllTaskEnd() {

    }


    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okDownload.removeOnAllTaskEndListener(this);
    }
}
