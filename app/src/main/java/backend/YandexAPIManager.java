package backend;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class YandexAPIManager {

    //API Key for the yandex translate service
    public final static String YANDEX_API_KEY = "trnsl.1.1.20180321T150021Z.8dfd187831b0fa28.cab54001c0f043c3f9609188ac21e061460079ec";

    //Recorded text string key
    public final static String RECORDED_TEXT = "Recorded Text";

    //Translated text string key
    public final static String TRANSLATED_TEXT = "Translated Text";

    //Table with languages and codes for languages
    private ArrayList<String> languages;
    private ArrayList<String> languageCodes;

    //Selected language codes
    private String originCode = "en";
    private String translationCode = "ko";

    //Singleton
    private static YandexAPIManager instance = null;

    public static YandexAPIManager getInstance(Context context){
        if(instance == null){
            instance = new YandexAPIManager(context);
        }
        return instance;
    }

    public YandexAPIManager(Context context){

        languageCodes = new ArrayList<>();
        languages = new ArrayList<>();
        loadCSVLanguageFile(context);
    }

    private void loadCSVLanguageFile(Context context){
        try {
            InputStream in = context.getAssets().open("languages.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line = reader.readLine();
            while(line != null){
                Log.d("Debug:", line);
                String[] data = line.split(",");
                languages.add(data[0]);
                languageCodes.add(data[1]);
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getURLRequest(String text){
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" +
                YandexAPIManager.YANDEX_API_KEY +
                "&text="+text +
                "&lang="+originCode+"-"+translationCode;
        return  url;
    }

    public void setOriginCode(String newCode){
        originCode = newCode;
    }

    public void setTranslationCode(String newTranslationCode){
        translationCode = newTranslationCode;
    }

    public ArrayList<String> getLanguageCodes() {
        return languageCodes;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }
}
