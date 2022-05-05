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

public class createRequestProcess extends AsyncTask<String, Void, String> {

    //initialise AsyncResponse interface as null
    public AsyncResponse delegate = null;

    //after doInBackground finishes executing, use AR interface to return result
    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    @Override
    protected String doInBackground(String... strings) {

        String result = "";

        String senderID = strings[0];
        String recipientID = strings[1];
        String groupID = strings[2];
        String message = strings[3];
        String seen = strings[4];

        String connstr = "https://www.doc.gold.ac.uk/~ddoch001/Year3/FYP/createRequest.php";

        //connect to IGOR and use POST to send request details
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
            String data = URLEncoder.encode("senderID", "UTF-8") + "=" + URLEncoder.encode(senderID, "UTF-8")
                    + "&&" + URLEncoder.encode("recipientID", "UTF-8") + "=" + URLEncoder.encode(recipientID, "UTF-8")
                    + "&&" + URLEncoder.encode("groupID", "UTF-8") + "=" + URLEncoder.encode(groupID, "UTF-8")
                    + "&&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8")
                    + "&&" + URLEncoder.encode("seen", "UTF-8") + "=" + URLEncoder.encode(seen, "UTF-8");

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

        System.out.println("CRP result: " + result);
        return result;
    }
}
