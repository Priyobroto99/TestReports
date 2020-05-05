package com.priyo.testreport.models;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestCasesModel extends ViewModel {

    private static final String TAG = "TestCasesModel";

    private MutableLiveData<String> allProjects = new MutableLiveData<>();

    public MutableLiveData<String> getallTestCases() {
        TesTCases downloadTask = new TesTCases();
        try {
            downloadTask.execute(new URL("https://testapi-priyo.herokuapp.com/get-all-testcases"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allProjects;
    }


    public MutableLiveData<String> getTestcasesbyRange(String from,String to) {
        TesTCases downloadTask = new TesTCases();
        try {
            downloadTask.execute(new URL("https://testapi-priyo.herokuapp.com/get-testcases?from="+from+"&to="+to));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allProjects;
    }

    public class TesTCases extends AsyncTask<URL, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            allProjects.setValue(s);

        }

        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection httpURLConnection = null;
            String result = "";
            try {
                URL url = urls[0];
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }
    }
}
