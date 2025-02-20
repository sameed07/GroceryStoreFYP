package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infusibleCoder.grocerystorefyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import LocalDb.DBHelper;
import Models.CartModel;

public class ProductDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView product_img;
    private TextView txt_title,txt_desc,txt_price,txt_time;
    private Button btn_cart;
    private String title,desc,productId,img_url,price,time;
    private DBHelper db;
    private List<CartModel> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

         db = new DBHelper(this);


        title = getIntent().getStringExtra("product_title");
        desc = getIntent().getStringExtra("product_desc");
        price = getIntent().getStringExtra("product_price");
        time = getIntent().getStringExtra("product_time");
        img_url = getIntent().getStringExtra("product_img");
        productId = getIntent().getStringExtra("product_id");


        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Product Detail");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                finish();


            }
        });

        txt_desc = findViewById(R.id.product_desc);
        txt_title = findViewById(R.id.product_name);
        txt_price = findViewById(R.id.product_price);
        txt_time = findViewById(R.id.product_time);
        product_img = findViewById(R.id.product_img);
        btn_cart  = findViewById(R.id.btn_add_cart);

        txt_title.setText(title);
        txt_desc.setText(desc);
        txt_price.setText("Rs "+price);
        txt_time.setText(time);
        Picasso.get().load(img_url).into(product_img);




        mlist = db.getAllContacts();
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inserting Contacts


                    //todo need to find the logic huh
                    if (mlist.isEmpty()) {

                    if (db.addProduct(new CartModel(productId, title, price, img_url))) {
                        Log.d("if", "in iff condition: ");
                        Toast.makeText(ProductDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        if(!mlist.isEmpty())
                            mlist.clear();

                           mlist = db.getAllContacts();
                    } else {

                        Toast.makeText(ProductDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    for (CartModel model : mlist){

                        Log.d("sahgasghaskjk", "in loop: "+ model.getTitle() + " = " + productId);
                        if (!model.getProduct_id().equals(productId)) {
                            if (db.addProduct(new CartModel(productId, title, price, img_url))) {
                                Log.d("if", "in if condition: ");
                                Toast.makeText(ProductDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                return;
//                                if(!mlist.isEmpty())
//                                    mlist.clear();
//
//                                mlist = db.getAllContacts();
                            } else {
                                Toast.makeText(ProductDetailsActivity.this, "Already in cart", Toast.LENGTH_SHORT).show();
                            }
//                db.addProduct(new CartModel(2,"10341","Mens Wear","234","1rqrastasgasga"));
//                db.addProduct(new CartModel(3,"10341","Mens Wear","234","1rqrastasgasga"));
//                db.addProduct(new CartModel(4,"10341","Mens Wear","234","1rqrastasgasga"));
//                Toast.makeText(ProductDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                            //   db.addData();
                        } else {

                            Toast.makeText(ProductDetailsActivity.this, "Product Already is added to cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}