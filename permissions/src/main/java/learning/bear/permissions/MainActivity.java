package learning.bear.permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static final int MY_PERMISSION_REQUEST_WRITE_CALENDAR = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.requestPermission);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag;
                flag = checkMyPermission(Manifest.permission.WRITE_CALENDAR);
                if(!flag){
                    requestMyPermission(Manifest.permission.WRITE_CALENDAR);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST_WRITE_CALENDAR:
                if(grantResults.length>0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    // TODO: 2016/8/4
                }
                break;
            default:break;
        }
    }

    boolean checkMyPermission(String string){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, string);
        return  (permissionCheck == PackageManager.PERMISSION_GRANTED);
    }

    void requestMyPermission(String string){
        ActivityCompat.requestPermissions(this,
                new String[]{string},MY_PERMISSION_REQUEST_WRITE_CALENDAR);
    }


}
