package learning.bear.appinteraction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class ShareDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button button;
        button = (Button) findViewById(R.id.open_google);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.call_tel);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.open_map);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.send_email);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.get_contact);
        if(button != null)
            button.setOnClickListener(buttonClick);
    }


    void openGoogle(){
        Uri webPage = Uri.parse("http://www.google.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);

        // Verify it resolves
        // 确保有APP可以接受这个Intent
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            String title = getResources().getText(R.string.chooser_title).toString();
            // 始终显示选择应用
            startActivity(Intent.createChooser(webIntent,title));
        }
    }

    View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.open_google:
                    openGoogle ();
                    break;
                case R.id.call_tel:
                    callTel();
                    break;
                case R.id.open_map:
                    openMap();
                    break;
                case R.id.send_email:
                    //sendEmail();
                    //Intent shareIntent = new Intent();
                    //shareIntent.setAction(Intent.ACTION_SEND);
                    //shareIntent.setType("text/plain");
                    //shareIntent.putExtra(Intent.EXTRA_TEXT,"Test ShareActionProvider!\n");

                    //setShareIntent(shareIntent);
                    break;
                case R.id.get_contact:
                    getContact();
                    break;
                default:break;
            }
        }
    };
}
