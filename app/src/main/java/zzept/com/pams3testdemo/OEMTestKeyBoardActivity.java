package zzept.com.pams3testdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OEMTestKeyBoardActivity extends Activity implements OnClickListener {

    private TextView mTv_0, mTv_1, mTv_2, mTv_3, mTv_4, mTv_5, mTv_6, mTv_7, mTv_8, mTv_9, mTv_sharp, mTv_x, mTv_u, mTv_d, mTv_r, mTv_l;
    private Button mBt_back;
    private TextView tv_key_tab, tv_key_ok, tv_key_space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard);

        initFrame();
    }


    private void initFrame() {
        mBt_back = (Button) this.findViewById(R.id.bt_back);
        mBt_back.setOnClickListener(this);

        mTv_0 = (TextView) this.findViewById(R.id.tv_key_0);
        mTv_1 = (TextView) this.findViewById(R.id.tv_key_1);
        mTv_2 = (TextView) this.findViewById(R.id.tv_key_2);
        mTv_3 = (TextView) this.findViewById(R.id.tv_key_3);
        mTv_4 = (TextView) this.findViewById(R.id.tv_key_4);
        mTv_5 = (TextView) this.findViewById(R.id.tv_key_5);
        mTv_6 = (TextView) this.findViewById(R.id.tv_key_6);
        mTv_7 = (TextView) this.findViewById(R.id.tv_key_7);
        mTv_8 = (TextView) this.findViewById(R.id.tv_key_8);
        mTv_9 = (TextView) this.findViewById(R.id.tv_key_9);
        mTv_sharp = (TextView) this.findViewById(R.id.tv_key_sharp);
        mTv_x = (TextView) this.findViewById(R.id.tv_key_x);

        mTv_r = (TextView) this.findViewById(R.id.tv_key_r);
        mTv_l = (TextView) this.findViewById(R.id.tv_key_l);
        mTv_u = (TextView) this.findViewById(R.id.tv_key_p);
        mTv_d = (TextView) this.findViewById(R.id.tv_key_d);

        tv_key_tab = (TextView) this.findViewById(R.id.tv_key_tab);
        tv_key_ok = (TextView) this.findViewById(R.id.tv_key_OK);
        tv_key_space = (TextView) this.findViewById(R.id.tv_key_space);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(this, "keyCode:" + keyCode, Toast.LENGTH_SHORT).show();
        // 所有顏色還原
        setAllWhite();
        switch (keyCode) {
            case KeyEvent.KEYCODE_NUMPAD_0:
            case KeyEvent.KEYCODE_0:
                mTv_0.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_1:
            case KeyEvent.KEYCODE_1:
                mTv_1.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_2:
            case KeyEvent.KEYCODE_2:
                mTv_2.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_3:
            case KeyEvent.KEYCODE_3:
                mTv_3.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_4:
            case KeyEvent.KEYCODE_4:
                mTv_4.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_5:
            case KeyEvent.KEYCODE_5:
                mTv_5.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_6:
            case KeyEvent.KEYCODE_6:
                mTv_6.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_7:
            case KeyEvent.KEYCODE_7:
                mTv_7.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_8:
            case KeyEvent.KEYCODE_8:
                mTv_8.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_NUMPAD_9:
            case KeyEvent.KEYCODE_9:
                mTv_9.setTextColor(Color.GREEN);
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                mTv_u.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mTv_d.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mTv_l.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mTv_r.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_MINUS:// #
                mTv_sharp.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_PERIOD:
                mTv_x.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_TAB:
                tv_key_tab.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_SPACE:
                tv_key_space.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_ENTER:
                tv_key_ok.setTextColor(Color.GREEN);
                break;
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
            default:
                break;
        }
        return true;

    }

    private void setAllWhite() {
        mTv_0.setTextColor(Color.WHITE);
        mTv_1.setTextColor(Color.WHITE);
        mTv_2.setTextColor(Color.WHITE);
        mTv_3.setTextColor(Color.WHITE);
        mTv_4.setTextColor(Color.WHITE);
        mTv_5.setTextColor(Color.WHITE);
        mTv_6.setTextColor(Color.WHITE);
        mTv_7.setTextColor(Color.WHITE);
        mTv_8.setTextColor(Color.WHITE);
        mTv_9.setTextColor(Color.WHITE);

        mTv_sharp.setTextColor(Color.WHITE);
        mTv_x.setTextColor(Color.WHITE);

        mTv_r.setTextColor(Color.WHITE);
        mTv_l.setTextColor(Color.WHITE);
        mTv_u.setTextColor(Color.WHITE);
        mTv_d.setTextColor(Color.WHITE);

        tv_key_space.setTextColor(Color.WHITE);
        tv_key_ok.setTextColor(Color.WHITE);
        tv_key_tab.setTextColor(Color.WHITE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                finish();
                break;
            default:
                break;

        }

    }

}
