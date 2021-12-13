package com.example.clientapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.clientapp.Authentication.Prefs;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import com.example.clientapp.ui.main.SectionsPagerAdapter;
import com.example.clientapp.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.logout:
                Prefs.getInstance(getApplicationContext()).clear();
                stopService(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.refresh:
                stopService(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}