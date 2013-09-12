package colorpicker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import colorpicker.ColorPicker;
import colorpicker.OpacityBar;
import colorpicker.SVBar;
import sekagra.android.binaryclock.R;


/**
 * Created by Administrator on 13-9-12.
 */
public class Main extends Activity implements ColorPicker.OnColorChangedListener {

    private ColorPicker picker;
    private SVBar svBar;
    private OpacityBar opacityBar;
    private Button button;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set screenOrientation landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        setContentView(R.layout.activity_main);

        picker = (ColorPicker) findViewById(R.id.picker);
        svBar = (SVBar) findViewById(R.id.svbar);
        opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
        button = (Button) findViewById(R.id.button1);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.setOnColorChangedListener(this);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                picker.setOldCenterColor(picker.getColor());

                Intent mIntent = new Intent();
                mIntent.putExtra("clock_colour", picker.getColor());
                // 设置结果，并进行传送
                Main.this.setResult(123, mIntent);
                Main.this.finish();
            }
        });



    }

    @Override
    public void onColorChanged(int color) {
        //gives the color when it's changed.

    }
}