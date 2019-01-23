package lottomaster.lunastratos.com.lottomaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import lottomaster.lunastratos.com.lottomaster.adapter.ContentsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    private Context mContext;
    private ViewPager mViewPager;
    private ContentsPagerAdapter mContentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        HashMap map = (HashMap) intent.getExtras().get("HashMap");

        mContext = getApplicationContext();

        mTabLayout = findViewById(R.id.layout_tab); // TabLayout
        mViewPager = (ViewPager) findViewById(R.id.pager_content);

        menuTextSetup(); //mTabLayout addTab


        mContentPagerAdapter = new ContentsPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), map);

        mViewPager.setAdapter(mContentPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

    }


    //메뉴바 텍스트 만들기
    private void menuTextSetup() {

        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.home));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.view));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.calender));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.info));

        //mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("내로또 확인")));
    }

}
