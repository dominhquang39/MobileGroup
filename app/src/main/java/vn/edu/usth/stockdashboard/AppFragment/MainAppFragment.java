package vn.edu.usth.stockdashboard.AppFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import vn.edu.usth.stockdashboard.Adapter.PageAdapter;
import vn.edu.usth.stockdashboard.MainActivity;
import vn.edu.usth.stockdashboard.R;


public class MainAppFragment extends Fragment {
    private static MainAppFragment Instance;

    public static MainAppFragment getInstance() {
        return Instance;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainAppFragment.Instance = this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main_app, container, false);

        PagerAdapter adapter = new PageAdapter(getActivity().getSupportFragmentManager());
        this.pager = (ViewPager) view.findViewById(R.id.pager);
        this.pager.setOffscreenPageLimit(4);
        this.pager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.home);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.chart);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.pay);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(R.drawable.profile);
        return view;
    }

    public void setFragment(int page){
        this.pager.setCurrentItem(page);
    }
}