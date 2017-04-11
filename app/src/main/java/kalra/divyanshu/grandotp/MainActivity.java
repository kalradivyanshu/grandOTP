package kalra.divyanshu.grandotp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //public  lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context c = this;
        final ListView lv = (ListView) findViewById(R.id.otpList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://182.64.26.160/Grand/GetOTP.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray otps = json.getJSONArray("result");
                            List<String> arr = new ArrayList<>();
                            for (int i = 0; i < otps.length(); i++) {
                                String otp = otps.getString(i);
                                arr.add(otp);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, arr);
                            lv.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                        }
                        Log.e("TAG", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "That didn't work.");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
