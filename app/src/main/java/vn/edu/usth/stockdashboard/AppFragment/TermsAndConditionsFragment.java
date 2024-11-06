package vn.edu.usth.stockdashboard.AppFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;

import vn.edu.usth.stockdashboard.MainActivity;
import vn.edu.usth.stockdashboard.R;


public class TermsAndConditionsFragment extends Fragment {
    private ImageView icon1, icon2, icon3, icon4, back_button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);


        icon1 = view.findViewById(R.id.homeicon);
        icon2 = view.findViewById(R.id.chart);
        icon3 = view.findViewById(R.id.pay);
        icon4 = view.findViewById(R.id.profile);
        back_button = view.findViewById(R.id.backbutton);


        icon1.setOnClickListener(v -> {
            switchFragment(0);
        });

        icon2.setOnClickListener(v -> {
            switchFragment(1);
        });

        icon3.setOnClickListener(v -> {
            switchFragment(2);
        });

        icon4.setOnClickListener(v -> {
            switchFragment(3);
        });

        back_button.setOnClickListener(v -> {
            switchFragment(3);
        });

        ImageButton notiButton = view.findViewById(R.id.notiButton);
        notiButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.terms_and_conditions, new NotificationFragment(3));
            transaction.addToBackStack(null);
            transaction.commit();
        });


        return view;
    }

    public void switchFragment(int page){
        MainActivity.getInstance().getMainAppFragment().setFragment(page);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}