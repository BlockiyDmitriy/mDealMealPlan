package com.example.clientwithbottmmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clientwithbottmmenu.ui.calendar.CalendarFragment;
import com.example.clientwithbottmmenu.ui.diary.DiaryFragment;
import com.example.clientwithbottmmenu.ui.profile.ProfileFragment;
import com.example.clientwithbottmmenu.ui.statistic.StatisticActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FrameLayout container;
    FragmentManager myFragmentManager;

    DiaryFragment diaryFragment;
    ProfileFragment profileFragment;
    CalendarFragment calendarFragment;

    Bundle bundle;
    FragmentTransaction fragmentTransaction;

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        container = (FrameLayout) findViewById(R.id.container);

        myFragmentManager = getSupportFragmentManager();
        diaryFragment = new DiaryFragment();
        profileFragment = new ProfileFragment();
        calendarFragment = new CalendarFragment();
        if (savedInstanceState == null) {

            FragmentTransaction fragmentTransaction = myFragmentManager
                    .beginTransaction();

            fragmentTransaction.add(R.id.container, diaryFragment);
            fragmentTransaction.commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragmentTransaction = myFragmentManager
                .beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_diary: {
                bundle = new Bundle();
                diaryFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.container, diaryFragment);
                break;
            }
            case R.id.action_profile:{
                bundle = new Bundle();
                profileFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.container, profileFragment);
                break;
            }
            case R.id.action_calendar:{
                bundle = new Bundle();
                calendarFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.container, calendarFragment);
                break;
            }
        }
        fragmentTransaction.commit();
        return false;
    }

    //TODO:
//    https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/560-urok-27-navigation-navigationui.html
//    https://habr.com/ru/post/416025/
    public void onNavigationItemSelected(int i) {
        if (i == 1) {
            Intent intent = new Intent(this, StatisticActivity.class);
            startActivity(intent);
        }
    }
}