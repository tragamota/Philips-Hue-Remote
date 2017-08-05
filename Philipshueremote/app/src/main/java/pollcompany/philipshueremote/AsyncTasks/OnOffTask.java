package pollcompany.philipshueremote.AsyncTasks;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ian on 3-8-2017.
 */

public class OnOffTask extends AsyncTask<String, Void, Void> {
    private boolean onOffState;

    public OnOffTask(boolean state) {
        onOffState = state;
    }

    @Override
    protected Void doInBackground(String... strings) {
        OutputStream outputStream = null;
        BufferedWriter writer = null;

        String urlAdress = "http://192.168.0.39:8000" + "/api/" + "newdeveloper" + "/lights/" + strings[0] + "/state";

        try {
            URL url = new URL(urlAdress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("USER-AGENT", "HueRemoteApp.4.0");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setDoOutput(true);

            String output = "{\n";
            if(!onOffState) {
                output += ("\"on\": " + "false" + "\n");
            }
            else {
                output += ("\"on\": " + "true" + "\n");
            }
            output += "}";

            outputStream = connection.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(output);
            writer.flush();
            writer.close();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            connection.disconnect();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
