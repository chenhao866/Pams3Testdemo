package zzept.com.pams3testdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author SR
 * 2019/4/11 13:30
 */
public class CardActivity extends Activity implements RFIDInterface {
    TextView tv_card_num;
    private RFIDThread thread;
    private Button btnReturn;
    private int rfidNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        tv_card_num = findViewById(R.id.tv_card_num);
        thread = new RFIDThread(this);
        thread.startRun();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                tv_card_num.setText(rfidNum + "");
            }
        }

        ;
    };



    private void stopThread() {
        if (thread != null) {
            thread.isRunning = false;
            thread.close();
            thread.interrupt();
            thread = null;
        }
    }

    @Override
    public void infromResult(int result) {
        rfidNum = result;
        handler.sendEmptyMessage(0);
    }

    @Override
    public void informState(boolean state) {
        if (state == false) {
            stopThread();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread();
    }

}
