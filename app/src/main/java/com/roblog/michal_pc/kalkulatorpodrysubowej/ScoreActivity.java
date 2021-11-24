package com.roblog.michal_pc.kalkulatorpodrysubowej;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.math.BigDecimal;


public class ScoreActivity extends AppCompatActivity {

    private AdView mAdView;

    TextView dataOdTextView, dataDoTextView, dietaTextView, dlugoscTextView;
    public static TextView dietaPL;
    public static TextView jsonTextView;
    public static double f;
    public static String waluta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getSupportActionBar().setTitle("KPS - Kalkulator Podróży Służbowej");

        MobileAds.initialize(this, "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        dataOdTextView = (TextView) findViewById(R.id.textView1);
        dataDoTextView = (TextView) findViewById(R.id.textView2);
        dietaTextView = (TextView) findViewById(R.id.textView3);
        dlugoscTextView = (TextView) findViewById(R.id.textView4);
        jsonTextView = (TextView) findViewById(R.id.textView5);
        dietaPL = (TextView) findViewById(R.id.textView6);

        //udostepnijBtn =(Button)  findViewById(R.id.button);


        String dietaCalosc = getIntent().getStringExtra("dietaCalosc");


        String dataCzasStartString = getIntent().getStringExtra("dataCzasStartString");
        String dataCzasEndString = getIntent().getStringExtra("dataCzasEndString");

        String dni = getIntent().getStringExtra("dni");
        String godz = getIntent().getStringExtra("godziny");
        String min = getIntent().getStringExtra("minuty");
        waluta = getIntent().getStringExtra("waluta");

        f = Double.parseDouble(dietaCalosc);


        dataOdTextView.setText("Pobyt od: " + dataCzasStartString);
        dataDoTextView.setText("Pobyt do: " + dataCzasEndString);
        dietaTextView.setText("Wysokość diety: " + String.format("%.2f", new BigDecimal(f)) + " " + waluta);


        dlugoscTextView.setText("Długość: " + dni + " dni, " + godz + " godz, " + min + " min, ");

        fetchData process = new fetchData();
        process.execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Wybrano opcje: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.share_item:
                // do your code
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.roblog.michal_pc.kalkulatorpodrysubowej";
                String shareSub = "Share sub";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Udostępnij przez:"));


                return true;
            case R.id.version_item:
                Toast.makeText(this, "Wersja 1", Toast.LENGTH_SHORT).show();
                return true;
            //case R.id.buy_item:
            // do your code
            //    return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
