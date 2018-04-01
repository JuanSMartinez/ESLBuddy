package backend;

/**
 * Created by juanmartinez on 3/20/18.
 */

public class Recording {

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
}
