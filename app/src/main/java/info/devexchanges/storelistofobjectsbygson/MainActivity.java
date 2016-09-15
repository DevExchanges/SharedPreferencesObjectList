package info.devexchanges.storelistofobjectsbygson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Hong Thai
 */

public class MainActivity extends AppCompatActivity {

    private View btnAdd;
    private View btnGet;
    private EditText textName;
    private EditText textScore;
    private ViewGroup buttonLayout, inputLayout, listViewLayout;
    private ListView listScore;
    private View btnOK;
    private View btnCancel;

    private ArrayList<HighScore> scores;
    private MySharedPreference sharedPreference;
    private HashSet<String> scoreset;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //scores = new ArrayList<HighScore>();
        gson = new Gson();
        sharedPreference = new MySharedPreference(getApplicationContext());
        getHighScoreListFromSharedPreference();
        findView();

        //set event for buttons
        btnAdd.setOnClickListener(onAddListener());
        btnGet.setOnClickListener(onGettingDataListener());
        btnOK.setOnClickListener(onConfirmListener());
        btnCancel.setOnClickListener(onCancelListener());
    }

    private void findView() {
        listScore = (ListView)findViewById(R.id.list);
        buttonLayout = (ViewGroup)findViewById(R.id.layout_add);
        inputLayout = (ViewGroup)findViewById(R.id.layout_input);
        btnAdd = findViewById(R.id.btn_add);
        btnGet = findViewById(R.id.btn_get);
        btnOK = findViewById(R.id.btn_ok);
        btnCancel = findViewById(R.id.btn_cancel);
        textName = (EditText)findViewById(R.id.txt_name);
        textScore = (EditText)findViewById(R.id.txt_score);
        listViewLayout = (ViewGroup)findViewById(R.id.layout_listview);
    }

    /**
     * Save list of scores to own sharedpref
     * @param scoresList
     */
    private void saveScoreListToSharedpreference(ArrayList<HighScore> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.saveHighScoreList(jsonScore);
    }

    /**
     * Retrieving data from sharepref
     */
    private void getHighScoreListFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreList();
        Type type = new TypeToken<List<HighScore>>(){}.getType();
        scores = gson.fromJson(jsonScore, type);

        if (scores == null) {
            scores = new ArrayList<>();
        }
    }

    private View.OnClickListener onAddListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewLayout.setVisibility(View.GONE);
                inputLayout.setVisibility(View.VISIBLE);
            }
        };
    }

    private View.OnClickListener onGettingDataListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scores.size() == 0) {
                    Toast.makeText(MainActivity.this, "No data in sharedPreferences", Toast.LENGTH_SHORT).show();
                } else {
                    getHighScoreListFromSharedPreference(); //get data
                    //set adapter for listview and visible it
                    ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.item_listview, scores);
                    listScore.setAdapter(adapter);
                    listViewLayout.setVisibility(View.VISIBLE);
                    inputLayout.setVisibility(View.GONE);
                }
            }
        };
    }

    private View.OnClickListener onConfirmListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textScore.getText().toString().equals("") && !textName.getText().toString().equals("")) {
                    //not null input
                    HighScore highScore = new HighScore();
                    highScore.setScore(textScore.getText().toString());
                    highScore.setPlayerName(textName.getText().toString());
                    scores.add(highScore); //add to scores list
                    saveScoreListToSharedpreference(scores); //save to share pref

                    //clear edit text data
                    textName.setText("");
                    textScore.setText("");
                } else {
                    Log.e("Activity", "null input");
                }

            }
        };
    }

    private View.OnClickListener onCancelListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gone input layout
                inputLayout.setVisibility(View.GONE);
            }
        };
    }
}