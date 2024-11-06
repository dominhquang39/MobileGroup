package vn.edu.usth.stockdashboard.AppFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.stockdashboard.Adapter.MyAdapter;
import vn.edu.usth.stockdashboard.DetailActivity;
import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.CompanyStockItem;

public class ChartFragment extends Fragment {
    private RecyclerView recyclerView;
//    private ImageView icon1, icon2, icon3, icon4;
    private MyAdapter myAdapter;
    private List<CompanyStockItem> mostViewedItems, topGainersItems, topLosersItems;
    private TextView MostViewed, TopGainers, TopLosers;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        init();

        MostViewed = view.findViewById(R.id.most_viewed);
        TopGainers = view.findViewById(R.id.top_gainers);
        TopLosers = view.findViewById(R.id.top_losers);


        // Set onClick listeners for each TextView
        MostViewed.setOnClickListener(v -> {
            MostViewed.setBackgroundResource(R.drawable.textbar1);
            TopGainers.setBackgroundResource(R.drawable.textbar_unselected);
            TopLosers.setBackgroundResource(R.drawable.textbar_unselected);
            myAdapter.updateList(mostViewedItems);  // Update adapter with Most Viewed items
        });

        TopGainers.setOnClickListener(v -> {
            MostViewed.setBackgroundResource(R.drawable.textbar_unselected);
            TopGainers.setBackgroundResource(R.drawable.textbar1);
            TopLosers.setBackgroundResource(R.drawable.textbar_unselected);
            myAdapter.updateList(topGainersItems);  // Update adapter with Top Gainers items
        });

        TopLosers.setOnClickListener(v -> {
            MostViewed.setBackgroundResource(R.drawable.textbar_unselected);
            TopGainers.setBackgroundResource(R.drawable.textbar_unselected);
            TopLosers.setBackgroundResource(R.drawable.textbar1);
            myAdapter.updateList(topLosersItems);  // Update adapter with Top Losers items
        });


//        icon1 = view.findViewById(R.id.homeicon);
//        icon2 = view.findViewById(R.id.chart);
//        icon3 = view.findViewById(R.id.pay);
//        icon4 = view.findViewById(R.id.profile);
//
//
//        icon1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                navigateToFragment(0);
//            }
//        });
//
//        icon2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                navigateToFragment(1);
//            }
//        });
//
//        icon3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                navigateToFragment(2);
//            }
//        });
//
//        icon4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                navigateToFragment(3);
//            }
//        });

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        ImageButton notiButton = view.findViewById(R.id.notiButton);
        notiButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new NotificationFragment(1));
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void init() {
        // Initialize the lists
        mostViewedItems = new ArrayList<>();
        topGainersItems = new ArrayList<>();
        topLosersItems = new ArrayList<>();

        // Add items to the lists

        // Add items to mostViewedItems


    }
}
