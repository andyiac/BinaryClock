package colorpicker.logo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 13-9-12.
 */
public class Welcome extends Activity {
    CircularSeekBar circularSeekbar;

    public static void waitTime(int time) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < (startTime + time))
            ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circularSeekbar = new CircularSeekBar(this);
        circularSeekbar.setMaxProgress(100);

        for (int i = 0; i < 100; i++) {
            waitTime(1);
            circularSeekbar.setProgress(i);
            setContentView(circularSeekbar);
            circularSeekbar.invalidate();
        }



        circularSeekbar.setSeekBarChangeListener(new CircularSeekBar.OnSeekChangeListener() {

            @Override
            public void onProgressChange(CircularSeekBar view, int newProgress) {
                Log.d("Welcome", "Progress:" + view.getProgress() + "/" + view.getMaxProgress());
            }
        });

    }
}