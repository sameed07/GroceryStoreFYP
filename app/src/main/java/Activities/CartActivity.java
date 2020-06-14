package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.infusibleCoder.grocerystorefyp.R;

import java.util.List;

import LocalDb.DBHelper;
import Models.CartModel;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DBHelper db;
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

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<CartModel> contacts = db.getAllContacts();
       // contacts.add(new CartModel("alsga","Product Title","200","gangaskgaskjgkaj"));

        for (CartModel cn : contacts) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getTitle() + " ,Price: " +
                    cn.getPrice();
            // Writing Contacts to log
            Log.d("Name: ", log);
            Toast.makeText(this, "" + log, Toast.LENGTH_SHORT).show();
        }


    }
}