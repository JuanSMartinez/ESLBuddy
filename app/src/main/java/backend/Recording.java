package backend;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by juanmartinez on 3/20/18.
 */

public class Recording implements Comparable<Recording>{


    //ID
    private String id;

    //Raw recorded text and its translation separated by :
    private String recordedText;

    /**
     * Constructor
     * @param id Unique id for the recording
     * @param recordedText Raw recorded text
     */
    public Recording(String id, String recordedText){
        this.setId(id);
        this.setRecordedText(recordedText);
    }


    /**
     * Gets the id
     * @return the id of the recording
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id
     * @param id the unique id of the recording
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Gets the recorded text
     * @return  the raw recorded text
     */
    public String getRecordedText() {
        return recordedText;
    }

    /**
     * Sets the recorded text
     * @param recordedText the new raw recorded text
     */
    public void setRecordedText(String recordedText) {
        this.recordedText = recordedText;
    }

    @Override
    public int compareTo(@NonNull Recording o){
        SimpleDateFormat format = new SimpleDateFormat(CRUDHelper.DATE_FORMAT);
        try{
            Date myDate = format.parse(id);
            Date otherDate = format.parse(o.getId());
            return myDate.compareTo(otherDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return id.compareTo(o.getId());
        }

    }
}
