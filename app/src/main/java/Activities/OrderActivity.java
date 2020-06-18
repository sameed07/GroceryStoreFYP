package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusibleCoder.grocerystorefyp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.OrderAdapter;
import Models.CategoryModel;
import Models.OrderModel;

public class OrderActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FirebaseDatabase mdatabase;
    private DatabaseReference mRef;

    //recycler
    RecyclerView orderRecycler;
    RecyclerView.LayoutManager layoutManager;
    List<OrderModel> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mdatabase = FirebaseDatabase.getInstance();
        mRef   = mdatabase.getReference("Orders");
        orderRecycler = findViewById(R.id.order_recycler);
        layoutManager = new LinearLayoutManager(this);
        orderRecycler.setLayoutManager(layoutManager);
        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
            }
        });

    }

    public void loadData(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    OrderModel model = ds.getValue(OrderModel.class);
                    model.setOrder_id(ds.getKey());
                    mList.add(model);

                    OrderAdapter adapter = new OrderAdapter(mList,OrderActivity.this);
                    orderRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}