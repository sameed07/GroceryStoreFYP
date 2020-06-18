package LocalDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Models.CartModel;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Cart.db";
    public static final String TABLE_NAME = "orders";

    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_TITLE = "product_title";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String IMAGE_URL = "img_url";

    private HashMap hp;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CART_TABLE = "CREATE TABLE orders(\n" +
                "   ID   INTEGER PRIMARY KEY     ,\n" +
                "   product_id          TEXT UNIQUE    ,\n" +
                "  product_title TEXT,\n" +
                "  product_price TEXT ,\n" +
                "   image_url TEXT  )";

        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "orders");
    }

    // code to add the new contact
    public boolean addProduct(CartModel model) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("product_id", model.getProduct_id()); // Product Id
            values.put("product_title", model.getTitle()); // Product Title
            values.put("product_price", model.getPrice()); // Product Price
            values.put("image_url", model.getImg_url());  // Image URL

            // Inserting Row
            long result =db.insert("orders", null, values);
            //2nd argument is String containing nullColumnHack


            if (result == -1)
                return false;
            else
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Test", "Exception due to" + e.getMessage());
            return false;
        }
    }

    public void addData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_CART_TABLE = "insert into orders values (null, 'asgsag','Product THis ', '234','113141qsfagag' )";

        db.execSQL(CREATE_CART_TABLE);
    }
//    public boolean addProducts(String product_id, String product_title, String product_price,String product_img) {
//
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put("product_id", product_id); // Product Id
//            values.put("product_title", product_title); // Product Title
//            values.put("product_price", product_price); // Product Price
//            values.put("image_url", product_img);  // Image URL
//
//            long result = db.insert("orders", null, values);
//            if (result == -1)
//                return false;
//            else
//                return true;
//
//        } catch (Exception e) {
//            //e.getMessage();
//            Log.d("Test", "Exception due to" + e.getMessage().toString());
//            return false;
//        }
//    }

    public List<CartModel> getAllContacts() {
        List<CartModel> contactList = new ArrayList<CartModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "orders";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartModel contact = new CartModel();
//                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setProduct_id(cursor.getString(1));
                contact.setTitle(cursor.getString(2));
                contact.setPrice(cursor.getString(3));
                contact.setImg_url(cursor.getString(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public long getItemsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "orders");
        db.close();
        return count;
    }

    public void deleteDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("orders",null,null);
    }
}
