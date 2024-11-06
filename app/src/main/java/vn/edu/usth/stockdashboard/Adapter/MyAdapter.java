package vn.edu.usth.stockdashboard.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.stockdashboard.CompanyStockItem;
import vn.edu.usth.stockdashboard.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<CompanyStockItem> itemList;
//    private OnItemClickListener listener;
    private ImageLoader imageLoader;

//    public interface OnItemClickListener {
//        void onItemClick(CompanyStockItem clikedItem);
//    }

    public MyAdapter(List<CompanyStockItem> itemList, ImageLoader imageLoader) {
        this.itemList = (itemList != null) ? itemList : new ArrayList<>();
//        this.listener = listener;
        this.imageLoader = imageLoader;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_stock_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CompanyStockItem currentItem = itemList.get(position);

        holder.stockName.setText(currentItem.getStockName());
        holder.companyName.setText(currentItem.getCompanyName());
        holder.stockPrice.setText(currentItem.getPrice());
        holder.changePercentage.setText(currentItem.getChangePercent());
        holder.logo.setImageUrl(currentItem.getLogoUrl(), imageLoader);

//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onItemClick(currentItem); // Passing the entire item object
//            }
//        });

    }

    public void updateList(List<CompanyStockItem> newList) {
        this.itemList = newList;
        notifyDataSetChanged();  // Refresh RecyclerView
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private NetworkImageView logo;
        TextView stockName, companyName, stockPrice, changePercentage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo_view);
            stockName = itemView.findViewById(R.id.stock_name);
            companyName = itemView.findViewById(R.id.company_name);
            stockPrice = itemView.findViewById(R.id.price);
            changePercentage = itemView.findViewById(R.id.item_percent);
        }
    }
}
