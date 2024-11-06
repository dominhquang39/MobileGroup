package vn.edu.usth.stockdashboard.AppFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.stockdashboard.DetailActivity;
import vn.edu.usth.stockdashboard.MainActivity;
import vn.edu.usth.stockdashboard.Adapter.MyAdapter;
import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.CompanyStockItem;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
//    private ImageView icon1, icon2, icon3, icon4;
    private MyAdapter myAdapter;
    private ImageLoader imageLoader;
    private List<CompanyStockItem> stockList = new ArrayList<>();
    private static final String ALPHA_VANTAGE_API_KEY = "WTJHO89LU7CUYSIC";
    private static final String FINNHUB_API_KEY = "csko419r01qhc8s4icvgcsko419r01qhc8s4id00";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        imageLoader = new ImageLoader(VolleySingleton.getInstance(getContext()).getRequestQueue(), new LruBitmapCache());

        // Initialize the adapter with the item list and a click listener
        myAdapter = new MyAdapter(stockList, imageLoader);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        addStockSymbols();
        fetchALlStockData();



        ImageButton notiButton = view.findViewById(R.id.notiButton);
        notiButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new NotificationFragment(0));
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void addStockSymbols() {
        stockList.add(new CompanyStockItem("AAPL"));
        stockList.add(new CompanyStockItem("GOOGL"));
        stockList.add(new CompanyStockItem("MSFT"));
        stockList.add(new CompanyStockItem("AMZN"));
        stockList.add(new CompanyStockItem("TSLA"));
        stockList.add(new CompanyStockItem("AAP"));
        stockList.add(new CompanyStockItem("A"));
        stockList.add(new CompanyStockItem("AAOI"));
        stockList.add(new CompanyStockItem("CERO"));
        stockList.add(new CompanyStockItem("CERT"));
        stockList.add(new CompanyStockItem("AACG"));
        stockList.add(new CompanyStockItem("AA"));
        stockList.add(new CompanyStockItem("CENTA"));
    }

    private void fetchALlStockData() {
        for (CompanyStockItem stockItem : stockList) {
            fetchStockData(stockItem);
        }
    }

    private void fetchStockData(CompanyStockItem stockItem) {
        fetchCompanyLogo(stockItem);
        fetchCompanyName(stockItem);
        fetchStockPriceAndPercentage(stockItem);
    }

    private void fetchCompanyName(CompanyStockItem stockItem) {
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + stockItem.getStockName() + "&apikey=" + ALPHA_VANTAGE_API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String companyString = response.getString("Name");
                    stockItem.setStockName(companyString);
                    myAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HomeFragment", "Error retrieving company name" + error.getMessage());
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void fetchStockPriceAndPercentage(CompanyStockItem stockItem) {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + stockItem.getStockName() + "&apikey=" + ALPHA_VANTAGE_API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject globalQuote = response.getJSONObject("Global Quote");
                    String price = globalQuote.getString("05. price");
                    String changePercent = globalQuote.getString("10. change percent");

                    stockItem.setPrice(price);
                    stockItem.setChangePercent(changePercent);
                    myAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HomeFragment", "Error fetching stock price and change percent: " + error.getMessage());
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void fetchCompanyLogo(CompanyStockItem stockItem) {
        String url = "https://finnhub.io/api/v1/stock/profile2?symbol=" + stockItem.getStockName() + "&token=" + FINNHUB_API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String logoUrl = response.getString("logo");
                    stockItem.setLogoUrl(logoUrl);
                    myAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HomeFragment", "Error fetching company logo: " + error.getMessage());
            }
        });

        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void navigateToFragment(int page) {
        MainAppFragment.getInstance().setFragment(page);
    }
}
