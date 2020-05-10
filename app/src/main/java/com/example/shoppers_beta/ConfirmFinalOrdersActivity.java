package com.example.shoppers_beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppers_beta.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrdersActivity extends AppCompatActivity {

    private EditText shipment_city,shipment_address,shipment_phone,shipment_name;
    private Button shipment_confirm_details;
    private int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_orders);

        totalPrice = getIntent().getIntExtra("Total Price",0);

        shipment_city = (EditText)findViewById(R.id.shipment_city);
        shipment_address = (EditText)findViewById(R.id.shipment_address);
        shipment_phone = (EditText) findViewById(R.id.shipment_phone);
        shipment_name = (EditText)findViewById(R.id.shipment_name);
        shipment_confirm_details = (Button) findViewById(R.id.shipment_confirm_details);

        shipment_confirm_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDetails();
            }
        });
    }

    private void validateDetails() {

        if(TextUtils.isEmpty(shipment_name.getText().toString())){
            Toast.makeText(this,"Please Enter the Name of Recipent",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(shipment_phone.getText().toString())){
            Toast.makeText(this,"Please Enter a Contact Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(shipment_address.getText().toString())){
            Toast.makeText(this,"Please Enter the Delivery address",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(shipment_city.getText().toString())){
            Toast.makeText(this,"Please Enter City Name",Toast.LENGTH_SHORT).show();
        }
        else{
            confirmOrders();
        }
    }

    private void confirmOrders() {

        //Get Date and Time of Order being placed
        final String saveCurrentDate,saveCurrentTime;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> orderMap = new HashMap<String, Object>();
        orderMap.put("name",shipment_name.getText().toString());
        orderMap.put("phone",shipment_phone.getText().toString());
        orderMap.put("address",shipment_address.getText().toString());
        orderMap.put("city",shipment_city.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("status","Order Placed");
        orderMap.put("total",Integer.toString(totalPrice));

        firebaseDatabaseReference.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ConfirmFinalOrdersActivity.this,"Order has been placed Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ConfirmFinalOrdersActivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }
}
