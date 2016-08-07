package learning.bear.appinteraction;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ShareActionProvider.OnShareTargetSelectedListener{

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    private ShareActionProvider shareActionProvider
            = null;

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
        button = (Button) findViewById(R.id.get_contact);
        button.setOnClickListener(buttonClick);


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Get the intent that started this activity
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if(type.startsWith("text/plain")){
                handleSendText(intent); // Handle text being sent
            }else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        }else if(Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null){

        }else {

        }
    }


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

    void getContact(){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                String number = "None";
                try{
                    cursor.moveToFirst();
                    // Retrieve the phone number from the NUMBER column
                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    number = cursor.getString(column);
                    cursor.close();
                    // Do something with the phone number...
                }catch (Exception e){
                    e.printStackTrace();
                }

                TextView textView = (TextView) findViewById(R.id.showNumber);
                String string = textView.getText().toString() +
                        "\nThe Number is " + number+ ".";
                textView.setText(string);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu,menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        shareActionProvider.setOnShareTargetSelectedListener(this);
        return (super.onCreateOptionsMenu(menu));
    }


    @Override
    public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {

        Toast.makeText(this,"Selected",
                Toast.LENGTH_LONG).show();
        return false;
    }

    private void setShareIntent(Intent shareIntent){
        if(shareActionProvider != null)
            shareActionProvider.setShareIntent(shareIntent);
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
                    //sendEmail();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT,"Test ShareActionProvider!\n");

                    setShareIntent(shareIntent);
                    break;
                case R.id.get_contact:
                    getContact();
                    break;
                default:break;
            }
        }
    };

}
