package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusibleCoder.grocerystorefyp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.OrderDetailAdapter;
import Models.CartModel;

public class CompleteOrderDetails extends AppCompatActivity {


    private Toolbar toolbar;

    private FirebaseDatabase mdatabase;
    private DatabaseReference mRef;

    //recycler
    RecyclerView orderRecycler;
    RecyclerView.LayoutManager layoutManager;
    List<CartModel> mList = new ArrayList<>();

    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order_details);
        orderId = getIntent().getStringExtra("order_id");

        Toast.makeText(this, "" + orderId, Toast.LENGTH_SHORT).show();
        mdatabase = FirebaseDatabase.getInstance();
        mRef   = mdatabase.getReference("Complete_Orders").child(orderId).child("items");
        orderRecycler = findViewById(R.id.order_detail_recycler);
        layoutManager = new LinearLayoutManager(this);
        orderRecycler.setLayoutManager(layoutManager);
        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //  loadData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
            }
        });
        //  Toast.makeText(OrderDetailActivity.this, "Called" , Toast.LENGTH_SHORT).show();
        getData();
    }

    public void getData(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    CartModel model = ds.getValue(CartModel.class);
                    mList.add(model);

                    Toast.makeText(CompleteOrderDetails.this, "" + model.getTitle(), Toast.LENGTH_SHORT).show();

                    OrderDetailAdapter adapter = new OrderDetailAdapter(mList,CompleteOrderDetails.this);
                    orderRecycler.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}