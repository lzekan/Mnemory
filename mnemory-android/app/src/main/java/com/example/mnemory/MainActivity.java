package com.example.mnemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment mainFragment;
    private Fragment historyFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        mainFragment = new MainFragment();
        historyFragment = new HistoryFragment();
        profileFragment = new ProfileFragment();

        loadFragment(new MainFragment());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, mainFragment).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_main) {
                    loadFragment(new MainFragment());
                    return true;
                } else if (itemId == R.id.nav_history) {
                    loadFragment(new HistoryFragment());
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    loadFragment(new ProfileFragment());
                    return true;
                }

                return false;
            };

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();

            return true;
        }

        return false;
    }



}

