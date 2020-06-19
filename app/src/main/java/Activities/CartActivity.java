package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.infusibleCoder.grocerystorefyp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.CartAdapter;
import Interface.CountPrice;
import LocalDb.DBHelper;
import Models.CartModel;

public class CartActivity extends AppCompatActivity implements CountPrice{

    private Toolbar toolbar;
    private DBHelper db;
    TextView txt_total,txt_total_dialog,txt_items;
    Button btn_checkout;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    String currentUser;

    public static double TOTAL_PRICE = 0;

    RecyclerView cart_recycler;
    RecyclerView.LayoutManager layoutManager;
    private EditText edtAddress,edtPhone;
    private Button btnConfirm;

    LinearLayout empty_layout;


    // private List<CartModel> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = new DBHelper(this);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Orders");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
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
                TOTAL_PRICE = 0;
                finish();
            }
        });
        cart_recycler = findViewById(R.id.cart_recycler);
        layoutManager = new LinearLayoutManager(this);
        cart_recycler.setLayoutManager(layoutManager);
        txt_total = findViewById(R.id.txt_total);
        btn_checkout = findViewById(R.id.btn_checkout);
        empty_layout = findViewById(R.id.layout_empty);



        if(db.getItemsCount() <=0){

            cart_recycler.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);

        }else{

            empty_layout.setVisibility(View.GONE);
            cart_recycler.setVisibility(View.VISIBLE);
        }


        //Toast.makeText(this, "" + db.getItemsCount(), Toast.LENGTH_SHORT).show();




        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<CartModel> mlist = db.getAllContacts();
       // contacts.add(new CartModel("alsga","Product Title","200","gangaskgaskjgkaj"));
//        Toast.makeText(CartActivity.this, "" + mlist.get(1).getTitle(), Toast.LENGTH_SHORT).show();
        CartAdapter adapter = new CartAdapter(mlist,this, this);
        cart_recycler.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        //adapter.notifyItemRemoved(getAda);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(db.getItemsCount() <=0) {
                    Toast.makeText(CartActivity.this, "Cart us Empty!", Toast.LENGTH_SHORT).show();

                }else{
                    showDialog();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TOTAL_PRICE = 0;
    }

    @Override
    public int getcount(double price, int number, boolean isAdd) {

//TOTAL_PRICE =0;
        if(isAdd) {

            TOTAL_PRICE = TOTAL_PRICE + (price * 1);
            txt_total.setText(TOTAL_PRICE + "");
        }else{

            TOTAL_PRICE = TOTAL_PRICE - (price * 1);
            txt_total.setText(TOTAL_PRICE + "");
        }
        return 0;
    }

    public void showDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.address_dialog);

       edtAddress = dialog.findViewById(R.id.edt_address);
       edtPhone = dialog.findViewById(R.id.edt_phone);
       btnConfirm = dialog.findViewById(R.id.btn_confirm);
       txt_items = dialog.findViewById(R.id.txt_total_items);
       txt_total_dialog = dialog.findViewById(R.id.txt_total_price);


       txt_total_dialog.setText("RS. "+TOTAL_PRICE);
       txt_items.setText("x " + db.getItemsCount());

       btnConfirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

             

                   checout();
                   dialog.dismiss();

               
           }
       });

        dialog.show();
    }

    public void checout(){

        
        if(!TextUtils.isEmpty(edtAddress.getText().toString()) && ! TextUtils.isEmpty(
                edtPhone.getText().toString()
        )) {

            Map<String, String> map = new HashMap<>();
            map.put("total_item", txt_items.getText().toString());
            map.put("total_price", txt_total_dialog.getText().toString());
            map.put("address", edtAddress.getText().toString());
            map.put("phone", edtPhone.getText().toString());
            map.put("user_id", currentUser);
            map.put("order_status","pending");
            
            mRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(CartActivity.this, "Order Placed Successful", Toast.LENGTH_SHORT).show();

                    db.deleteDb();
                    startActivity(new Intent(CartActivity.this, OrderActivity.class));
                    finish();
                }
            });

        }else{

            Toast.makeText(this, " Fields Must not be empty", Toast.LENGTH_SHORT).show();
        }

    }
}