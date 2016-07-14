package learning.bear.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 确认 Activity 使用的布局版本包含
        // fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // 不过，如果我们要从先前的状态还原，
            // 则无需执行任何操作而应返回
            // 否则就会得到重叠的 Fragment 。
            if (savedInstanceState != null) {
                return;
            }
        }
        else{
            return;
        }
        // 创建一个要放入 Activity 布局中的新 Fragment
        BlankFragment firstFragment = new BlankFragment();

        // 如果此 Activity 是通过 Intent 发出的特殊指令来启动的，
        // 请将该 Intent 的 extras 以参数形式传递给该 Fragment
        // firstFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container,firstFragment).commit();
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // 创建 Fragment 并为其添加一个参数，用来指定应显示的文章
                BlankFragment2 newFragment = new BlankFragment2();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // 将 fragment_container View 中的内容替换为此 Fragment ，
                // 然后将该事务添加到返回堆栈，以便用户可以向后导航
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                // 执行事务
                transaction.commit();
            }
        });
    }
}
