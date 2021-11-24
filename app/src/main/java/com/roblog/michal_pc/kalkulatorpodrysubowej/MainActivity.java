package com.roblog.michal_pc.kalkulatorpodrysubowej;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    ImageButton polandBtn, worldBtn;
    TextView polandTextView, worldTextView;
    Context context;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("KPS - Kalkulator Podróży Służbowej");

        MobileAds.initialize(this, "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        polandBtn = (ImageButton) findViewById(R.id.imageButton);
        worldBtn = (ImageButton) findViewById(R.id.imageButton1);
        polandTextView = (TextView) findViewById(R.id.textView);
        worldTextView = (TextView) findViewById(R.id.textView2);

        polandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context = getApplicationContext();
                Intent intent = new Intent(context, PolandActivity.class);
                startActivity(intent);
            }
        });

        worldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context = getApplicationContext();
                Intent intent = new Intent(context, WorldActivity.class);
                startActivity(intent);
            }
        });

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
            // case R.id.buy_item:
            // do your code
            //  return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

