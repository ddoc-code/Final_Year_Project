package com.example.finalyearproject;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class requestAcceptProcess extends AsyncTask<String, Void, String> {

    //initialise AsyncResponse interface as null
    public AsyncResponse delegate = null;

    //after doInBackground finishes executing, use AR interface to return result
    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    //connect to MySQL DB via PHP on IGOR server to update response for this request
    @Override
    protected String doInBackground(String... strings) {

        String result = "";

        //get requestID/senderID from function call
        String requestID = strings[0];
        String senderID = strings[1];

        //connect to requestAccept PHP script on IGOR
        String connstr = "https://www.doc.gold.ac.uk/~ddoch001/Year3/FYP/requestAccept.php";

        //connect to IGOR and use POST to send requestID
        try {
            URL url = new URL(connstr);

            //create connection
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            //encode data to UTF-8
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("requestID", "UTF-8") + "=" + URLEncoder.encode(requestID, "UTF-8")
                    + "&&" + URLEncoder.encode("senderID", "UTF-8") + "=" + URLEncoder.encode(senderID, "UTF-8");

            //send data
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            //read JSON response from PHP script
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }

            //close everything and disconnect
            reader.close();
            ips.close();
            http.disconnect();

        } catch (MalformedURLException e) {
//            e.printStackTrace();
            result = e.getMessage();
        } catch (IOException e) {
//            e.printStackTrace();
            result = e.getMessage();
        }

        System.out.println("RAP result: " + result);
        return result;
    }
}
