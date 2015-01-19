package ua.kpi.campus.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.util.PrefUtils;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class Auth extends AsyncTask<Context, Integer, Integer> {

    private Context mContext;
    public Auth(Context context){
        mContext=context;
    }

    @Override
    protected Integer doInBackground(Context... params) {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet("http://campus-api.azurewebsites.net/User/Auth?login="+PrefUtils.getLogin(mContext)+"&password="+PrefUtils.getPassword(mContext));
        try{

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity httpEntity =response.getEntity();
            String answer = EntityUtils.toString(httpEntity, "UTF-8");

            JSONObject jsonResponce = new JSONObject(answer);
            Log.d("JSONanswer",jsonResponce.toString());
            switch (jsonResponce.getInt("StatusCode")){
                case 200:
                    PrefUtils.putAuthKey(mContext, jsonResponce.getString("Data"));
                    return 200; //it seems everything is good
                case 403:
                    makeSnackBarInUI(403);
                    return 403; //access denied
                case 500:
                    makeSnackBarInUI(500);
                    return 500; //internal server problems
                default:
                    makeSnackBarInUI(0);
                    return 0; //unlisted code
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
            makeSnackBarInUI(-2);
            return -2; //connection problems
        }
        catch(UnknownHostException e){
            makeSnackBarInUI(-3);
            return -3;
        }
        catch(Exception e){
            e.printStackTrace();
            makeSnackBarInUI(-1);
            return -1; //exception raised
        }
    }
    @Override
    protected void onPostExecute(Integer result) {
        if(!PrefUtils.getAuthKey(mContext).isEmpty()){
            PrefUtils.markLoginDone(mContext);
            SnackbarManager.show(
                    Snackbar.with(mContext)
                            .text(mContext.getString(R.string.in_progress)));
            GetCurrentUser gcu = new GetCurrentUser(mContext);

            gcu.execute();
        }

    }

    public void makeSnackBarInUI(final int i){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                switch(i) {
                    case 403:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.invalid_auth_data)));
                        break;
                    case 500:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.server_is_down) + " (500)"));
                        break;
                    case -2:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.server_is_down) + " (-2)"));
                        break;
                    case -1:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.internal_error) + " (-1)"));
                        break;
                    case -3:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.no_internet)));
                        break;
                    default:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.internal_error) + " (d)"));
                        break;
                }
            }
        });
    }

}
