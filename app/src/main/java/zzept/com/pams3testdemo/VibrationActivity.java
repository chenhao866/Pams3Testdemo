package zzept.com.pams3testdemo;

import java.text.DecimalFormat;
import java.text.NumberFormat;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.expert.pams.Pams3Hardware;

public class VibrationActivity extends Activity implements OnClickListener, VibInterface {
	private UIHolder mUIHolder = new UIHolder();

	private Pams3Hardware pams3Hardware = new Pams3Hardware();

	private float mAcc, mVel, mDis;

	private VibTimer vibTimer;

	private boolean isCaptureV = false;

	public Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

				case 0:
					mUIHolder.tvAcc.setText(formatTwoFloat(mAcc));
					mUIHolder.tvVel.setText(formatOneFloat(mVel));
					mUIHolder.tvDis.setText(formatZeroFloat(mDis));

					break;
			}
		}
	};

	// 格式化float
	private String formatTwoFloat(float f) {
		DecimalFormat m_nf = (DecimalFormat) NumberFormat.getInstance();
		m_nf.applyPattern("#0.00");
		return m_nf.format(f);
	}

	// 格式化float
	private String formatOneFloat(float f) {
		DecimalFormat m_nf = (DecimalFormat) NumberFormat.getInstance();
		m_nf.applyPattern("#0.0");
		return m_nf.format(f);
	}

	// 格式化float
	private String formatZeroFloat(float f) {
		DecimalFormat m_nf = (DecimalFormat) NumberFormat.getInstance();
		m_nf.applyPattern("#0");
		return m_nf.format(f);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capturevib);
		initViews();
		initData();
	}

	private void initData() {
		pams3Hardware.init_native();
		vibTimer = new VibTimer(this, pams3Hardware);
		pams3Hardware.Io_AnalogPwrEnable();
		pams3Hardware.Io_Dc18VEnable();
	}

	private void initViews() {
		mUIHolder.btnCancle = (Button) findViewById(R.id.bt_back);
		mUIHolder.btnVib = (Button) findViewById(R.id.bt_vib_capture);
		mUIHolder.btnCancle.setOnClickListener(this);
		mUIHolder.btnVib.setOnClickListener(this);
		mUIHolder.tvAcc = (TextView) findViewById(R.id.tv_vid_a);
		mUIHolder.tvVel = (TextView) findViewById(R.id.tv_vid_b);
		mUIHolder.tvDis = (TextView) findViewById(R.id.tv_vid_c);
	}

	@Override
	public void informVib(float acc, float vel, float dis, float[] sampleDataAcc, float[] sampleDataVel, float[] sampleDataDis) {
		mAcc = acc;
		mVel = vel;
		mDis = dis;
		myHandler.sendEmptyMessage(0);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.bt_back:
				finish();
				break;
			case R.id.bt_vib_capture:

				if (!isCaptureV) {
					mUIHolder.btnVib.setText("停止");
					if (vibTimer != null) {
						vibTimer.startRun();
					}
					isCaptureV = true;

				} else {
					mUIHolder.btnVib.setText("采集");
					if (vibTimer != null) {
						vibTimer.stopTimer();
					}
					isCaptureV = false;
				}

				break;
			default:
				break;
		}
	}

	class UIHolder {
		TextView tvAcc;
		TextView tvVel;
		TextView tvDis;
		Button btnVib;
		Button btnCancle;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (vibTimer != null) {
			vibTimer.stopTimer();
		}
		pams3Hardware.Io_Dc18VDisable();
		pams3Hardware.Io_AnalogPwrDisable();

	}
}
