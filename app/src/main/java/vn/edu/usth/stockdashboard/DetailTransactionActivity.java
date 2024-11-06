package vn.edu.usth.stockdashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailTransactionActivity extends AppCompatActivity {
    private TextView text1, text2, text3,text4, text5 ;
    private ImageView backButton;
    private ImageView icon1, icon2, icon3, icon4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_transaction);

        text1 = findViewById(R.id.value1);
        text2 = findViewById(R.id.date);
        text3 = findViewById(R.id.buy);
        text4 = findViewById(R.id.value2);
        text5 = findViewById(R.id.fullname);
        backButton = findViewById(R.id.backbutton);

        // Get data from intent
        String text1value = getIntent().getStringExtra("text1");
        String text2value = getIntent().getStringExtra("text2");
        String text3value = getIntent().getStringExtra("text3");
        String text4value = getIntent().getStringExtra("text4");
        String text5value = getIntent().getStringExtra("text5");

        // Set text to TextViews
        text1.setText(text1value);
        text2.setText(text2value);
        text3.setText(text3value);
        text4.setText(text4value);
        text5.setText(text5value);

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

