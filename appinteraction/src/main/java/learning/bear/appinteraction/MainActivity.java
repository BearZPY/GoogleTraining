package learning.bear.appinteraction;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // UI
        Button button = (Button) findViewById(R.id.share_data);
        if(button!=null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.share_file);
        if(button!=null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.share_action_bar);
        if(button!=null)
            button.setOnClickListener(buttonClick);
    }

    View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.share_data:
                {
                    Intent intent = new Intent(MainActivity.this,ShareDataActivity.class);
                    startActivity(intent);
                }
                    break;
                case R.id.share_file:
                {
                    Intent intent = new Intent(MainActivity.this,ShareFileActivity.class);
                    startActivity(intent);
                }
                    break;
                case R.id.share_action_bar:
                {
                    Intent intent = new Intent(MainActivity.this,ActionBarActivity.class);
                    startActivity(intent);
                }
                break;
                default:
                    break;
            }
        }
    };
}


/*

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


    private void setShareIntent(Intent shareIntent){
        if(shareActionProvider != null)
            shareActionProvider.setShareIntent(shareIntent);
    }


}
*/