package vn.edu.usth.stockdashboard;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;

    public CardView getCardView() {
        return cardView;
    }

    private ImageView companyLogo;

    public ImageView getCompanyLogo() {
        return companyLogo;
    }

    private ImageView imageView2;

    public ImageView getImageView2() {
        return imageView2;
    }

    private ImageView imageView3;

    public ImageView getImageView3() {
        return imageView3;
    }

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.cardview);
        companyLogo = itemView.findViewById(R.id.logo_view);
        imageView2 = itemView.findViewById(R.id.item_chart);
        imageView3 = itemView.findViewById(R.id.item_percent);
    }
}