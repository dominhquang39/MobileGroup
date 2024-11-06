package vn.edu.usth.stockdashboard.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.PurchaseItem;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder> {

    private List<PurchaseItem> itemList;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PurchaseItem item = this.itemList.get(position);
        holder.textView.setText(item.getBuy());
        holder.textView2.setText(item.getDate());
        holder.textView3.setText(item.getAmount());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item); // Passing the entire item object
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public MyAdapter3(List<PurchaseItem> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView;
        private TextView textView2;
        private TextView textView3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            textView=itemView.findViewById(R.id.text1);
            textView2=itemView.findViewById(R.id.text2);
            textView3=itemView.findViewById(R.id.text3);
        }
    }



    public interface OnItemClickListener {
        void onItemClick(PurchaseItem clikedItem);
}
}
