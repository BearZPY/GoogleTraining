package learning.bear.appinteraction;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActionBarActivity extends AppCompatActivity {

    ShareActionProvider shareActionProvider;
    Intent shareIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        ActionBar actionBar = getSupportActionBar();
        if(null == actionBar)
            return ;
        Button button = (Button) findViewById(R.id.button_setWeb);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.button_setImage);
        if(button != null)
            button.setOnClickListener(buttonClick);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu,menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(Intent.createChooser(shareIntent,"share"));
        return (super.onCreateOptionsMenu(menu));
    }

    View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button_setImage:
                {
                    shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    invalidateOptionsMenu();
                }
                break;
                case R.id.button_setWeb:
                {
                    Uri webPage = Uri.parse("http://www.google.com");
                    shareIntent = new Intent(Intent.ACTION_VIEW, webPage);
                    invalidateOptionsMenu();
                }
                break;
                default:
                    break;
            }
        }
    };
}
