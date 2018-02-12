package com.arkhipenkapiotr.demo.shopdemo.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arkhipenkapiotr.demo.shopdemo.R;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null){
            fragment = new ItemsListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer,fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }
}
