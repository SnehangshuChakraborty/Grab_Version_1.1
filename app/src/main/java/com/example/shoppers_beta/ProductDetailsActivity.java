package com.example.shoppers_beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shoppers_beta.Prevalent.Prevalent;
import com.example.shoppers_beta.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView product_ImageView;
    private TextView product_Name_Details,product_Description_Details,product_Price_Details, product_Price_original;
    private ElegantNumberButton number_Button;
    private Button product_to_cart;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productId = getIntent().getStringExtra("Pid");
        product_ImageView = (ImageView) findViewById(R.id.Product_Imageview);
        product_Name_Details = (TextView) findViewById(R.id.product_name_details);
        product_Description_Details = (TextView) findViewById(R.id.Product_description_details);
        product_Price_Details = (TextView) findViewById(R.id.product_price_details);
        product_Price_original = (TextView) findViewById(R.id.product_price_original);
        number_Button = (ElegantNumberButton) findViewById(R.id.number_btn);
        product_to_cart = (Button) findViewById(R.id.Product_to_cart);

        getProductDetails(productId);
        product_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

    }

    private void addingToCartList() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calDate.getTime());

        final DatabaseReference cartListReference = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("pname",product_Name_Details.getText().toString());
        cartMap.put("price",product_Price_Details.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("qty",number_Button.getNumber());
        cartMap.put("discount","");

        cartListReference.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListReference.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(productId)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(ProductDetailsActivity.this,"Product Added to Cart",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    private void getProductDetails(String productId){

        DatabaseReference productReference = FirebaseDatabase.getInstance().getReference().child("Products");
        productReference.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Product product = dataSnapshot.getValue(Product.class);
                    product_Name_Details.setText(product.getPname());
                    product_Description_Details.setText(product.getDescription());
                    product_Price_original.setText(product.getOprice());
                    product_Price_Details.setText(product.getPrice());
                    Picasso.get().load(product.getImage()).into(product_ImageView);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        

    }
}
