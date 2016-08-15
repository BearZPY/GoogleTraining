package learning.bear.appinteraction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShareDataActivity extends AppCompatActivity {

    private static final int PICK_CONTACT_REQUEST = 1;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case PICK_CONTACT_REQUEST:
                if(RESULT_OK == resultCode)
                    Toast.makeText(this, "获取的号码:"+getContactNumber(data)
                            , Toast.LENGTH_LONG).show();
                if(RESULT_CANCELED == resultCode)
                    Toast.makeText(this, "用户取消操作", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
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
            //String title = getResources().getText(R.string.chooser_title).toString();
            // 始终显示选择应用
            startActivity(Intent.createChooser(intent,"Choose Google Map"));
        }
    }

    void openAMap(){
        // 经度：116.39722824； 纬度：39.91724124
        Uri location = Uri.parse("androidamap://viewMap?sourceApplication=App Interaction&poiname=地图标点&lat=39.9&lon=116.4&dev=0");
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

    void sendEmail(){
        //此处必须是mailto
        Uri emailUri = Uri.fromParts("mailto","test@example.com",null);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,emailUri);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    // 启动联系人目前，选择联系人，获取号码
    void getContact(){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    // 从返回的data中读取联系人号码
    String getContactNumber(Intent data){
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
        String number;
        try{
            if( cursor == null)
                return null;
            cursor.moveToFirst();
            // Retrieve the phone number from the NUMBER column
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            number = cursor.getString(column);
            cursor.close();
            // Do something with the phone number...
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return number;
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
                    sendEmail();
                    break;
                case R.id.get_contact:
                    getContact();
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
