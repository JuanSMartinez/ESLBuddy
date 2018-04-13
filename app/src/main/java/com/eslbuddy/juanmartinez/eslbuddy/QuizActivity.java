package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import backend.Recording;
import backend.Sorter;
import backend.TTSManager;
import backend.TTSSpeaker;
import backend.YandexAPIManager;

public class QuizActivity extends WearableActivity {

    //Text view for the translated word
    private TextView translatedTextView;

    //Recorded response text view
    private TextView recordedResponse;

    //List of words to test
    private ArrayList<Recording> words;

    //index of current trial
    private int indexTrial;

    //Correct response per trial
    private String correctResponse;

    //Speech recognizer
    private SpeechRecognizer speechRecognizer;

    //Recognition result
    String recognitionResult;

    //TTS speaker
    private TTSSpeaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        speaker = new TTSSpeaker(this, YandexAPIManager.getInstance(this).getTranslationCode(), "quiz");
        indexTrial = 0;

        translatedTextView = findViewById(R.id.textViewQuizRecording);
        recordedResponse = findViewById(R.id.recordedTextQuiz);

        words = (ArrayList<Recording>) getIntent().getSerializableExtra(ListOfWordsActivity.LIST);
        Sorter sorter = new Sorter();
        sorter.shuffleArray(words);

        ImageButton micButton = findViewById(R.id.micButton);
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechRecognition();
            }
        });

        // Enables Always-on
        setAmbientEnabled();

        performTrial();

    }

    //Perform a trial
    private void performTrial(){
        if(indexTrial < words.size()){
            Recording trialRecording = words.get(indexTrial);
            String[] data = trialRecording.getRecordedText().split(":");
            correctResponse = data[0];
            String translation = data[1];
            if(TTSManager.getInstance().isOn() && speaker.isInitialized())
                speaker.speakText(translation);
            translatedTextView.setText(translation);
            indexTrial ++;
        }
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
        speechRecognizer.cancel();
        speechRecognizer.destroy();
        super.onDestroy();
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
            recordedResponse.setText(finalResults);


        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            String results = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
            recognitionResult = results;
            recordedResponse.setText(results);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }
}
