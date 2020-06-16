package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.infusibleCoder.grocerystorefyp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.CartAdapter;
import Interface.CountPrice;
import LocalDb.DBHelper;
import Models.CartModel;

public class CartActivity extends AppCompatActivity implements CountPrice {

    private Toolbar toolbar;
    private DBHelper db;
    TextView txt_total;
    Button btn_checkout;


    static double TOTAL_PRICE = 0;

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
        txt_total = findViewById(R.id.txt_total);
        btn_checkout = findViewById(R.id.btn_checkout);


        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<CartModel> mlist = db.getAllContacts();
       // contacts.add(new CartModel("alsga","Product Title","200","gangaskgaskjgkaj"));
//        Toast.makeText(CartActivity.this, "" + mlist.get(1).getTitle(), Toast.LENGTH_SHORT).show();
        CartAdapter adapter = new CartAdapter(mlist,this, this);
        cart_recycler.setAdapter(adapter);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

    }


    @Override
    public int getcount(double price, int number, boolean isAdd) {

        if(isAdd) {
            TOTAL_PRICE = TOTAL_PRICE + (price * number);
            txt_total.setText(TOTAL_PRICE + "");
        }else{

            TOTAL_PRICE = TOTAL_PRICE - (price * number);
            txt_total.setText(TOTAL_PRICE + "");
        }
        return 0;
    }

    public void showDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.address_dialog);

        EditText edtAddress = dialog.findViewById(R.id.edt_address);
        EditText edtPhone = dialog.findViewById(R.id.edt_phone);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);

        dialog.show();
    }
}