package vn.edu.usth.testapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private EditText compName, startDate, endDate;
    private TextView stockPrice, companyName, percentage;
    private Button getStock;
    private static final String API_KEY = "Z07VZCFXG1ZNK4CL"; // Replace with your actual API key

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
        getStock = findViewById(R.id.stock_btn);

        getStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockName = compName.getText().toString().trim();
                String start = startDate.getText().toString().trim();
                String end = endDate.getText().toString().trim();

                if (!stockName.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
                    fetchCompStockName(stockName, start, end);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter stock name and date range", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchCompStockName(String stockName, String start, String end) {
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + stockName + "&apikey=" + API_KEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
                companyName.setText("Error fetching company name");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }



    private void fetchStockPrice(String stockName, String start, String end) {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockName + "&apikey=" + API_KEY;

        // Create a new request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // JSON request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response
                            JSONObject timeSeries = response.getJSONObject("Time Series (Daily)");
                            displayStockInRange(timeSeries, start, end);
                            Log.d("APIResponse", response.toString());
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
                        stockPrice.setText("Error retrieving data.");
                    }
                });

        // Add the request to the queue
        requestQueue.add(jsonObjectRequest);
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject globalQuote = response.getJSONObject("Global Quote");
                            String change_percent = globalQuote.getString("10. change percent");

                            // Display the latest price
                            percentage.setText("change percent: " + change_percent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            percentage.setText("Failed to get the latest percantage change.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        percentage.setText("Error retrieving price.");
                    }
                });

        // Add the request to the queue
        requestQueue.add(jsonObjectRequest);
    }
}
