package vn.edu.usth.stockdashboard.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.edu.usth.stockdashboard.AppFragment.ChartFragment;
import vn.edu.usth.stockdashboard.AppFragment.HomeFragment;
import vn.edu.usth.stockdashboard.AppFragment.ProfileFragment;
import vn.edu.usth.stockdashboard.AppFragment.WalletFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT =4;
//    private String titles[]= new String[] {"Hanoi", "Paris","Toulouse"};
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getCount(){
        return PAGE_COUNT; //numberofpagesforaViewPager
    }
    @Override
    public Fragment getItem(int page){
        switch (page) {
            case 0: return new HomeFragment();
            case 1: return new ChartFragment();
            case 2: return new WalletFragment();
            case 3: return new ProfileFragment();
        }
        return new Fragment();
    }


//    @Override
//    public CharSequence getPageTitle(int page){
//        //returnsatabtitlecorrespondingtothespecifiedpage
//        return titles[page];
//    }

}
