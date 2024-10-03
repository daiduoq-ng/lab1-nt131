package com.example.duongnvdssupperclock;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import adapter.AdapterViewPager;
import fragment.FragmentAlarm;
import fragment.FragmentClock;
import fragment.FragmentTimer;


public class MainActivity extends AppCompatActivity {
    ViewPager2 pagerMain;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Setting up window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize BottomNavigationView and ViewPager2
        bottomNav = findViewById(R.id.bottomNav);
        pagerMain = findViewById(R.id.pagerMain);

        // Add fragments to the list
        fragmentArrayList.add(new FragmentClock());
        fragmentArrayList.add(new FragmentTimer());
        fragmentArrayList.add(new FragmentAlarm());

        // Set up the adapter for the ViewPager2
        AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);
        pagerMain.setAdapter(adapterViewPager);

        // Set listener for ViewPager2 page changes
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNav.setSelectedItemId(getMenuIdFromPosition(position));
                super.onPageSelected(position);
            }
        });


        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itClock:
                        pagerMain.setCurrentItem(0);
                        break;
                    case R.id.itAlarm:
                        pagerMain.setCurrentItem(1);
                        break;
                    case R.id.itTimer:
                        pagerMain.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

    // Method to get the menu ID from position
    private int getMenuIdFromPosition(int position) {
        switch (position) {
            case 0:
                return R.id.itClock;
            case 1:
                return R.id.itAlarm;
            case 2:
                return R.id.itTimer;
            default:
                return -1; // Invalid position
        }
    }
}
