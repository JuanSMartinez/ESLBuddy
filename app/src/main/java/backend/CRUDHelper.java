package backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eslbuddy.juanmartinez.eslbuddy.ListOfWordsActivity;

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

    //Maximum of words in special lists
    public final static int MAX_WORDS = 30;

    //Required CRUD operations for recordings and buddies
    public static String createRecording(String recording, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        //No repeated recordings are added, even if the translation text has changed
        if(getRecordingFromOriginText(context, recording.split(":")[0]) == null) {

            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            String id = format.format(new Date());

            editor.putString(id, recording);
            editor.commit();
            return id;
        }
        else
            return null;
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
            //Check if the saved string is a recording or the id of a wrong answer in a quiz
            if(recordingText.split(":").length == 2) {
                String id = entry.getKey();
                Recording recording = new Recording(id, recordingText);
                recordings.add(recording);
            }
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
        for(int i = allRecordings.size() -1; i >= 0  && j < MAX_WORDS; i--){
            lastRecordings.add(allRecordings.get(i));
            j++;

        }

        return lastRecordings;

    }

    //Save a recording id as a wrong recording from a quiz
    public static String saveRecordingAsWrong(Context context, String wrongRecordingId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if(!findIdSavedAsWrong(context, wrongRecordingId)) {
            String id = String.valueOf(System.currentTimeMillis());
            editor.putString(id, wrongRecordingId);
            editor.commit();
            return id;
        }
        else
            return null;
    }

    //Find a recording ID saved as a wrong answer
    public static boolean findIdSavedAsWrong(Context context, String recordingId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> key = preferences.getAll();
        for(Map.Entry<String, ?> entry : key.entrySet()){
            String recordingText = (String)entry.getValue();

            if(recordingText.split(":").length == 1 && recordingText.equals(recordingId))
                return true;
        }
        return false;
    }

    //Get a recording by ID
    public static Recording getRecordingById(Context context, String searchedId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> key = preferences.getAll();
        for(Map.Entry<String, ?> entry : key.entrySet()){
            String recordingText = (String)entry.getValue();
            String id = entry.getKey();
            if(searchedId.equals(id))
                return new Recording(id, recordingText);
        }
        return null;
    }

    //Get all recordings marked as wrong
    public static ArrayList<Recording> getAllWrongRecordings(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> key = preferences.getAll();
        ArrayList<Recording> response = new ArrayList<>();
        for(Map.Entry<String, ?> entry : key.entrySet()){
            String recordingText = (String)entry.getValue();
            String id = entry.getKey();
            if(recordingText.split(":").length == 1){
                Recording marked = getRecordingById(context, recordingText);
                response.add(marked);
            }
        }
        return response;
    }

    //Get a recording from an origin text
    public static Recording getRecordingFromOriginText(Context context, String text){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> key = preferences.getAll();
        for(Map.Entry<String, ?> entry : key.entrySet()){
            String recordingText = (String)entry.getValue();
            String id = entry.getKey();
            if(text.equals(recordingText.split(":")[0]))
                return new Recording(id, recordingText);
        }
        return null;
    }

    //Delete all recordings
    public static void deleteAllRecordings(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    //Get a random set of recordings
    public static ArrayList<Recording> getRandomRecordings(Context context){
        //All recordings
        ArrayList<Recording> all = getRecordings(context);
        ArrayList<Recording> response = new ArrayList<>();
        int amount = Math.min(all.size(), MAX_WORDS);
        for(int i = 0 ; i < amount; i++){
            int index = (int)Math.random()*all.size();
            Recording random = all.get(index);
            while (response.indexOf(random) != -1){
                index = (int)Math.random()*all.size();
                random = all.get(index);
            }
            response.add(random);
        }
        return response;
    }


}
