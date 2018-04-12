package backend;

public class TTSManager {

    //Instance
    private static TTSManager instance = null;

    //TTS on option
    private boolean ttsOn;

    //Get instante
    public static TTSManager getInstance(){
        if(instance == null)
            instance = new TTSManager();
        return instance;
    }

    //Private constructor
    private TTSManager(){
        ttsOn = true;
    }

    public void turnOn(){
        ttsOn = true;
    }

    public void turnOff(){
        ttsOn = false;
    }

    public boolean isOn(){return ttsOn;}


}
