package com.example.client.ui.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client.R;

import java.util.List;

public class DataAdapterRecyclerView extends RecyclerView.Adapter<DataAdapterRecyclerView.ViewHolder> {
    private Context mContext;
    private List<DiaryProduct> products;

    public DataAdapterRecyclerView(Context mContext, List<DiaryProduct> products) {
        this.products = products;
        this.mContext = mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final public TextView nameView, descriptionView, proteinView, fatView, carbohydratesView, txtOptionDigit;

        ViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            descriptionView = (TextView) view.findViewById(R.id.description);
            proteinView = (TextView) view.findViewById(R.id.protein);
            fatView = (TextView) view.findViewById(R.id.fat);
            carbohydratesView = (TextView) view.findViewById(R.id.carbohydrates);
            txtOptionDigit = (TextView) itemView.findViewById(R.id.txtOptionDigit);
        }
    }

    //  Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder,final int position) {
        DiaryProduct product = products.get(position);
        holder.nameView.setText(product.getName());
        holder.descriptionView.setText(product.getDescription());
        holder.proteinView.setText(String.format("Protein: %s", String.valueOf(product.getProtein())));
        holder.fatView.setText(String.format("Fat: %s", String.valueOf(product.getFat())));
        holder.carbohydratesView.setText(String.format("Carbohydrates: %s", String.valueOf(product.getCarbohydrates())));
        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display option menu

                PopupMenu popupMenu = new PopupMenu(mContext, holder.nameView);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.mnu_item_delete:
                                products.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    //  Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public DataAdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_diary_view, parent, false);
        return new ViewHolder(view);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }
}
