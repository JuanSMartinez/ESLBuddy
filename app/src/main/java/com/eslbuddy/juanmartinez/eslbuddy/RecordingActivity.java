package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import backend.YandexAPIManager;

public class RecordingActivity extends WearableActivity {

    //Request queue for HTTP requests
    private RequestQueue queue;

    //Speech recognizer
    private SpeechRecognizer speechRecognizer;

    //Recorded text text view
    private TextView resultsView;

    //result of recognition
    private String recognitionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        resultsView = findViewById(R.id.partialRecordedTextView);

        //Set HTTP request queue fro translations
        queue = Volley.newRequestQueue(this);

        // Enables Always-on
        setAmbientEnabled();

        //Stop Button callback
        ImageButton stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRecognizer.cancel();
                if(recognitionResult != null){
                    translateText(recognitionResult);
                    //String translation = "(Translation)";
                    //startRecordedTextActivity(recognitionResult, translation);
                }

            }
        });

        //Start speech recognition
        startSpeechRecognition();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    /**
     * Start listening
     */
    private void startSpeechRecognition(){
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        speechRecognizer.setRecognitionListener(listener);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        speechRecognizer.startListening(intent);
    }



    @Override
    protected void onDestroy() {

        if(speechRecognizer != null)
            speechRecognizer.destroy();
        speechRecognizer = null;
        super.onDestroy();
    }

    /**
     * Get translated text from a recorded text
     */
    private void translateText(final String text){

        String url = YandexAPIManager.getInstance(getApplicationContext()).getURLRequest(text);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObj = new JSONObject(response);
                    String translation;
                    if(responseObj.getInt("code") == 200) {
                        JSONArray arrayString = responseObj.getJSONArray("text");
                        translation = arrayString.getString(0);
                    }
                    else
                        translation = "Error code from external translator";
                    startRecordedTextActivity(text, translation);
                }
                catch(Exception e){
                    Log.e("JSON Exception,", "The response JSON could not be formatted");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP Request", error.toString());
            }
        });

        queue.add(request);
    }

    /**
     * Start the recorded text activity
     */
    private void startRecordedTextActivity(String recordedText, String translation){
        Intent intent = new Intent(getApplicationContext(), RecordedTextActivity.class);
        intent.putExtra(YandexAPIManager.RECORDED_TEXT, recordedText);
        intent.putExtra(YandexAPIManager.TRANSLATED_TEXT, translation);

        startActivity(intent);
        finish();
    }


    protected class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            speechRecognizer.cancel();
        }

        @Override
        public void onResults(Bundle results) {
            String finalResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
            recognitionResult = finalResults;
            resultsView.setText(finalResults);

            translateText(finalResults);
            //String translation = "(Translation)";
            //startRecordedTextActivity(finalResults, translation);

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            String results = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
            recognitionResult = results;
            resultsView.setText(results);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }
}
