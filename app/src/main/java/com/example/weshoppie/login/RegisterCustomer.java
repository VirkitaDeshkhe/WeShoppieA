package com.example.weshoppie.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weshoppie.CustomerDashboard.CustomerDashboardNew;
import com.example.weshoppie.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterCustomer extends AppCompatActivity {

    EditText Address_1, Address_2 , cust_city, pincode, state, Cust_Name, Cust_Phone;
    Button Cust_Register;
    String userid, usermail;
    FirebaseUser CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        Address_1 = findViewById(R.id.Address_1);
        Address_2 = findViewById(R.id.Address_2);
        cust_city = findViewById(R.id.cust_city);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        Cust_Name = findViewById(R.id.Cust_Name);
        Cust_Phone = findViewById(R.id.Cust_Phone);
        Cust_Register = findViewById(R.id.Cust_Register);

        userid = CurrentUser.getUid();
        usermail = CurrentUser.getEmail();
        //Register the Customer *********************************************************************************************8
        Cust_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Cust_name,Cust_phone,Cust_add_1,Cust_add_2,Cust_city,Cust_pincode,Cust_state;
                Cust_name = Cust_Name.getText().toString();
                Cust_phone = Cust_Phone.getText().toString();
                Cust_add_1 = Address_1.getText().toString();
                Cust_add_2 = Address_2.getText().toString();
                Cust_city = cust_city.getText().toString();
                Cust_pincode = pincode.getText().toString();
                Cust_state = state.getText().toString();
                //Checking if the fields are empty**********************************************************************************
                if(TextUtils.isEmpty(Cust_name)){
                    Toast.makeText(RegisterCustomer.this, "Enter the Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Cust_phone)){
                    Toast.makeText(RegisterCustomer.this, "Enter the Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Cust_add_1)){
                    Toast.makeText(RegisterCustomer.this, "Enter the Address line 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Cust_add_2)){
                    Toast.makeText(RegisterCustomer.this, "Enter the Address line 2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Cust_city)){
                    Toast.makeText(RegisterCustomer.this, "Enter the City", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Cust_pincode)){
                    Toast.makeText(RegisterCustomer.this, "Enter the Pincode", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Cust_state)){
                    Toast.makeText(RegisterCustomer.this, "Enter the State", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Putting data into the hashmap ***********************************************************
                Map<String,Object> userdata = new HashMap<>();
                userdata.put("Name",Cust_name);
                userdata.put("Mobile_Number",Cust_phone);
                userdata.put("Address_1",Cust_add_1);
                userdata.put("Address_2",Cust_add_2);
                userdata.put("City",Cust_city);
                userdata.put("Pincode",Cust_pincode);
                userdata.put("State",Cust_state);
                userdata.put("Email",usermail);
                //Adding data to database **************************************************************************************
                db.collection("Customer").document(userid).set(userdata)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterCustomer.this, "Data Saved", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterCustomer.this, CustomerDashboardNew.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterCustomer.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}