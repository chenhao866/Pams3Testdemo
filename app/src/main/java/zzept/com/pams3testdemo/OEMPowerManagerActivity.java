package zzept.com.pams3testdemo;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.expert.pams.Pams3Hardware;

public class OEMPowerManagerActivity extends Activity implements OnClickListener {

	private Button mBt_back;

	private Button mBt_vib_open, mBt_temp_open, mBt_laser_open, mBt_rfid_open, mBt_key_open, mBt_deep_open, mBtn_GPS_open, mBtn_4G_open;
	private Button mBt_vib_close, mBt_temp_close, mBt_laser_close, mBt_rfid_close, mBt_key_close, mBt_deep_close, mBtn_GPS_close, mBtn_4G_close;
	private TextView mTv_vib_sta, mTv_temp_sta, mTv_laser_sta, mTv_rfid_sta, mTv_key_sta, mTv_deep_sta, mTv_state, mTv_gps_state, mTv_4g_state;

	private String strTotalState = "";
	private Pams3Hardware pams3Hardware;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.powermana);
		initFrame();
		initData();
	}

	private void initData() {
		pams3Hardware = new Pams3Hardware();
		int a = pams3Hardware.init_native();
		if (a != 0) {
			strTotalState = strTotalState + "初始化失败：\n" + a;
			mTv_state.setText(strTotalState);
		} else {
			strTotalState = strTotalState + "初始化成功";
			mTv_state.setText(strTotalState);
		}
	}

	private void initFrame() {
		mBt_back = (Button) this.findViewById(R.id.bt_back);
		mBt_back.setOnClickListener(this);

		mBt_vib_open = (Button) this.findViewById(R.id.bt_vib_open);
		mBt_vib_open.setOnClickListener(this);
		mBt_temp_open = (Button) this.findViewById(R.id.bt_temp_open);
		mBt_temp_open.setOnClickListener(this);
		mBt_laser_open = (Button) this.findViewById(R.id.bt_laser_open);
		mBt_laser_open.setOnClickListener(this);
		mBt_rfid_open = (Button) this.findViewById(R.id.bt_rfid_open);
		mBt_rfid_open.setOnClickListener(this);
		mBt_key_open = (Button) this.findViewById(R.id.bt_key_open);
		mBt_key_open.setOnClickListener(this);
		mBt_deep_open = (Button) this.findViewById(R.id.bt_deep_open);
		mBt_deep_open.setOnClickListener(this);
		mBtn_4G_open = (Button) this.findViewById(R.id.bt_4g_open);
		mBtn_4G_open.setOnClickListener(this);
		mBtn_GPS_open = (Button) this.findViewById(R.id.bt_gps_open);
		mBtn_GPS_open.setOnClickListener(this);

		mBt_vib_close = (Button) this.findViewById(R.id.bt_vib_close);
		mBt_vib_close.setOnClickListener(this);
		mBt_temp_close = (Button) this.findViewById(R.id.bt_temp_close);
		mBt_temp_close.setOnClickListener(this);
		mBt_laser_close = (Button) this.findViewById(R.id.bt_laser_close);
		mBt_laser_close.setOnClickListener(this);
		mBt_rfid_close = (Button) this.findViewById(R.id.bt_rfid_close);
		mBt_rfid_close.setOnClickListener(this);
		mBt_key_close = (Button) this.findViewById(R.id.bt_key_close);
		mBt_key_close.setOnClickListener(this);
		mBt_deep_close = (Button) this.findViewById(R.id.bt_deep_close);
		mBt_deep_close.setOnClickListener(this);
		mBtn_4G_close = (Button) this.findViewById(R.id.bt_4g_close);
		mBtn_4G_close.setOnClickListener(this);
		mBtn_GPS_close = (Button) this.findViewById(R.id.bt_gps_close);
		mBtn_GPS_close.setOnClickListener(this);

		mTv_vib_sta = (TextView) this.findViewById(R.id.tv_vib_sta);
		mTv_temp_sta = (TextView) this.findViewById(R.id.tv_temp_sta);
		mTv_laser_sta = (TextView) this.findViewById(R.id.tv_laser_sta);
		mTv_rfid_sta = (TextView) this.findViewById(R.id.tv_rfid_sta);
		mTv_key_sta = (TextView) this.findViewById(R.id.tv_key_sta);
		mTv_deep_sta = (TextView) this.findViewById(R.id.tv_deep_sta);
		mTv_4g_state = (TextView) this.findViewById(R.id.tv_4g_sta);
		mTv_gps_state = (TextView) this.findViewById(R.id.tv_gps_sta);

		mTv_state = (TextView) this.findViewById(R.id.tv_state);

	}

	// vib laser wifi keyboard
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_back:
				finish();
				break;
			case R.id.bt_vib_open:
				pams3Hardware.Io_AnalogPwrEnable();
				pams3Hardware.Io_Dc18VEnable();
				mTv_vib_sta.setText("开");
				break;
			case R.id.bt_vib_close:
				pams3Hardware.Io_Dc18VDisable();
				pams3Hardware.Io_AnalogPwrDisable();
				mTv_vib_sta.setText("关");
				break;
			case R.id.bt_temp_open:
				pams3Hardware.Io_TempPwrEnable();
				mTv_temp_sta.setText("开");
				break;
			case R.id.bt_temp_close:
				pams3Hardware.Io_TempPwrDisable();
				mTv_temp_sta.setText("关");
				break;
			case R.id.bt_laser_open:
				// pams3Hardware.Io_TempPwrEnable();
				pams3Hardware.Power_On_Laser();
				mTv_laser_sta.setText("开");
				break;
			case R.id.bt_laser_close:
				pams3Hardware.Shut_Off_Laser();
				// pams3Hardware.Io_TempPwrDisable();
				mTv_laser_sta.setText("关");
				break;
			case R.id.bt_rfid_open:
				pams3Hardware.Io_RfidPwrEnable();
				mTv_rfid_sta.setText("开");
				break;
			case R.id.bt_rfid_close:
				pams3Hardware.Io_RfidPwrDisable();
				mTv_rfid_sta.setText("关");
				break;
			case R.id.bt_key_open:
				pams3Hardware.Io_KeyledEnable();
				mTv_key_sta.setText("开");
				break;
			case R.id.bt_key_close:
				pams3Hardware.Io_KeyledDisable();
				mTv_key_sta.setText("关");
				break;
			case R.id.bt_deep_open:
				pams3Hardware.Io_BuzzerEnable();
				mTv_deep_sta.setText("开");
				break;
			case R.id.bt_deep_close:
				pams3Hardware.Io_BuzzerDisable();
				mTv_deep_sta.setText("关");
				break;
			case R.id.bt_4g_open:
				pams3Hardware.Io_4GPwrOn();
				mTv_4g_state.setText("开");
				break;
			case R.id.bt_4g_close:
				pams3Hardware.Io_4GPwrOff();
				mTv_4g_state.setText("关");
				break;
			case R.id.bt_gps_open:
				pams3Hardware.Io_GpsPwrEnable();
				mTv_gps_state.setText("开");
				break;
			case R.id.bt_gps_close:
				pams3Hardware.Io_GpsPwrDisable();
				mTv_gps_state.setText("关");
				break;

		}

	}
}
