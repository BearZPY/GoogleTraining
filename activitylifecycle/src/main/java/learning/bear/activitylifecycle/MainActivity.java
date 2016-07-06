package learning.bear.activitylifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView; // Member variable for text view in the layout
    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";
    int mCurrentScore = 10;
    int mCurrentLevel = 20;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
        mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
            mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
        }

        setContentView(R.layout.activity_main);


        mTextView = (TextView) findViewById(R.id.text_message);
        String testString =  (mTextView.getText().toString()
                +"\nonCreate");
        mTextView.setText(testString);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        mTextView = (TextView) findViewById(R.id.text_message);
        String testString =  (mTextView.getText().toString()
                +"\nonResume");
        mTextView.setText(testString);
    }

    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first

        mTextView = (TextView) findViewById(R.id.text_message);
        String testString =  (mTextView.getText().toString()
                +"\nonStart");
        mTextView.setText(testString);
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
        mTextView = (TextView) findViewById(R.id.text_message);
        String testString =  (mTextView.getText().toString()
                +"\nonRestart");
        mTextView.setText(testString);
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        mCurrentScore += 1;

        mTextView = (TextView) findViewById(R.id.text_message);
        String testString =  (mTextView.getText().toString()
                +"\nonPause");
        mTextView.setText(testString);
        /*
        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        if (mCamera != null) {
            mCamera.release()
            mCamera = null;
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

        // Save the note's current draft, because the activity is stopping
        // and we want to be sure the current note progress isn't lost.
        mCurrentLevel += 2;

        mTextView = (TextView) findViewById(R.id.text_message);
        String testString =  (mTextView.getText().toString()
                +"\nonStop");
        mTextView.setText(testString);
    }

    public void onDestroy() {
        super.onDestroy();  // Always call the superclass

        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }
}
