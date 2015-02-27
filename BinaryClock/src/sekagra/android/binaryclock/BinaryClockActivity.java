package sekagra.android.binaryclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import colorpicker.Activity.Main;

public class BinaryClockActivity extends Activity {
    private Timer _timer;
    private int CLOCL_COLOR = 0xFFFFFFFF;
    public static final String PREFS_NAME = "MyPrefsFile";

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            Date __date = new Date();

            //Set the labels to display the current time
     /*       ((TextView) findViewById(R.id.lblHours)).setText(String.format("%02d", __date.getHours()));
            ((TextView) findViewById(R.id.lblMinutes)).setText(String.format("%02d", __date.getMinutes()));
            ((TextView) findViewById(R.id.lblSeconds)).setText(String.format("%02d", __date.getSeconds()));
*/
            //Set the views to display the current in its binary representation
            SetDigit((LinearLayout) findViewById(R.id.linearLayoutHours2), __date.getHours() / 10);
            SetDigit((LinearLayout) findViewById(R.id.linearLayoutHours1), __date.getHours() % 10);

            SetDigit((LinearLayout) findViewById(R.id.linearLayoutMinutes2), __date.getMinutes() / 10);
            SetDigit((LinearLayout) findViewById(R.id.linearLayoutMinutes1), __date.getMinutes() % 10);

            SetDigit((LinearLayout) findViewById(R.id.linearLayoutSeconds2), __date.getSeconds() / 10);
            SetDigit((LinearLayout) findViewById(R.id.linearLayoutSeconds1), __date.getSeconds() % 10);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set screenOrientation landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.main);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        CLOCL_COLOR =   settings.getInt("mColor", CLOCL_COLOR);

        _timer = new Timer(true);
        _timer.schedule(new TimerTask() {
            public void run() {
                TimerMethod(); //every tick executes this method in the background thread
            }
        }, 100, 200);
    }

    //pass the job to a method on the UI
    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private void SetDigit(LinearLayout group, int digit) {
        //Convert the digit to an boolean array
        boolean[] __values = IntToBoolArray(digit, group.getChildCount());

        //Iterate through all blocks an apply the correct color
        for (int i = 0; i < group.getChildCount(); i++) {
            //reverse, because the binary clock shows the digits from the bottom to the top
            if (__values[i])
                group.getChildAt(group.getChildCount() - 1 - i).setBackgroundColor(CLOCL_COLOR);
            else
                group.getChildAt(group.getChildCount() - 1 - i).setBackgroundColor(0x66FFFFFF);
        }
    }

    private boolean[] IntToBoolArray(int value, int digits) {
        boolean[] __binary = new boolean[digits];

        for (int i = 0; value > 0 || i < digits; i++) {
            __binary[i] = (value % 2) != 0;
            value /= 2;
        }

        return __binary;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(BinaryClockActivity.this, Main.class);
                startActivityForResult(intent, 123);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            CLOCL_COLOR = data.getIntExtra("clock_colour", CLOCL_COLOR);
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("mColor",CLOCL_COLOR);
            // Commit the edits!
            editor.commit();
        }
    }
}
