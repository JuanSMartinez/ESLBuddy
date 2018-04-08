package backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.RecognizerResultsIntent;
import android.util.Log;

import com.eslbuddy.juanmartinez.eslbuddy.RecentWordsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


/**
 * Created by juanmartinez on 3/20/18.
 */

public class CRUDHelper {

    //Date format for string ids
    public final static String DATE_FORMAT = "mm-dd-yyyy'T'HH:mm:ss";

    //Required CRUD operations for recordings and buddies
    public static String createRecording(String recording, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String id = format.format(new Date());
        editor.putString(id, recording);
        editor.commit();
        return id;
    }

    //Delete a recording
    public static String deleteRecording(Recording recording, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(recording.getId());
        editor.commit();
        return recording.getId();
    }

    //Get all the recordings
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

    //Get the most recent Recordings
    public static ArrayList<Recording> getRecentRecordings(Context context){
        //Get all the recordings
        ArrayList<Recording> allRecordings = getRecordings(context);

        //Response array
        ArrayList<Recording> lastRecordings = new ArrayList<>();

        //Sort the array
        Sorter sorter = new Sorter();
        sorter.quickSort(allRecordings, 0, allRecordings.size()-1);

        //Add the latest recordings
        int j = 0;
        for(int i = allRecordings.size() -1; i >= 0  && j < RecentWordsActivity.NUMBER_RECENT_WORDS; i--){
            lastRecordings.add(allRecordings.get(i));
            j++;
        }

        return lastRecordings;

    }


}
