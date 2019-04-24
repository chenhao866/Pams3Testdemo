package zzept.com.pams3testdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.File;

public class MainActivity extends Activity implements OnClickListener {

    private UIHolder mUIHolder = new UIHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getMediaDir();
    }

    public File getMediaDir() {
        File dir = new File(Environment.getExternalStorageDirectory(), "pams3_camera");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private void initViews() {
        mUIHolder.btnPower = (Button) findViewById(R.id.btn_Power);
        mUIHolder.btnVib = (Button) findViewById(R.id.btn_vib);
        mUIHolder.btnTemp = (Button) findViewById(R.id.btn_temp);
        mUIHolder.btn_card = (Button) findViewById(R.id.btn_card);
        mUIHolder.btn_back = (Button) findViewById(R.id.btn_back);
        mUIHolder.btn_key = (Button) findViewById(R.id.btn_key);
        mUIHolder.btn_light = (Button) findViewById(R.id.btn_light);
        mUIHolder.btn_camera = (Button) findViewById(R.id.btn_camera);
        mUIHolder.btnPower.setOnClickListener(this);
        mUIHolder.btnVib.setOnClickListener(this);
        mUIHolder.btnTemp.setOnClickListener(this);
        mUIHolder.btn_back.setOnClickListener(this);
        mUIHolder.btn_card.setOnClickListener(this);
        mUIHolder.btn_key.setOnClickListener(this);
        mUIHolder.btn_light.setOnClickListener(this);
        mUIHolder.btn_camera.setOnClickListener(this);
    }

    class UIHolder {
        Button btnVib;
        Button btnTemp;
        Button btnPower;
        Button btn_back;
        Button btn_card;
        Button btn_key;
        Button btn_light;
        Button btn_camera;
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.btn_Power:
                intent.setClass(MainActivity.this, OEMPowerManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_vib:
                intent.setClass(MainActivity.this, VibrationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_temp:
                intent.setClass(MainActivity.this, TemperatureActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_card:
                intent.setClass(MainActivity.this, CardActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_key:
                intent.setClass(MainActivity.this, OEMTestKeyBoardActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_camera:
                intent.setClass(MainActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_light:
                intent.setClass(MainActivity.this, FlashLightActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_back:
                finish();
                break;

            default:
                break;
        }
    }
}
