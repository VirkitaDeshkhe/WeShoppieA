package com.example.weshoppie.ShopkeeperDashboard.ShopkeeperNewOrders;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.weshoppie.R;
import com.example.weshoppie.ShopkeeperDashboard.ShopkeeperNewOrders.ProductStatus.SeeUnpackedProducts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShopkeeperNewOrders extends AppCompatActivity implements SelectNewOrder{

    RecyclerView recyclerNewOrder;
    String userID;
    ArrayList<NewOrderModel> arrNewOrderModel;
    RecyclerNewProductAdapter recyclerNewProductAdapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser CurrentUser = mAuth.getCurrentUser();
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_new_orders);

        recyclerNewOrder = findViewById(R.id.recyclerNewOrder);
        recyclerNewOrder.setHasFixedSize(true);
        recyclerNewOrder.setLayoutManager(new LinearLayoutManager(this));
        userID = CurrentUser.getUid();

        db = FirebaseFirestore.getInstance();
        arrNewOrderModel = new ArrayList<NewOrderModel>();
        recyclerNewProductAdapter = new RecyclerNewProductAdapter(ShopkeeperNewOrders.this, arrNewOrderModel,this);
        recyclerNewOrder.setAdapter(recyclerNewProductAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("Orders").whereEqualTo("Shopkeeper_ID",userID).whereEqualTo("Status","Unpacked")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(ShopkeeperNewOrders.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                NewOrderModel nom = dc.getDocument().toObject(NewOrderModel.class);
                                nom.setDocumentID(dc.getDocument().getId());
                                arrNewOrderModel.add(nom);
                            }
                            if (dc.getType() == DocumentChange.Type.MODIFIED){
                                @SuppressLint("UnsafeIntentLaunch") Intent intent = getIntent();
                                overridePendingTransition(0, 0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(intent);
                            }
                            recyclerNewProductAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClicked(NewOrderModel newOrderModel) {
        String orderID = newOrderModel.getDocumentID();
        Intent intent = new Intent(ShopkeeperNewOrders.this, SeeUnpackedProducts.class);
        intent.putExtra("OrderID",orderID);
        startActivity(intent);
    }
}