package com.example.clientwithbottmmenu.ui.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.clientwithbottmmenu.MainActivity;
import com.example.clientwithbottmmenu.R;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment implements View.OnClickListener {
//TODO:
//    http://developer.alexanderklimov.ru/android/views/cardview.php
//    создание cardView или recyclerView
//    https://www.fandroid.info/urok-3-sozdanie-spiskov-kartochek-v-android-prilozheniyah-material-design/
//    https://metanit.com/java/android/5.11.php
//    https://www.fandroid.info/android-uchebnik-sozdanie-recyclerview-cardview-i-menyu-optsij-dlya-elementa-recyclerview/ Переделать на это

    private DiaryProduct diaryProduct;
    private DiaryAddItemFragment diaryAddItemFragment;

    private RecyclerView mRecyclerView;
    private DataAdapterRecyclerView mAdapter;
    private DiaryViewModel mDiaryViewModel;
    private List<DiaryProduct> products;

    private String mName;
    private String mDescription;
    private int mProtein;
    private int mFat;
    private int mCarbohydrates;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_diary, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_diary);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        products = new ArrayList<>();

        setHasOptionsMenu(true);

        return root;
    }
    public FragmentTransaction ft;
    //    TODO:
//     http://developer.alexanderklimov.ru/android/theory/fragment-replace.php
//     обработчик нажитий на меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistic: {
                ((MainActivity) getActivity()).onNavigationItemSelected(1);
                break;
            }
            case R.id.add: {
                diaryAddItemFragment = new DiaryAddItemFragment();

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, diaryAddItemFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

//                Bundle bundle = this.getArguments();
//                mName = bundle.getString("name", " ");
//                mDescription = bundle.getString("description", " ");
//                mProtein = bundle.getInt("protein", 0);
//                mFat = bundle.getInt("fat", 0);
//                mCarbohydrates = bundle.getInt("carbohydrates", 0);

                products.add(new DiaryProduct(mName, mDescription, mProtein, mFat, mCarbohydrates));
                mAdapter = new DataAdapterRecyclerView(getContext(), products);
                mRecyclerView.setAdapter(mAdapter);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
    // Вызов меню

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.diary, menu);
    }
}
