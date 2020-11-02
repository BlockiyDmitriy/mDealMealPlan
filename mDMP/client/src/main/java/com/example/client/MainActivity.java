package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.client.ui.diary.DiaryFragment;
import com.example.client.ui.statistic.StatisticActivity;
import com.example.client.ui.statistic.StatisticFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//https://ru.stackoverflow.com/questions/753013/android-%D0%9F%D0%B5%D1%80%D0%B5%D1%85%D0%BE%D0%B4-%D0%BC%D0%B5%D0%B6%D0%B4%D1%83-%D1%84%D1%80%D0%B0%D0%B3%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D0%BC%D0%B8
//http://developer.alexanderklimov.ru/android/theory/fragment-replace.php
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    StatisticFragment fStatistic;
    FragmentManager myFragmentManager;
    DiaryFragment fDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFragmentManager = getSupportFragmentManager();
        fStatistic = new StatisticFragment();
        fDiary = new DiaryFragment();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_diary, R.id.nav_profile, R.id.nav_calendar)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
//    https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/560-urok-27-navigation-navigationui.html
    public void onNavigationItemSelected(int item) {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        Bundle bundle = new Bundle();
        if (item == 1) {
            fStatistic.setArguments(bundle);
            fragmentTransaction.replace(R.id.constatin_fragment, fStatistic);
//            Intent i = new Intent(this, StatisticActivity.class);
//            startActivity(i);
        }
        fragmentTransaction.commit();
    }
}
