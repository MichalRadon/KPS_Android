package com.roblog.michal_pc.kalkulatorpodrysubowej;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.roblog.michal_pc.kalkulatorpodrysubowej.ScoreActivity.f;
import static com.roblog.michal_pc.kalkulatorpodrysubowej.ScoreActivity.waluta;

public class fetchData extends AsyncTask<Void, Void, Void> {

    Context context;
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    String code = "";
    String effectiveDate = "";
    String mid;
    double rate;
    double dietaXrates;
    String xcode;
    boolean flag;


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/a/?format=json");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray AR = new JSONArray(data);

            for (int i = 0; i < AR.length(); i++) {
                JSONObject OB = (JSONObject) AR.get(i);
                effectiveDate = OB.getString("effectiveDate");
                JSONArray AR2 = OB.getJSONArray("rates");
                for (int j = 0; j < AR2.length(); j++) {
                    JSONObject OB2 = (JSONObject) AR2.get(j);
                    code = OB2.getString("code");
                    if (code.equals(waluta)) {
                        xcode = code;
                        mid = OB2.getString("mid");
                        flag = true;
                    }
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (flag == true) {
            rate = Double.parseDouble(mid);
            dietaXrates = rate * f;
            singleParsed = "Kurs " + xcode + " z dnia : " + effectiveDate + " wynosi: " + mid;
            ScoreActivity.dietaPL.setText(singleParsed);
            // ScoreActivity.jsonTextView.setText(this.singleParsed);
            ScoreActivity.jsonTextView.setText("Wysokość diety po przeliczeniu: " + String.format("%.2f", new BigDecimal(dietaXrates)) + " zł");

        } else {
            ScoreActivity.dietaPL.setText("Brak połączenia z internetem");
            // ScoreActivity.jsonTextView.setText(this.singleParsed);
            ScoreActivity.jsonTextView.setText("Brak połączenia z internetem");
        }

        if (waluta.equals("PLN")) {
            ScoreActivity.dietaPL.setText("");
            // ScoreActivity.jsonTextView.setText(this.singleParsed);
            ScoreActivity.jsonTextView.setText("");
        }

    }
}

