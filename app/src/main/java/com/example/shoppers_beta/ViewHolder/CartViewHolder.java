package com.example.shoppers_beta.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppers_beta.Interface.ItemClickListner;
import com.example.shoppers_beta.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textProductName,textProductPrice,textProductQty;
    public ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        textProductName = itemView.findViewById(R.id.cart_product_name);
        textProductPrice = itemView.findViewById(R.id.cart_product_price);
        textProductQty = itemView.findViewById(R.id.cart_product_qty);

    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
