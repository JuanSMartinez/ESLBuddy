package backend;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;

import java.util.Locale;
import java.util.Set;

public class TTSSpeaker implements TextToSpeech.OnInitListener{

    //TTS object
    private  TextToSpeech tts;

    //Locale
    private Locale locale;

    //Language code
    private String code;

    //Initialized flag
    private  boolean initialized;

    //Speaker on
    private boolean on = true;

    //Singleton
    private static TTSSpeaker instance = null;

    //Get instance
    public static TTSSpeaker getInstance(Context context){
        if(instance == null)
            instance = new TTSSpeaker(context, "en");
        return instance;
    }

    //Constructor
    public TTSSpeaker(Context context, String languageCode){
        code = languageCode;
        initialized = false;
        on = true;
        tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            initialized = true;
            Locale closest = Locale.forLanguageTag(code);
            tts.setLanguage(closest);
            Set<Voice> voices = tts.getVoices();
            int lowestLatency = Voice.LATENCY_VERY_HIGH;
            Voice selected = tts.getDefaultVoice();
            for(Voice voice : voices){
                if(voice.getLatency() <= lowestLatency
                        && voice.getLocale().getLanguage().equals(code)
                        && !voice.isNetworkConnectionRequired()){
                    lowestLatency = voice.getLatency();
                    selected = voice;
                }
            }
            tts.setVoice(selected);
        }
    }

    public void turnOn(){
        on = true;
    }

    public void turnOff(){
        on = false;
    }

    public boolean isOn (){
        return on;
    }

    public void speakText(String text){
        if(initialized && on) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

        }
    }

    public void changeLanguage(String code){
        if(initialized){
            Locale closest = Locale.forLanguageTag(code);
            tts.setLanguage(closest);
            Set<Voice> voices = tts.getVoices();
            int lowestLatency = Voice.LATENCY_VERY_HIGH;
            Voice selected = tts.getDefaultVoice();
            for(Voice voice : voices){
                if(voice.getLatency() <= lowestLatency
                        && voice.getLocale().getLanguage().equals(code)
                        && !voice.isNetworkConnectionRequired()){
                    lowestLatency = voice.getLatency();
                    selected = voice;
                }
            }
            tts.setVoice(selected);
        }
    }

    public boolean isInitialized(){return initialized;}

    public void finishTTS(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
            instance = null;
        }
    }
}
