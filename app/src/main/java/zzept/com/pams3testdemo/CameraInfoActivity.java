package zzept.com.pams3testdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.expert.pams.Pams3Hardware;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class CameraInfoActivity extends Activity implements OnClickListener, PictureCallback {

    public static final String TAG = "CameraInfoActivity";
    private Button mBt_camera;
    private View lyCar;
    private Button mBt_back;
    private View btnLight;
    private ImageView imgLight;

    private CameraSurfacePreview mCameraSurPreview = null;
    private Pams3Hardware pams3Hardware = new Pams3Hardware();

    private boolean isCanTakePhoto = true;
    private long lastTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.camerainfo);
        isCanTakePhoto = true;
        initFrame();
        initDate();

        IntentFilter itf = new IntentFilter("android.intent.action.SCREEN_OFF");
        registerReceiver(mScreenOReceiver, itf);

    }

    private BroadcastReceiver mScreenOReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                finish();
            }
        }
    };


    private void initDate() {
        if (!getLightState()) {
            imgLight.setImageResource(R.drawable.turn_off);
        } else {
            imgLight.setImageResource(R.drawable.turn_on);
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
    }


    public void initFrame() {
        mBt_camera = (Button) this.findViewById(R.id.bt_camera);
        mBt_back = (Button) this.findViewById(R.id.bt_back);
        lyCar = findViewById(R.id.rl_tail);
        btnLight = findViewById(R.id.btn_light);
        imgLight = (ImageView) findViewById(R.id.img_light);

        mBt_camera.setClickable(true);
        mBt_back.setClickable(true);
        mBt_camera.setOnClickListener(this);
        mBt_back.setOnClickListener(this);
        btnLight.setOnClickListener(this);

        FrameLayout preview = (FrameLayout) findViewById(R.id.video);
        mCameraSurPreview = new CameraSurfacePreview(this);
        preview.addView(mCameraSurPreview);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_camera:
                takePhoto();
                break;
            case R.id.bt_back:
                finish();
                break;
            case R.id.btn_light:
                if (!getLightState()) {
                    imgLight.setImageResource(R.drawable.turn_on);
                    setLightState(true);
                } else {
                    imgLight.setImageResource(R.drawable.turn_off);
                    setLightState(false);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void finish() {
        pams3Hardware.Io_CameraLedDisable();
        super.finish();

    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        try {
            pams3Hardware.Io_CameraLedDisable();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            //存在日期文件名在windows上不能保存，所有做修改为时间戳
            File f = new File(getMediaDir(), getPhotoFileName());
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            camera.stopPreview();
            Intent intent = new Intent();
            intent.putExtra("PicName", f.getName());
            setResult(1, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void takePhoto() {
        if (isCanTakePhoto) {
            mBt_camera.setClickable(false);
            mBt_back.setClickable(false);
            mCameraSurPreview.takePicture(this);
            if (getLightState()) {
                pams3Hardware.Io_CameraLedEnable();
            }
            lyCar.setBackgroundColor(Color.BLACK);
            isCanTakePhoto = false;
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_ENTER:
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime > 500) {
                        lastTime = currentTime;
                        takePhoto();
                    }
                    return true;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
                case KeyEvent.KEYCODE_TAB:
                    return true;
                default:
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        unregisterReceiver(mScreenOReceiver);
        super.onDestroy();

    }

    //此处应该放在全局中，做记录
    private void setLightState(boolean Value) {
        SharedPreferences settings = CameraInfoActivity.this.getSharedPreferences("SystemInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("light", Value);
        editor.commit();
    }

    private boolean getLightState() {
        SharedPreferences settings = CameraInfoActivity.this.getSharedPreferences("SystemInfo", Activity.MODE_PRIVATE);
        return settings.getBoolean("light", false);
    }

    public File getMediaDir() {
        File dir = new File(Environment.getExternalStorageDirectory(), "pams3_camera");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String getPhotoFileName() {
        String name = new android.text.format.DateFormat().format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        return name;
    }
}
