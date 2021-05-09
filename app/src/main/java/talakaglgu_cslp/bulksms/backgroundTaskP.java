package talakaglgu_cslp.bulksms;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by
 * Marvin Kao
 * on 2/12/2018.
 */

public class backgroundTaskP extends AsyncTask<String,Integer,String> {
    private MainActivity caller;
    backgroundTaskP (MainActivity caller) {
        this.caller = caller;
    }
    @Override
    protected String doInBackground(String... voids) {
        try {
            String host_url ="http://"+voids[0]+"/FarmSend/"+voids[1]+"/"+voids[2]+"/"+voids[3]+".php";
            URL url = new URL(host_url);
            HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
            httpconn.setDoOutput(true);
            httpconn.setDoInput(true);
            InputStream input = httpconn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input,"iso-8859-1"));
            String result="";
            String line;
            while((line = reader.readLine())!= null){
                result += line;
            }
            reader.close();
            input.close();
            httpconn.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        caller.getDataP(result);
    }
}
