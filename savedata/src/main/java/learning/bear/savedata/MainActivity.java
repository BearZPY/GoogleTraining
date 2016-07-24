package learning.bear.savedata;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    //private FeedReaderDbHelper mDbHelper;
    private Context mContext = this;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 记录显示bootTimes 启动次数
        testSharedPreferences();
        // 写入内部存储文件一个值并读取显示
        testInternalStorage();
        // 写入内部缓存文件一个值并读取显示
        testInternalCache();
        //verifyStoragePermissions(this);
        // 写入外部公开存储文件一个值并读取显示
        testExternalPublicStorage();
        // 写入外部私有存储文件一个值并读取显示
        testExternalPrivateStorage();
        //testSQLite(this);

        Button button;
        button =(Button)findViewById(R.id.button_new_db);
        button.setOnClickListener(buttonClick);
        button =(Button)findViewById(R.id.button_update_db);
        button.setOnClickListener(buttonClick);
        button =(Button)findViewById(R.id.button_insert_data);
        button.setOnClickListener(buttonClick);
        button =(Button)findViewById(R.id.button_update_db);
        button.setOnClickListener(buttonClick);
        button =(Button)findViewById(R.id.button_query_data);
        button.setOnClickListener(buttonClick);
        button =(Button)findViewById(R.id.button_delete_data);
        button.setOnClickListener(buttonClick);
    }

    void testSharedPreferences() {
        //SharedPreferences sharedPreferences = this.getSharedPreferences(
        //        getString(R.string.preference_file_key),MODE_PRIVATE);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int bootTimes;
        bootTimes = sharedPref.getInt(getString(R.string.saved_bootTimes), 0);
        bootTimes++;
        editor.putInt(getString(R.string.saved_bootTimes), bootTimes);
        editor.apply();

        TextView textView = (TextView) findViewById(R.id.showBootTimes);
        String string = "Boot times : " + bootTimes + "(By SharedPreferences).";
        textView.setText(string);
    }

    void testInternalStorage() {
        //File file = new File(this.getFilesDir(),getString(R.string.internalStorageFileName));
        FileOutputStream outputStream;
        FileInputStream inputStream;
        String filename = getString(R.string.internalStorageFileName);
        //int writeFileValue = 36;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(36);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int writeFileValue = 0;
        try {
            inputStream = openFileInput(filename);
            writeFileValue = inputStream.read();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView textView = (TextView) findViewById(R.id.showBootTimes);
        String string = textView.getText().toString() + "\nWriteReadTest："
                + writeFileValue + "(By InternalStorage).";
        textView.setText(string);
    }

    void testInternalCache() {
        String fileName = getString(R.string.internalCacheFileName);
        File file = getTempFile(fileName);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(78);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int writeFileValue = 0;

        try {
            InputStream inputStream = new FileInputStream(file);
            writeFileValue = inputStream.read();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) findViewById(R.id.showBootTimes);
        String string = textView.getText().toString() + "\nWriteReadTest："
                + writeFileValue + "(By InternalCache).";
        textView.setText(string);
    }

    void testExternalPublicStorage() {
        verifyStoragePermissions(this);
        String fileName = getString(R.string.eStoragePublicFileName);
        if (!isExternalStorageWritable())
            return;
        File path = getPublicDocumentStorageDir();
        File file = new File(path, fileName);
        String value = "Hi!";
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(value.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        value = "0";
        if (!isExternalStorageReadable())
            return;
        verifyStoragePermissions(this);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            value = bufferedReader.readLine();
            bufferedReader.close();
/*          InputStream inputStream = new FileInputStream(file);
            byte[] inCh = new byte[100];
            inputStream.read(inCh);
            value = inCh.toString();
            inputStream.close();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView textView = (TextView) findViewById(R.id.showBootTimes);
        String string = textView.getText().toString() + "\nWriteReadTest："
                + value + "(By ExternalPublicStorage).";
        textView.setText(string);
    }

    void testExternalPrivateStorage() {
        String fileName = getString(R.string.eStoragePrivateFileName);
        if (!isExternalStorageWritable())
            return;
        File file = getPrivateDocumentStorageDir(fileName);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(254);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int writeFileValue = 0;
        if (!isExternalStorageReadable())
            return;
        try {
            InputStream inputStream = new FileInputStream(file);
            writeFileValue = inputStream.read();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView textView = (TextView) findViewById(R.id.showBootTimes);
        String string = textView.getText().toString() + "\nWriteReadTest："
                + writeFileValue + "(By ExternalPrivateStorage).";
        textView.setText(string);
    }



    public File getTempFile(String fileName) {
        File file;
        try {
            file = File.createTempFile(fileName, null, this.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
            return null;
        }
        return file;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public File getPrivateDocumentStorageDir(String documentName) {
        // Get the directory for the app's private document directory.
        File file = new File(this.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), documentName);
        if (!file.exists())
            if (!file.mkdirs())
                return null;
        return file;
    }

    public File getPublicDocumentStorageDir() {
        // Get the directory for the user's public document directory.
        File file = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        if (!file.exists())
            if (!file.mkdirs())
                return null;
        return file;
    }

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
        permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    View.OnClickListener buttonClick = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.button_new_db:
                    String path = "/data/data/learning.bear.savedata/databases/my_database.db";
                    db=SQLiteDatabase.openOrCreateDatabase(path,null);
                    //创建表SQL语句
                    String stu_table="create table usertable(_id integer primary key autoincrement,sname text,snumber text)";
                    //执行SQL语句
                    db.execSQL(stu_table);
                    //mDbHelper = new FeedReaderDbHelper(mContext);
                    //db = mDbHelper.getWritableDatabase();
                    break;
                case R.id.button_update_db:
                case R.id.button_insert_data:
                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    //values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, "id");
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "title");
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT, "content");

                    // Insert the new row, returning the primary key value of the new row
                    long newRowId;
                    newRowId = db.insert(
                            FeedReaderContract.FeedEntry.TABLE_NAME,
                            null,
                            //FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                            values);
                    break;
                case R.id.button_update_data:
                case R.id.button_query_data:
                case R.id.button_delete_data:
                default:break;
            }
        }
    };

}

