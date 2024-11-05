package vn.edu.usth.testchart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private EditText compName, startDate, endDate;
    private TextView stockPrice, companyName, percentage;
    private NetworkImageView companyLogo;
    private Button getStock;
    private static final String API_KEY = "WTJHO89LU7CUYSIC"; // Replace with your actual API key
    private static final String FINNHUB_API_KEY = "csko419r01qhc8s4icvgcsko419r01qhc8s4id00"; // Replace with your actual API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compName = findViewById(R.id.stock_name);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        stockPrice = findViewById(R.id.stock_price);
        companyName = findViewById(R.id.company_name);
        percentage = findViewById(R.id.change_percentage);
        companyLogo = findViewById(R.id.company_logo);
        getStock = findViewById(R.id.stock_btn);

        getStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockName = compName.getText().toString().trim();
                String start = startDate.getText().toString().trim();
                String end = endDate.getText().toString().trim();

                if (!stockName.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
                    fetchCompStockName(stockName, start, end);
                    fetchCompanyLogo(stockName); // Fetch logo from Finnhub
                } else {
                    Toast.makeText(MainActivity.this, "Please enter stock name and date range", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchCompanyLogo(String stockName) {
        String url = "https://finnhub.io/api/v1/stock/profile2?symbol=" + stockName + "&token=" + FINNHUB_API_KEY;
        Log.d("APIRequest", "Fetching logo from URL: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("APIResponse", "Response: " + response.toString());
                        try {
                            if (response.has("logo") && !response.getString("logo").isEmpty()) {
                                String logoUrl = response.getString("logo");
                                Log.d("APIResponse", "Logo URL: " + logoUrl);

                                ImageLoader imageLoader = new ImageLoader(
                                        VolleySingleton.getInstance(MainActivity.this).getRequestQueue(),
                                        new LruBitmapCache()
                                );

                                companyLogo.setImageUrl(logoUrl, imageLoader);
                            } else {
                                Toast.makeText(MainActivity.this, "Logo not available for this company", Toast.LENGTH_SHORT).show();
                                Log.d("APIResponse", "Logo field is empty or missing");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error fetching logo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("APIError", "Error retrieving logo: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Error retrieving logo", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void fetchCompStockName(String stockName, String start, String end) {
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + stockName + "&apikey=" + API_KEY;
        Log.d("APIRequest", "Fetching company name from URL: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("APIResponse", "Company Name Response: " + response.toString());
                try {
                    String companyNameString = response.getString("Name");
                    companyName.setText("Company: " + companyNameString + "\n");
                    fetchStockPrice(stockName, start, end);
                    fetchLatestPrice(stockName);
                } catch (JSONException e) {
                    e.printStackTrace();
                    stockPrice.setText("Error retrieving company name");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("APIError", "Error retrieving company name: " + error.getMessage());
                companyName.setText("Error fetching company name");
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void fetchStockPrice(String stockName, String start, String end) {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockName + "&apikey=" + API_KEY;
        Log.d("APIRequest", "Fetching stock price from URL: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("APIResponse", "Stock Price Response: " + response.toString());
                        try {
                            JSONObject timeSeries = response.getJSONObject("Time Series (Daily)");
                            displayStockInRange(timeSeries, start, end);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            stockPrice.setText("Failed to parse data.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("APIError", "Error retrieving stock price: " + error.getMessage());
                        stockPrice.setText("Error retrieving data.");
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void displayStockInRange(JSONObject timeSeries, String start, String end) {
        StringBuilder stockData = new StringBuilder();
        boolean withinRange = false;

        try {
            Iterator<String> dates = timeSeries.keys();
            while (dates.hasNext()) {
                String date = dates.next();
                if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
                    withinRange = true;
                    JSONObject dayData = timeSeries.getJSONObject(date);
                    String closePrice = dayData.getString("4. close");
                    stockData.append(date).append(": $").append(closePrice).append("\n");
                }
                if (withinRange && date.compareTo(start) < 0) {
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            stockPrice.setText("Error displaying data.");
        }

        if (stockData.length() > 0) {
            stockPrice.setText(stockData.toString());
        } else {
            stockPrice.setText("No data available for this date range.");
        }
    }

    private void fetchLatestPrice(String symbol) {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + API_KEY;
        Log.d("APIRequest", "Fetching latest price from URL: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("APIResponse", "Latest Price Response: " + response.toString());
                        try {
                            JSONObject globalQuote = response.getJSONObject("Global Quote");
                            String change_percent = globalQuote.getString("10. change percent");
                            percentage.setText("change percent: " + change_percent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            percentage.setText("Failed to get the latest percentage change.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("APIError", "Error retrieving latest price: " + error.getMessage());
                        percentage.setText("Error retrieving price.");
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
