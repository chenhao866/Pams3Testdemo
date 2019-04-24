package zzept.com.pams3testdemo;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.pams.Pams3Hardware;

public class TemperatureActivity extends Activity implements OnClickListener, TemInterface {

	private UIHolder mUIHolder = new UIHolder();

	private Pams3Hardware mPams3Hardware = new Pams3Hardware();

	private TemTimer temThread;

	private boolean isCapture = false;

	private float mTemp;

	private Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					mUIHolder.tvTemp.setText(mTemp + "");
					break;
				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capturetemp);
		initViews();
		initData();
	}

	private void initViews() {
		mUIHolder.btnCancle = (Button) findViewById(R.id.bt_back);
		mUIHolder.btnTemp = (Button) findViewById(R.id.bt_temp_capture);
		mUIHolder.mEt_sendbitrate = (EditText) findViewById(R.id.et_temp_sendbitrate);
		mUIHolder.tvTemp = (TextView) findViewById(R.id.tv_temp_now);
		mUIHolder.btnCancle.setOnClickListener(this);
		mUIHolder.btnTemp.setOnClickListener(this);
	}

	private void initData() {
		mPams3Hardware.init_native();
		mPams3Hardware.Io_TempPwrEnable();
		temThread = new TemTimer(this, mPams3Hardware);
	}

	@Override
	public void infromTem(float temp) {
		mTemp = temp;
		hander.sendEmptyMessage(0);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.bt_back:
				finish();
				break;
			case R.id.bt_temp_capture:
				if (isCapture) {
					mPams3Hardware.Shut_Off_Laser();
					mUIHolder.btnTemp.setText("采集");
					isCapture = false;
					if (temThread != null) {
						temThread.stopTimer();
					}
				} else {

					try {
						String str_fsl = mUIHolder.mEt_sendbitrate.getText().toString();
						float ffsl = Float.parseFloat(str_fsl);
						if (ffsl == 0) {
							isCapture = false;
							Toast.makeText(TemperatureActivity.this, "不能设置为0！", Toast.LENGTH_SHORT).show();
						} else {
							mUIHolder.btnTemp.setText("停止");
							isCapture = true;
							mPams3Hardware.Power_On_Laser();
							mPams3Hardware.Set_Temp_Emis(ffsl);
							temThread.startRun();
						}

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				break;
			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (temThread != null) {
			temThread.stopTimer();
		}
		if (temThread != null) {
			temThread = null;
		}
		mPams3Hardware.Shut_Off_Laser();
		mPams3Hardware.Io_TempPwrDisable();
	}

	class UIHolder {
		TextView tvTemp;
		Button btnTemp;
		Button btnCancle;
		EditText mEt_sendbitrate;
	}

}
