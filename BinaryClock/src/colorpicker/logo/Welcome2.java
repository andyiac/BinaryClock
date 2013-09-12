package colorpicker.logo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import sekagra.android.binaryclock.BinaryClockActivity;
import sekagra.android.binaryclock.R;

/**
 * Created by Administrator on 13-9-12.
 */
public class Welcome2 extends Activity {

    private int CLOCL_COLOR = 0xFFFFFFFF;
    public static final String PREFS_NAME = "MyPrefsFile";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set screenOrientation landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.welcome2);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        CLOCL_COLOR =   settings.getInt("mColor", CLOCL_COLOR);
        TextView textView = (TextView)findViewById(R.id.fullscreen_content);
        textView.setTextColor(CLOCL_COLOR);


        final Intent it = new Intent(this, BinaryClockActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                startActivity(it);
                Welcome2.this.finish();
            }
        };
        timer.schedule(task, 1000 * 2); // 2秒后自动跳转


    }
}