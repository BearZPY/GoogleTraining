package learning.bear.savedata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    int bootTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //SharedPreferences sharedPref = getPreferences(
        //            Context.MODE_PRIVATE);




        SharedPreferences.Editor editor = sharedPref.edit();

        int defaultValue = Integer.parseInt(getString(R.string.boot_times_default));
        //int defaultValue = 0;
        int bootTimes = sharedPref.getInt(getString(R.string.boot_times), defaultValue);


        bootTimes++;

        editor.putInt(getString(R.string.boot_times),bootTimes);
        editor.apply();

        TextView textView = (TextView)findViewById(R.id.showBootTimes);
        String string = "Boot times : " + bootTimes + ".";
        textView.setText(string);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
