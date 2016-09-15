package info.devexchanges.storelistofobjectsbygson;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HongThai
 */
public class MySharedPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "pref";
    private static final String SCORES = "scores";

    public MySharedPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveHighScoreList(String scoreString) {
        editor.putString(SCORES, scoreString);
        editor.commit();
    }

    public String getHighScoreList() {
        return pref.getString(SCORES, "");
    }
}
