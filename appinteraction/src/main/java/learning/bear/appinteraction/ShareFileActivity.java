package learning.bear.appinteraction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;

public class ShareFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_file);

        File docPath = new File(this.getFilesDir(), "docs");
        File docFile = new File(docPath, "test.txt");
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(docFile.getName(), Context.MODE_PRIVATE);
            outputStream.write(36);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //boolean flag = docFile.exists();
        //if(!docFile.exists())

        Uri docUri = FileProvider.getUriForFile(
                ShareFileActivity.this,
                "learning.bear.appinteraction.fileprovider",
                docFile);
        if(docUri != null){
            Intent docIntent = new Intent(
                    Intent.ACTION_VIEW,docUri);
            docIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(docIntent,"Share txt."));
        }
    }
}
