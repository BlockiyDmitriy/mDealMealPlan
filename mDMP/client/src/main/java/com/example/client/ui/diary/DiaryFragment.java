package com.example.client.ui.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.NavigationUI;

import com.example.client.MainActivity;
import com.example.client.R;
import com.example.client.ui.statistic.StatisticActivity;
import com.example.client.ui.statistic.StatisticFragment;

public class DiaryFragment extends Fragment {

    private DiaryViewModel diaryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_diary, container, false);

        final TextView textView = root.findViewById(R.id.text_diary);
        diaryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    //http://developer.alexanderklimov.ru/android/theory/fragment-replace.php
    // обработчик нажитий на меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit: {
                break;
            }
            case R.id.statistic: {
                ((MainActivity)getActivity()).onNavigationItemSelected(1);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // Вызов меню
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.diary, menu);
    }
}
