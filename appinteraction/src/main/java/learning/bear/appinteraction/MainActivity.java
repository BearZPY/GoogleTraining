package learning.bear.appinteraction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button;
        button = (Button) findViewById(R.id.open_google);
        button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.call_tel);
        button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.open_map);
        button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.send_email);
        button.setOnClickListener(buttonClick);
    }

    View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.open_google:
                    openGoogle();
                    break;
                case R.id.call_tel:
                    callTel();
                    break;
                case R.id.open_map:
                    openMap();
                    break;
                case R.id.send_email:
                    sendEmail();
                    break;
                default:break;
            }
        }
    };

    void openGoogle(){
        Uri webPage = Uri.parse("http://www.google.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);

        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(webIntent);
        }
    }

    void callTel(){
        Uri number = Uri.parse("tel:18115176827");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(callIntent);
        }
    }

    void openMap(){
        // Build the intent
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(mapIntent);
        }
    }

    void sendEmail(){
        // FIXME : solve email and Chooser
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = getResources().getString(R.string.chooser_title);
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(emailIntent, title);

        // Verify the intent will resolve to at least one activity
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }

        // The intent does not have a URI, so declare the "text/plain" MIME type
        // emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        // emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
        // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        // emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        // emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        // You can also attach multiple items by passing an ArrayList of Uris
    }
}
