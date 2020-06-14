package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.infusibleCoder.grocerystorefyp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.CartAdapter;
import LocalDb.DBHelper;
import Models.CartModel;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DBHelper db;
    RecyclerView cart_recycler;
    RecyclerView.LayoutManager layoutManager;

   // private List<CartModel> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = new DBHelper(this);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                finish();
            }
        });

        cart_recycler = findViewById(R.id.cart_recycler);
        layoutManager = new LinearLayoutManager(this);
        cart_recycler.setLayoutManager(layoutManager);

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<CartModel> mlist = db.getAllContacts();
       // contacts.add(new CartModel("alsga","Product Title","200","gangaskgaskjgkaj"));

        CartAdapter adapter = new CartAdapter(this,mlist);
        cart_recycler.setAdapter(adapter);

//        for (CartModel cn : mlist) {
//            String log = "Id: " + cn.getId() + " ,Name: " + cn.getTitle() + " ,Price: " +
//                    cn.getPrice();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//            Toast.makeText(this, "" + log, Toast.LENGTH_SHORT).show();
//        }


    }
}