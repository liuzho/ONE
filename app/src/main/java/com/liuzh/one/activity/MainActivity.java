package com.liuzh.one.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.fragment.HomeFragment;
import com.liuzh.one.view.AppToolbar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String INTENT_KEY_LIST_ID = "list_id";
    private ArrayList<Fragment> mFragments;//all fragment
    private AppToolbar toolbar;
    private FragmentManager mFragmentManager;//fragment manager


    public static void start(Context context, ArrayList<String> value) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putStringArrayListExtra(INTENT_KEY_LIST_ID, value);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        //init toolbar
        toolbar = (AppToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbarTitle(getString(R.string.app_name));
        toolbar.setLeftDrawable(getResources().getDrawable(R.drawable.user));
        toolbar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("用户");
            }
        });
        toolbar.setRightRDrawable(getResources().getDrawable(R.drawable.search));
        toolbar.setRightRClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("搜索");
            }
        });
        setSupportActionBar(toolbar);
        //view data
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_fragment, mFragments.get(0));
        transaction.commit();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 创建fragment实例
     */
    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
    }

}
