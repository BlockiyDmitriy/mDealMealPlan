package com.example.client.ui.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client.MainActivity;
import com.example.client.R;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment {

//TODO:
//    http://developer.alexanderklimov.ru/android/views/cardview.php
//    создание cardView или recyclerView
//    https://www.fandroid.info/urok-3-sozdanie-spiskov-kartochek-v-android-prilozheniyah-material-design/
//    https://metanit.com/java/android/5.11.php
//    https://www.fandroid.info/android-uchebnik-sozdanie-recyclerview-cardview-i-menyu-optsij-dlya-elementa-recyclerview/ Переделать на это

    private RecyclerView mRecyclerView;
    private DataAdapterRecyclerView mAdapter;
    private DiaryViewModel mDiaryViewModel;
    private List<DiaryProduct> products;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_diary, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_diary);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        products = new ArrayList<>();

        //Generate sample data
        for (int i = 0; i<10; i++) {
            products.add(new DiaryProduct("Item " + (i + 1), "Welcome to Torisan channel, this is description of item " + (i+1), 10, 50, 20));
        }

        mAdapter = new DataAdapterRecyclerView(getContext(), products);
        mRecyclerView.setAdapter(mAdapter);

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
                ((MainActivity) getActivity()).onNavigationItemSelected(1);
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
