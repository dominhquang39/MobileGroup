package vn.edu.usth.stockdashboard.AppFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.stockdashboard.Adapter.MyAdapter3;
import vn.edu.usth.stockdashboard.DetailTransactionActivity;
import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.PurchaseItem;

public class WalletFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter3 myAdapter;
//    private ImageView icon1, icon2, icon3, icon4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);


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
        List<PurchaseItem> itemList = new ArrayList<>();

        itemList.add(new PurchaseItem("Buy AMZN", "20 November 2024", "$207.00","Amazon.com Inc."));
        itemList.add(new PurchaseItem("Buy TSLA", "07 November 2024", "$534.80","Tesla. Inc."));
        itemList.add(new PurchaseItem("Buy SPOT", "27 October 2024", "$118.40","Spotify Inc."));
        itemList.add(new PurchaseItem("Buy NFLX", "16 October 2024", "$428.40", "Netflix Inc."));
        itemList.add(new PurchaseItem("Buy MFST", "5 September 2024", "$378.60", "Microsoft Corp"));


        myAdapter = new MyAdapter3(itemList, clickedItem -> {
            Intent intent = new Intent(getActivity(), DetailTransactionActivity.class);
            intent.putExtra("text1", clickedItem.getAmount());
            intent.putExtra("text2", clickedItem.getDate());
            intent.putExtra("text3", clickedItem.getBuy());
            intent.putExtra("text4", clickedItem.getAmount());
            intent.putExtra("text5", clickedItem.getFullname());
            startActivity(intent);
        });
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        ImageButton notiButton = view.findViewById(R.id.notiButton);
        notiButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new NotificationFragment(2));
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void navigateToFragment(int page) {
        MainAppFragment.getInstance().setFragment(page);
    }


}