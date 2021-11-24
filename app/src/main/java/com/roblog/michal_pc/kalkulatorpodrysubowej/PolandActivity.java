package com.roblog.michal_pc.kalkulatorpodrysubowej;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PolandActivity extends AppCompatActivity {

    Button dateStartBtn, timeStartBtn, dateEndBtn, timeEndBtn, obliczBtn;
    EditText liczbaSniadanET, liczbaObiadowET, liczbaKolacjiET, wysokoscDietyET;
    int iloscSniadan;
    int iloscObiadow;
    int iloscKolacji;
    Context context;
    int hoursMinutes;
    int dayHourMinutes;
    int minutesMinutes;
    double dietaCalosc;
    String dataCzasStartString;
    String dataCzasEndString;
    String waluta = "PLN";
    double wartoscDietyPanstwa;
    Date dataCzasStart;
    Date dataCzasEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poland);

        getSupportActionBar().setTitle("KPS - Kalkulator Podróży Służbowej");

        dateStartBtn = (Button) findViewById(R.id.setStartDateBtn);
        timeStartBtn = (Button) findViewById(R.id.setStartTimeBtn);
        dateEndBtn = (Button) findViewById(R.id.setEndDateBtn);
        timeEndBtn = (Button) findViewById(R.id.setEndTimeBtn);
        obliczBtn = (Button) findViewById(R.id.obliczBtn);
        liczbaSniadanET = (EditText) findViewById(R.id.liczbaSniadanET);
        liczbaObiadowET = (EditText) findViewById(R.id.liczbaObiadowET);
        liczbaKolacjiET = (EditText) findViewById(R.id.liczbaKolacjiET);
        wysokoscDietyET = (EditText) findViewById(R.id.wysokoscDietyET);


        dateStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setFlag(DatePickerFragment.FLAG_START_DATE);
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        timeStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.setFlagtime(TimePickerFragment.FLAG_START_TIME);
                newFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });

        dateEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setFlag(DatePickerFragment.FLAG_END_DATE);
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        timeEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.setFlagtime(TimePickerFragment.FLAG_END_TIME);
                newFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });

        obliczBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Tekst = "Data";
                String Tekst1 = "Godzina";
                if ((dateStartBtn.getText()).equals(Tekst) || timeStartBtn.getText().equals(Tekst1) || dateEndBtn.getText().equals(Tekst) || timeEndBtn.getText().equals(Tekst1))
                    Toast.makeText(getApplicationContext(), "Wprowadź date i godzine", Toast.LENGTH_SHORT).show();
                else if (wysokoscDietyET.getText().toString().equals("") || wysokoscDietyET.getText().toString().length() <= 0 || liczbaSniadanET.getText().toString().equals("") || liczbaSniadanET.getText().toString().length() <= 0 || liczbaObiadowET.getText().toString().equals("") || liczbaObiadowET.getText().toString().length() <= 0 || liczbaKolacjiET.getText().toString().equals("") || liczbaKolacjiET.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Wprowadź liczbe sniadan, obiadow i kolacji, oraz wysokosc diety", Toast.LENGTH_SHORT).show();

                } else {
                    Obliczanie();
                    if (dataCzasStart.compareTo(dataCzasEnd) < 0) {

                        context = getApplicationContext();
                        Intent intent = new Intent(context, ScoreActivity.class);
                        intent.putExtra("dietaCalosc", "" + dietaCalosc);
                        intent.putExtra("dataCzasStartString", "" + dataCzasStartString);
                        intent.putExtra("dataCzasEndString", "" + dataCzasEndString);


                        intent.putExtra("dni", "" + hoursMinutes);
                        intent.putExtra("godziny", "" + dayHourMinutes);
                        intent.putExtra("minuty", "" + minutesMinutes);
                        intent.putExtra("waluta", "" + waluta);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Daty wprowadzone źle", Toast.LENGTH_SHORT).show();   //daty zle wprowadzone
                    }
                }
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
            //  case R.id.buy_item:
            // do your code
            //   return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Obliczanie() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

        try {
            String dataStartString = dateStartBtn.getText().toString();
            String czasStartString = timeStartBtn.getText().toString();
            dataCzasStartString = (dataStartString + "-" + czasStartString);
            dataCzasStart = sdf.parse(dataCzasStartString);

            String dataEndString = dateEndBtn.getText().toString();
            String czasEndString = timeEndBtn.getText().toString();
            dataCzasEndString = (dataEndString + "-" + czasEndString);
            dataCzasEnd = sdf.parse(dataCzasEndString);

            if (dataCzasStart.compareTo(dataCzasEnd) < 0) {

                iloscSniadan = Integer.parseInt(liczbaSniadanET.getText().toString());
                iloscObiadow = Integer.parseInt(liczbaObiadowET.getText().toString());
                iloscKolacji = Integer.parseInt(liczbaKolacjiET.getText().toString());
                wartoscDietyPanstwa = Integer.parseInt(wysokoscDietyET.getText().toString());


                // Toast.makeText(getApplicationContext(),"Daty wprowadzone dobrze",Toast.LENGTH_SHORT).show();  // daty wprowadzone dobrze

                long wynik = Math.abs(dataCzasStart.getTime() - dataCzasEnd.getTime());
                int numOfDays = (int) (wynik / (1000 * 60 * 60 * 24));
                int hours = (int) ((wynik / (1000 * 60 * 60)));
                int minutes = (int) (wynik / (1000 * 60)); //minuty

                hoursMinutes = numOfDays; //minutes-(hours*60);
                dayHourMinutes = (minutes - (numOfDays * 24 * 60)) / 60;
                minutesMinutes = minutes - (hours * 60);

                if (numOfDays < 1) {
                    if ((dayHourMinutes >= 8 && dayHourMinutes < 12) || (dayHourMinutes == 12 && minutesMinutes == 0)) {
                        dietaCalosc = (wartoscDietyPanstwa / 2);
                    }
                    if ((dayHourMinutes == 12 && minutesMinutes > 0) || dayHourMinutes > 12) {
                        dietaCalosc = wartoscDietyPanstwa;
                    }

                    if (dayHourMinutes < 8) {
                        dietaCalosc = 0;
                    }
                }

                if (numOfDays >= 1) {
                    if (dayHourMinutes < 8) {
                        dietaCalosc = (wartoscDietyPanstwa * numOfDays) + (wartoscDietyPanstwa / 2);
                    }

                    if (dayHourMinutes >= 8) {
                        dietaCalosc = (wartoscDietyPanstwa * numOfDays) + wartoscDietyPanstwa;
                    }
                }


                dietaCalosc -= iloscSniadan * (wartoscDietyPanstwa * 0.25);
                dietaCalosc -= iloscObiadow * (wartoscDietyPanstwa * 0.50);
                dietaCalosc -= iloscKolacji * (wartoscDietyPanstwa * 0.25);

            } else {
                Toast.makeText(getApplicationContext(), "Daty wprowadzone źle", Toast.LENGTH_SHORT).show();   //daty zle wprowadzone
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
