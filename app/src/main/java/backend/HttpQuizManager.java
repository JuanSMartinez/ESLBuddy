package backend;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Interfaces.HttpGetListener;

public class HttpQuizManager {

    //URL for posting quizzes
    public final static String POST_URL = "https://esl-buddy-server.herokuapp.com/quiz";

    //URl for fetching quizzes
    public final static String GET_URL = "https://esl-buddy-server.herokuapp.com/fetch";

    //Request queue
    private RequestQueue queue;

    //Singleton
    private static HttpQuizManager instance = null;

    //Get instance
    public static  HttpQuizManager getInstance(Context context){
        if(instance == null)
            instance = new HttpQuizManager(context);
        return instance;
    }

    private HttpQuizManager(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void postQuiz(String buddyName, String quiz){
        try {
            Log.d("HttpManager", "Creating request");
            JSONObject obj = new JSONObject();
            obj.put("user", buddyName);
            obj.put("quiz", quiz);
            JsonObjectRequest request = new JsonObjectRequest(POST_URL, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("HttpManager", "Response!");
                    Log.d("HttpManager", "Response Obj: " + response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("HttpManager", "Error :(");

                }
            });
            queue.add(request);
        }
        catch (JSONException exception){
            Log.e("HttpManager", "Error creating JSON");
        }
    }

    public void getAQuiz(String buddyName, final HttpGetListener listener){
        Log.d("HttpManager", "Creating request");
        StringRequest request = new StringRequest(GET_URL + "?user=" + buddyName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("HttpManager", "Response!");
                Log.d("HttpManager", "Response Obj: " + response);
                listener.processStringGetResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HttpManager", "Error :( " + error.getMessage());

            }
        });
        queue.add(request);
    }

}
