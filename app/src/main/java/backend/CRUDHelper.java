package backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by juanmartinez on 3/20/18.
 */

public class CRUDHelper {

    //Required CRUD operations for recordings and buddies
    public static String createRecording(String recording, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        String id = String.valueOf(System.currentTimeMillis());
        editor.putString(id, recording);
        editor.commit();
        return id;
    }


    public static String deleteRecording(Recording recording, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(recording.getId());
        editor.commit();
        return recording.getId();
    }

    public static ArrayList<Recording> getRecordings(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<Recording> recordings = new ArrayList<>();
        Map<String, ?> key = preferences.getAll();
        for(Map.Entry<String, ?> entry : key.entrySet()){
            String recordingText = (String)entry.getValue();
            String id = entry.getKey();
            Recording recording = new Recording(id, recordingText);
            recordings.add(recording);
        }
        return recordings;
    }


}
