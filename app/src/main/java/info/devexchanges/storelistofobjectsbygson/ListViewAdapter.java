package info.devexchanges.storelistofobjectsbygson;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hong Thai
 */
public class ListViewAdapter extends ArrayAdapter<HighScore> {

    private Activity activity;

    public ListViewAdapter(Activity activity, int resource, List<HighScore> scores) {
        super(activity, resource, scores);
        this.activity = activity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        HighScore score = getItem(position);

        holder.playerName.setText(score.getPlayerName());
        holder.score.setText(score.getScore());

        return convertView;
    }

    private static class ViewHolder {
        private TextView playerName;
        private TextView score;

        public ViewHolder(View v) {
            playerName = (TextView) v.findViewById(R.id.name);
            score = (TextView) v.findViewById(R.id.score);
        }
    }
}