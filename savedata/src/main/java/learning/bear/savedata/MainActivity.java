package learning.bear.savedata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {


    int bootTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSharedPreferences();
        testInternalStorage();
    }

    void testSharedPreferences(){
        //SharedPreferences sharedPreferences = this.getSharedPreferences(
        //        getString(R.string.preference_file_key),MODE_PRIVATE);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int bootTimes;
        bootTimes = sharedPref.getInt(getString(R.string.saved_bootTimes), 0);
        bootTimes++;
        editor.putInt(getString(R.string.saved_bootTimes), bootTimes);
        editor.apply();

        TextView textView = (TextView)findViewById(R.id.showBootTimes);
        String string = "Boot times : "+bootTimes+"(By SharedPreferences).";
        textView.setText(string);
    }

    void testInternalStorage(){
        //File file = new File(this.getFilesDir(),getString(R.string.internalStorageFileName));
        FileOutputStream outputStream;
        FileInputStream inputStream;
        String filename = getString(R.string.internalStorageFileName);
        int writeFileValue = 36;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(writeFileValue);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        writeFileValue = 0;
        try{
            inputStream = openFileInput(filename);
            writeFileValue = inputStream.read();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        TextView textView = (TextView)findViewById(R.id.showBootTimes);
        String string = textView.getText().toString()+"\nWriteReadTest："
                +writeFileValue+"(By InternalStorage).";
        textView.setText(string);
    }
}
