package zzept.com.pams3testdemo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import com.expert.pams.Pams3Hardware;

public class FlashLightActivity extends Activity implements OnClickListener {
    private Pams3Hardware pams3Hardware = new Pams3Hardware();
    private ImageButton mIv_flashlight = null;
    private Button mBt_closeopen;
    private Instrumentation inst = new Instrumentation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashlight);
        initFrame();
        pams3Hardware.init_native();
    }

    public void initFrame() {
        this.findViewById(R.id.bt_back).setOnClickListener(this);
        mBt_closeopen = (Button) this.findViewById(R.id.bt_openclose);
        mBt_closeopen.setOnClickListener(this);
        mIv_flashlight = (ImageButton) this.findViewById(R.id.iv_flashlight);
        if (getLightState()) {
            mIv_flashlight.setBackgroundResource(R.drawable.shou_on);
            mBt_closeopen.setText("关闭");
        } else {
            mIv_flashlight.setBackgroundResource(R.drawable.shou_off);
            mBt_closeopen.setText("打开");
        }

        mIv_flashlight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (getLightState()) {
                    openCloseflash(false);
                    mBt_closeopen.setText("打开");
                    mIv_flashlight.setBackgroundResource(R.drawable.shou_off);
                    setLightState(false);
                } else {
                    openCloseflash(true);
                    mBt_closeopen.setText("关闭");
                    mIv_flashlight.setBackgroundResource(R.drawable.shou_on);
                    setLightState(true);

                }
            }
        });
        inst.setInTouchMode(true);
        inst.setInTouchMode(false);

    }

    public void openCloseflash(boolean is) {
        if (is) {
            pams3Hardware.Io_CameraLedEnable();
        } else {
            pams3Hardware.Io_CameraLedDisable();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_back) {
            finish();
        } else if (v.getId() == R.id.bt_openclose) {
            if (getLightState()) {
                openCloseflash(false);
                mBt_closeopen.setText("打开");
                mIv_flashlight.setBackgroundResource(R.drawable.shou_off);
               setLightState(false);
            } else {
                openCloseflash(true);
                mBt_closeopen.setText("关闭");
                mIv_flashlight.setBackgroundResource(R.drawable.shou_on);
                setLightState(true);
            }
        }

    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_ENTER:
                    if (getLightState()) {
                        openCloseflash(false);
                        mBt_closeopen.setText("打开");
                        mIv_flashlight.setBackgroundResource(R.drawable.shou_off);
                        setLightState(false);
                    } else {
                        openCloseflash(true);
                        mBt_closeopen.setText("关闭");
                        mIv_flashlight.setBackgroundResource(R.drawable.shou_on);
                        setLightState(true);
                    }
                    return true;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;

                case KeyEvent.KEYCODE_TAB:
                    return true;
                case KeyEvent.KEYCODE_SPACE:
                    if (getLightState()) {
                        openCloseflash(false);
                        mBt_closeopen.setText("打开");
                        mIv_flashlight.setBackgroundResource(R.drawable.shou_off);
                        setLightState(false);
                    } else {
                        openCloseflash(true);
                        mBt_closeopen.setText("关闭");
                        mIv_flashlight.setBackgroundResource(R.drawable.shou_on);
                        setLightState(true);

                    }
                    return true;
                default:
                    break;
            }

        }
        return super.dispatchKeyEvent(event);
    }

    private  void setLightState( boolean Value) {
        SharedPreferences settings = FlashLightActivity.this.getSharedPreferences("SystemInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("light", Value);
        editor.commit();
    }

    private  boolean getLightState() {
        SharedPreferences settings = FlashLightActivity.this.getSharedPreferences("SystemInfo", Activity.MODE_PRIVATE);
       return settings.getBoolean("light",false);
    }

}
