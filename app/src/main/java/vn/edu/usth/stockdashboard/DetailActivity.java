package vn.edu.usth.stockdashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImage1, detailImage2, backButton;
    private TextView hourly, weekly, daily ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage1 = findViewById(R.id.detail_image1);
        detailImage2 = findViewById(R.id.detail_image2);
        hourly = findViewById(R.id.hourly);
        weekly = findViewById(R.id.weekly);
        daily = findViewById(R.id.daily);
        backButton = findViewById(R.id.backbutton);

        // Get data from intent
        int image1 = getIntent().getIntExtra("image1", 0);
        int image2 = getIntent().getIntExtra("image2", 0);
        int hourlygraph = getIntent().getIntExtra("hourlygraph", 0);
        int weeklygraph = getIntent().getIntExtra("weeklygraph", 0);
        int dailygraph = getIntent().getIntExtra("dailygraph", 0);



        // Set images to ImageViews
        detailImage1.setImageResource(image1);
        detailImage2.setImageResource(image2);

        weekly.setOnClickListener(v -> {
            detailImage2.setImageResource(weeklygraph);
            weekly.setBackgroundResource(R.drawable.textbar1);
            hourly.setBackgroundResource(R.drawable.textbar_unselected);
            daily.setBackgroundResource(R.drawable.textbar_unselected);
        });

        hourly.setOnClickListener(v -> {
            detailImage2.setImageResource(hourlygraph);
            hourly.setBackgroundResource(R.drawable.textbar1);
            weekly.setBackgroundResource(R.drawable.textbar_unselected);
            daily.setBackgroundResource(R.drawable.textbar_unselected);
        });

        daily.setOnClickListener(v -> {
            detailImage2.setImageResource(dailygraph);
            daily.setBackgroundResource(R.drawable.textbar1);
            hourly.setBackgroundResource(R.drawable.textbar_unselected);
            weekly.setBackgroundResource(R.drawable.textbar_unselected);
        });

        backButton.setOnClickListener(v -> {
            finish();
        });

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
