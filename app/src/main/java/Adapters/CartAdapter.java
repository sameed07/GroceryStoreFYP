package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infusibleCoder.grocerystorefyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Interface.CountPrice;
import Models.CartModel;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.ViewHolder>{

    List<CartModel> mList;
    Context mContext;
    CountPrice countPrice;

    public CartAdapter(List<CartModel> mList, Context mContext, CountPrice countPrice) {
        this.mList = mList;
        this.mContext = mContext;
        this.countPrice = countPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adatper,parent,false);
        return new CartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {



        final CartModel model = mList.get(position);
        holder.txt_title.setText(model.getTitle());
        holder.txt_price.setText(model.getPrice());
       Picasso.get().load(model.getImg_url()).into(holder.product_img);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             holder.count ++;
             holder.txt_count.setText(""+holder.count);

             countPrice.getcount(Double.parseDouble(model.getPrice()),holder.count,true);

            }
        });
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.count --;
                if(holder.count>=0) {
                    holder.txt_count.setText("" + holder.count);
                    countPrice.getcount(Double.parseDouble(model.getPrice()),holder.count,false);
                }else{

                    holder.btn_remove.setClickable(false);
                    Toast.makeText(mContext, "Must be 1", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int count =1;
        ImageView product_img;
        TextView txt_title,txt_price,txt_count;
        Button btn_add,btn_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_img = itemView.findViewById(R.id.product_img);
            txt_title = itemView.findViewById(R.id.txt_product_title);
            txt_price = itemView.findViewById(R.id.txt_product_price);
            txt_count = itemView.findViewById(R.id.text_item);
            btn_add = itemView.findViewById(R.id.btn_incremeant);
            btn_remove = itemView.findViewById(R.id.btn_decreament);

        }
    }
}
