package com.example.client.serv;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class FonService extends AsyncTask<Void, URL, Void> {

    //String serverName = "https://localhost:44390/";


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            getRequest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void getRequest() throws Exception {
        URL url = new URL("http://example.com/?param=1");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //Если запрос выполнен удачно, читаем полученные данные и далее, делаем что-то
        } else {
            //Если запрос выполнен не удачно, делаем что-то другое
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String res = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        in.close();
    }


}
