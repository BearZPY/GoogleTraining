package learning.bear.appinteraction;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_file);

        File path = new File(getFilesDir(),"docs");
        File docFile = new File(path,"myTest.txt");
        if(path.mkdirs()){
            Toast.makeText(this, "新建docs文件夹"
                    , Toast.LENGTH_LONG).show();
        }


        try{
            String testString = "This is a test.txt file.";
            byte testByte[] = testString.getBytes();

            FileOutputStream outputStream = new FileOutputStream(docFile);
            outputStream.write(testByte);
            outputStream.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        //boolean flag = docFile.exists();
        Uri contentUri = FileProvider.getUriForFile(this,
                "learning.bear.appinteraction.fileprovider",
                docFile);


        Intent shareIntent = new Intent();
        shareIntent.setData(contentUri);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent,"Share myTest.txt"));
        finish();
    }
}
