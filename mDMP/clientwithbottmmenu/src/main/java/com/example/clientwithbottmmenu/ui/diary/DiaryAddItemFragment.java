package com.example.clientwithbottmmenu.ui.diary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.clientwithbottmmenu.R;
import com.example.clientwithbottmmenu.dbSave.dbProduct.DBHelperProduct;
import com.example.clientwithbottmmenu.ui.diary.model.DiaryProduct;

import java.util.ArrayList;
import java.util.List;

public class DiaryAddItemFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private List<DiaryProduct> mProducts;

    private String mName;
    private String mDescription;
    private float mProtein;
    private float mFat;
    private float mCarbohydrates;

    private EditText mAddItemName;
    private EditText mAddItemDescription;
    private EditText mAddItemProtein;
    private EditText mAddItemFat;
    private EditText mAddItemCarbohydrates;

    private String mTempProtein;
    private String mTempFat;
    private String mTempCarbohydrates;

    private DBHelperProduct mDbHelperProduct;

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(List<DiaryProduct> link);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diary_add_item_fragment, container, false);

        mAddItemName = (EditText) v.findViewById(R.id.addItemName);
        mAddItemDescription = (EditText) v.findViewById(R.id.addItemDescription);
        mAddItemProtein = (EditText) v.findViewById(R.id.addItemProtein);
        mAddItemFat = (EditText) v.findViewById(R.id.addItemFat);
        mAddItemCarbohydrates = (EditText) v.findViewById(R.id.addItemCarbohydrates);

        mProducts = new ArrayList<>();

        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DiaryAddItemViewModel mViewModel = ViewModelProviders.of(this).get(DiaryAddItemViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemAccept: {
                boolean temp = getParam();
                if (temp) {
                    updateDetail();
                    setDataToDb();

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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mDbHelperProduct.close();
    }

    private void updateDetail() {
        // Посылаем данные Activity
        mListener.onFragmentInteraction(mProducts);
    }

    private boolean getParam() {
        mName = mAddItemName.getText().toString();
        mDescription = mAddItemDescription.getText().toString();
        mTempProtein = mAddItemProtein.getText().toString();
        mTempFat = mAddItemFat.getText().toString();
        mTempCarbohydrates = mAddItemCarbohydrates.getText().toString();
        if (!mName.equals("") && !mTempProtein.equals("") && !mTempFat.equals("") && !mTempCarbohydrates.equals("")) {
            mProtein = Float.parseFloat(mTempProtein);
            mFat = Float.parseFloat(mTempFat);
            mCarbohydrates = Float.parseFloat(mTempCarbohydrates);
            mProducts.add(new DiaryProduct(mName, mDescription, mProtein, mFat, mCarbohydrates));
            return true;
        } else {
            return false;
        }
    }
    private void setDataToDb(){
        mDbHelperProduct = new DBHelperProduct(getContext());
        mDbHelperProduct.insertProduct(mName,mDescription,mTempProtein,mTempFat,mTempCarbohydrates);
    }
}