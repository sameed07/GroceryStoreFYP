package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusibleCoder.grocerystorefyp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.CategoryAdapter;
import Adapters.CompleteOrderAdapter;
import Adapters.OrderAdapter;
import Common.Common;
import Models.CategoryModel;
import Models.OrderModel;

public class MyProfileActivity extends AppCompatActivity {

    private TextView txt_name,txt_phone;
    private Toolbar toolbar;

    RecyclerView completed_recycler;
    RecyclerView.LayoutManager layoutManager;
    List<OrderModel> mList = new ArrayList<>();
    CategoryAdapter adapter;
    //Firebase
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Complete_Orders");
        mAuth = FirebaseAuth.getInstance();

        txt_name = findViewById(R.id.txt_user_name);
        txt_phone = findViewById(R.id.txt_user_phone);
        completed_recycler = findViewById(R.id.complete_recycler);
        layoutManager = new LinearLayoutManager(this);
        completed_recycler.setLayoutManager(layoutManager);


//        txt_phone.setText(Common.currentUser.getPhone_number());
//        txt_name.setText(Common.currentUser.getUser_name());

//        Toast.makeText(this, ""  + Common.currentUser.getUser_name(), Toast.LENGTH_SHORT).show();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
            }
        });


        loadData();

    }

    // TODO: 25/06/2020  I have to create a complete order details activity 

    public void loadData(){

        mRef.orderByChild("user_id").equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    OrderModel model = ds.getValue(OrderModel.class);
                    model.setOrder_id(ds.getKey());
                    mList.add(model);

                    CompleteOrderAdapter adapter = new CompleteOrderAdapter(mList,MyProfileActivity.this);
                    completed_recycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}