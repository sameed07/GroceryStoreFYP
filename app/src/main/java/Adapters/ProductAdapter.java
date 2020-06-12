package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;;
import com.infusibleCoder.grocerystorefyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Activities.ProductDetailsActivity;
import Models.ProductModel;


public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    List<ProductModel> mList;
    Context mContext;

    public ProductAdapter(Context mContext, List<ProductModel> mList){

        this.mContext = mContext;
        this.mList = mList;

    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter,parent,false);

        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder holder, int position) {

        final ProductModel model = mList.get(position);

        Picasso.get().load(model.getProduct_img()).into(holder.product_img);
        holder.txt_title.setText(model.getProduct_title());
        holder.txt_price.setText("Rs. "+model.getProduct_price());

        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductDetailsActivity.class);
                i.putExtra("product_title",model.getProduct_title());
                i.putExtra("product_desc",model.getProduct_desc());
                i.putExtra("product_price",model.getProduct_price());
                i.putExtra("product_time",model.getTime_stamp());
                i.putExtra("product_img",model.getProduct_img());
                mContext.startActivity(i);

            }
        });




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_img;
        TextView txt_title,txt_price;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_img = itemView.findViewById(R.id.product_img);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_title = itemView.findViewById(R.id.txt_title);


        }
    }
}
