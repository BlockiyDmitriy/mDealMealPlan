package com.example.clientwithbottmmenu.ui.diary;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clientwithbottmmenu.MainActivity;
import com.example.clientwithbottmmenu.R;

import java.util.ArrayList;
import java.util.List;

public class DiaryAddItemFragment extends Fragment {

    private DiaryAddItemViewModel mViewModel;
    private DiaryFragment diaryFragment;

    private List<DiaryProduct> products;

    private String mName;
    private String mDescription;
    private int mProtein;
    private int mFat;
    private int mCarbohydrates;

    private EditText mAddItemName;
    private EditText mAddItemDescription;
    private EditText mAddItemProtein;
    private EditText mAddItemFat;
    private EditText mAddItemCarbohydrates;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diary_add_item_fragment, container, false);

        mAddItemName = (EditText) v.findViewById(R.id.addItemName);
        mAddItemDescription = (EditText) v.findViewById(R.id.addItemDescription);
        mAddItemProtein = (EditText) v.findViewById(R.id.addItemProtein);
        mAddItemFat = (EditText) v.findViewById(R.id.addItemFat);
        mAddItemCarbohydrates = (EditText) v.findViewById(R.id.addItemCarbohydrates);

        products = new ArrayList<>();

        setHasOptionsMenu(true);
        return v;
    }

    private boolean getParam() {
        String mTempProtein;
        String mTempFat;
        String mTempCarbohydrates;
        mName = mAddItemName.getText().toString();
        mDescription = mAddItemDescription.getText().toString();
        mTempProtein = mAddItemProtein.getText().toString();
        mTempFat = mAddItemFat.getText().toString();
        mTempCarbohydrates = mAddItemCarbohydrates.getText().toString();
        if (!mName.equals("") && !mTempProtein.equals("") && !mTempFat.equals("") && !mTempCarbohydrates.equals("")) {
            mProtein = Integer.parseInt(mTempProtein);
            mFat = Integer.parseInt(mTempFat);
            mCarbohydrates = Integer.parseInt(mTempCarbohydrates);
            return true;
        } else {
            return false;
        }
        //products.add(new DiaryProduct(mName,mDescription,mProtein,mFat,mCarbohydrates));
    }

    private void setBundleArg() {
        diaryFragment = new DiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", mName);
        bundle.putString("description", mDescription);
        bundle.putInt("protein", mProtein);
        bundle.putInt("fat", mFat);
        bundle.putInt("carbohydrates", mCarbohydrates);
        diaryFragment.setArguments(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DiaryAddItemViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemAccept: {
                boolean temp = getParam();
                if (temp == true) {
                    setBundleArg();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Вы не ввели данные", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.diary_add_item, menu);
    }
}