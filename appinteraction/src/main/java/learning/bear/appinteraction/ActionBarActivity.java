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
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(buttonClick);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu,menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(Intent.createChooser(shareIntent,"share"));
        return (super.onCreateOptionsMenu(menu));
    }

    Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return Intent.createChooser(intent,"Share");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
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
                case R.id.button:
                {
                    shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    invalidateOptionsMenu();
                }
                break;
                case R.id.button2:
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
