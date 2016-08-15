package learning.bear.appinteraction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
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
        button = (Button) findViewById(R.id.open_google_map);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.send_email);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.get_contact);
        if(button != null)
            button.setOnClickListener(buttonClick);
        button = (Button) findViewById(R.id.open_a_map);
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

    void callTel(){
        Uri number = Uri.parse("tel:10000");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);

        // Verify it resolves
        // 确保有APP可以接受这个Intent
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            String title = getResources().getText(R.string.chooser_title).toString();
            // 始终显示选择应用
            startActivity(Intent.createChooser(intent,title));
        }
    }


    void openGoogleMap(){
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent intent = new Intent(Intent.ACTION_VIEW, location);

        // Verify it resolves
        // 确保有APP可以接受这个Intent
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            String title = getResources().getText(R.string.chooser_title).toString();
            // 始终显示选择应用
            startActivity(Intent.createChooser(intent,title));
        }
    }


    void openAMap(){
        // 经度：116.39722824； 纬度：39.91724124
        Uri location = Uri.parse("androidamap://viewMap?sourceApplication=App Interaction&poiname=地图标点&lat=39.9&lon=116.3&dev=0");
        Intent intent = new Intent(Intent.ACTION_VIEW, location);
        String title = getResources().getText(R.string.chooser_title).toString();

        // Start an activity if it's safe
        if (isAppAvailable(this,"com.autonavi.minimap")) {
            // 始终显示选择应用
            startActivity(Intent.createChooser(intent,title));
        }
        else{
            Toast.makeText(this, "请安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(Intent.createChooser(intent,title));
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
                case R.id.open_google_map:
                    openGoogleMap();
                    break;
                case R.id.open_a_map:
                    openAMap();
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
                    //getContact();
                    break;
                default:break;
            }
        }
    };

    boolean isAppAvailable(Context context, String packageName){
        //获取 packageManager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();
        //从 packageInfo 中将包名字逐一取出，压入 pName list中
        if(packageInfo != null){
            for(int i = 0; i < packageInfo.size(); i++){
                String packName = packageInfo.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
