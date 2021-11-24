package com.roblog.michal_pc.kalkulatorpodrysubowej;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorldActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SearchableSpinner countryListSpinner;
    Button dateStartBtn, timeStartBtn, dateEndBtn, timeEndBtn, obliczBtn;
    EditText liczbaSniadanET, liczbaObiadowET, liczbaKolacjiET;
    double wartoscDietyPanstwa;
    String waluta;
    Context context;
    int hoursMinutes;
    int dayHourMinutes;
    int minutesMinutes;
    double dietaCalosc;
    String dataCzasStartString;
    String dataCzasEndString;
    int iloscSniadan;
    int iloscObiadow;
    int iloscKolacji;
    Date dataCzasStart;
    Date dataCzasEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        getSupportActionBar().setTitle("KPS - Kalkulator Podróży Służbowej");

        countryListSpinner = (SearchableSpinner) findViewById(R.id.spinner);
        countryListSpinner.setOnItemSelectedListener(this);
        dateStartBtn = (Button) findViewById(R.id.setStartDateBtn);
        timeStartBtn = (Button) findViewById(R.id.setStartTimeBtn);
        dateEndBtn = (Button) findViewById(R.id.setEndDateBtn);
        timeEndBtn = (Button) findViewById(R.id.setEndTimeBtn);
        obliczBtn = (Button) findViewById(R.id.obliczBtn);
        liczbaSniadanET = (EditText) findViewById(R.id.liczbaSniadanET);
        liczbaObiadowET = (EditText) findViewById(R.id.liczbaObiadowET);
        liczbaKolacjiET = (EditText) findViewById(R.id.liczbaKolacjiET);

        countryListSpinner.setTitle("Wybierz kraj");
        countryListSpinner.setPositiveButton("Wyjdź");


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.panstwa, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        countryListSpinner.setAdapter(adapter);

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
                else if (liczbaSniadanET.getText().toString().equals("") || liczbaSniadanET.getText().toString().length() <= 0 || liczbaObiadowET.getText().toString().equals("") || liczbaObiadowET.getText().toString().length() <= 0 || liczbaKolacjiET.getText().toString().equals("") || liczbaKolacjiET.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Wprowadź liczbe sniadan, obiadow i kolacji", Toast.LENGTH_SHORT).show();

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


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        int[] dietyPanstw = getResources().getIntArray(R.array.dietyPanstw);
        String[] waluty = getResources().getStringArray(R.array.waluty);
        wartoscDietyPanstwa = dietyPanstw[pos];
        waluta = waluty[pos];
        Toast.makeText(getApplicationContext(), (String) parent.getItemAtPosition(pos) + " " + wartoscDietyPanstwa + " " + waluta, Toast.LENGTH_SHORT).show();


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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


                // Toast.makeText(getApplicationContext(),"Daty wprowadzone dobrze",Toast.LENGTH_SHORT).show();  // daty wprowadzone dobrze

                long wynik = Math.abs(dataCzasStart.getTime() - dataCzasEnd.getTime());
                int numOfDays = (int) (wynik / (1000 * 60 * 60 * 24));
                int hours = (int) ((wynik / (1000 * 60 * 60)));
                int minutes = (int) (wynik / (1000 * 60)); //minuty

                hoursMinutes = numOfDays; //minutes-(hours*60);
                dayHourMinutes = (minutes - (numOfDays * 24 * 60)) / 60;
                minutesMinutes = minutes - (hours * 60);


                if (dayHourMinutes > 12 && minutesMinutes >= 0) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + wartoscDietyPanstwa;
                }
                if (dayHourMinutes == 12 && minutesMinutes >= 1) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + wartoscDietyPanstwa;
                }
                if (dayHourMinutes == 12 && minutesMinutes == 0) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + (wartoscDietyPanstwa / 2);
                }
                if (dayHourMinutes >= 9 && dayHourMinutes < 12) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + (wartoscDietyPanstwa / 2);
                }
                if (dayHourMinutes == 8 && minutesMinutes > 0) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + (wartoscDietyPanstwa / 2);
                }
                if (dayHourMinutes == 8 && minutesMinutes == 0) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + (wartoscDietyPanstwa / 3);
                }
                if (dayHourMinutes < 8 && dayHourMinutes > 0 && minutesMinutes >= 0) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + (wartoscDietyPanstwa / 3);
                }
                if (dayHourMinutes < 8 && dayHourMinutes >= 0 && minutesMinutes >= 1) {
                    dietaCalosc = (wartoscDietyPanstwa * numOfDays) + (wartoscDietyPanstwa / 3);
                }
                if (dayHourMinutes == 0 && minutesMinutes == 0) {
                    dietaCalosc = wartoscDietyPanstwa * numOfDays;
                }

                dietaCalosc -= iloscSniadan * (wartoscDietyPanstwa * 0.15);
                dietaCalosc -= iloscObiadow * (wartoscDietyPanstwa * 0.30);
                dietaCalosc -= iloscKolacji * (wartoscDietyPanstwa * 0.30);

            } else {
                Toast.makeText(getApplicationContext(), "Daty wprowadzone źle", Toast.LENGTH_SHORT).show();   //daty zle wprowadzone
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
