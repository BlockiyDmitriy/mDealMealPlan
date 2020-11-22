package com.example.client.ui.diary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.client.R;

import java.util.ArrayList;
import java.util.List;

public class DiaryAddItem extends AppCompatActivity {
    private DiaryProduct mDiaryProduct;
    private TempSaveData mTempSaveData;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAddItemName = (EditText) findViewById(R.id.addItemName);
        mAddItemDescription = (EditText) findViewById(R.id.addItemDescription);
        mAddItemProtein = (EditText) findViewById(R.id.addItemProtein);
        mAddItemFat = (EditText) findViewById(R.id.addItemFat);
        mAddItemCarbohydrates = (EditText) findViewById(R.id.addItemCarbohydrates);

        products = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_diary_add_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                return true;
            }
            case R.id.addItemAccept: {
                add();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public void add() {
        mName = mAddItemName.getText().toString();
        mDescription = mAddItemDescription.getText().toString();
        mProtein = Integer.parseInt(mAddItemProtein.getText().toString());
        mFat = Integer.parseInt(mAddItemFat.getText().toString());
        mCarbohydrates = Integer.parseInt(mAddItemCarbohydrates.getText().toString());

//        products.add(new DiaryProduct(mName,mDescription,mProtein,mFat,mCarbohydrates));
        mTempSaveData = new TempSaveData(mName, mDescription, mProtein, mFat, mCarbohydrates);

    }
}