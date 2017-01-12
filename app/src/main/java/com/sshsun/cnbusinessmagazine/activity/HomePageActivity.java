package com.sshsun.cnbusinessmagazine.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sshsun.cnbusinessmagazine.R;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sshsun.cnbusinessmagazine.activity.base.BaseFragment;
import com.sshsun.cnbusinessmagazine.activity.fragment.ActivityFragment;
import com.sshsun.cnbusinessmagazine.activity.fragment.AmuseFragment;
import com.sshsun.cnbusinessmagazine.activity.fragment.FindFragment;
import com.sshsun.cnbusinessmagazine.activity.fragment.NewestFragment;


/**
 * 实现ViewPager与Fragment的联合使用以及ViewPager的预加载问题处理
 */
public class HomePageActivity extends AppCompatActivity {
    private String[] mTabTitles = new String[]{};
    private BaseFragment[] fragments = {new NewestFragment(), new ActivityFragment(),
            new AmuseFragment(), new FindFragment(), new NewestFragment(), new NewestFragment(), new NewestFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        mTabTitles = getResources().getStringArray(R.array.tab_titles);

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 方案二：页面选中时才去加载数据
                // BaseFragment fragment = fragments[position];
                // fragment.initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        // System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        Toast.makeText(com.sshsun.cnbusinessmagazine.activity.HomePageActivity.this, "吓死宝宝了, 还好不是真退出！！！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确认退出?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("确认", dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }


    private class FragmentAdapter extends FragmentPagerAdapter {
        // FragmentPagerAdapter与FragmentStatePagerAdapter区别
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTabTitles != null) {
                return mTabTitles[position];
            }
            return super.getPageTitle(position);
        }

    }
}