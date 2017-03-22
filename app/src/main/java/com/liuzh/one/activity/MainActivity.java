package com.liuzh.one.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.liuzh.one.R;
import com.liuzh.one.fragment.HomeFragment;
import com.liuzh.one.utils.DensityUtil;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String INTENT_KEY_LIST_ID = "list_id";
    private ArrayList<Fragment> mFragments;//all fragment
    private Toolbar toolbar;
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

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
