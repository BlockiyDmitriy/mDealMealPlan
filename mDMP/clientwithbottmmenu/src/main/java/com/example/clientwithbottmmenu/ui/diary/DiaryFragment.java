package com.example.clientwithbottmmenu.ui.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.clientwithbottmmenu.R;
import com.example.clientwithbottmmenu.ui.statistic.StatisticFragment;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment implements View.OnClickListener {
//TODO:
//    http://developer.alexanderklimov.ru/android/views/cardview.php
//    создание cardView или recyclerView
//    https://www.fandroid.info/urok-3-sozdanie-spiskov-kartochek-v-android-prilozheniyah-material-design/
//    https://metanit.com/java/android/5.11.php
//    https://www.fandroid.info/android-uchebnik-sozdanie-recyclerview-cardview-i-menyu-optsij-dlya-elementa-recyclerview/ Переделать на это

    private DiaryAddItemFragment mDiaryAddItemFragment;
    private StatisticFragment mStatisticFragment;

    private RecyclerView mRecyclerView;
    private DataAdapterRecyclerView mAdapter;
    private DiaryViewModel mDiaryViewModel;
    private List<DiaryProduct> products = new ArrayList<>();

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_diary, container, false);


        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_diary);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        TODO: Проверить создавался ли раньше этот фрагмент и если создавался, то добавить адаптер
        mAdapter = new DataAdapterRecyclerView(getContext(), products);
        mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);

        return root;
    }

    //TODO:
//     http://developer.alexanderklimov.ru/android/theory/fragment-replace.php
//     обработчик нажитий на меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistic: {
                mStatisticFragment = new StatisticFragment();
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container, mStatisticFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            }
            case R.id.add: {
                mDiaryAddItemFragment = new DiaryAddItemFragment();
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container, mDiaryAddItemFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    // обновление текстового поля
    public void setText(List<DiaryProduct> item) {
        products = item;
    }
    //TODO:
    // https://www.fandroid.info/sohranenie-dannyh-v-sql-bazu-dannyh/#2 база данных
    // https://www.tutorialspoint.com/android/android_sqlite_database.htm
    // https://habr.com/ru/post/336196/

    @Override
    public void onClick(View v) {

    }
    // Вызов меню

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.diary, menu);
    }
}
