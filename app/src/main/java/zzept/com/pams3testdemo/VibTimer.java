package zzept.com.pams3testdemo;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;


import com.ept.pams3.util.SP;

import com.expert.pams.Pams3Hardware;

public class VibTimer {

	public static final int VIB_SAMP_FS = 4883;//采样频率
	public static final int VIB_SAMP_LENGTH = 2048;// 采样长度
	public static final int VIB_CHANNEL = 4; // 通道

	public float VOLT2MINIVOLT = 1000.0f;
	public float AD_MAX_VOLT = 5.0f;
	public float AD_MAX_DIGITAL = 32768;

	public float[] pSampleDataAcc = new float[VIB_SAMP_LENGTH];
	public float[] pSampleDataVel = new float[VIB_SAMP_LENGTH];
	public float[] pSampleDataDis = new float[VIB_SAMP_LENGTH];

	public float Sensity = 10.21f;
	public float CompAcc = 0.10171914f;
	public float CompVel = 1.0206355f;
	public float CompDis = 7.5072594f;
//    public float CompAcc = 0.10226083f;
//    public float CompVel = 1.0208225f;
//    public float CompDis = 7.4663615f;

	private Pams3Hardware mPams3Hardware = new Pams3Hardware();

	public boolean bVibComp = false;

	private float[] VibSampleData = new float[VIB_SAMP_LENGTH * VIB_CHANNEL];

	private VibInterface mVibInterface;

	private Timer timer;

	public VibTimer(VibInterface vibInterface, Pams3Hardware pams3Hardware) {
		mVibInterface = vibInterface;
		mPams3Hardware = pams3Hardware;
	}

	public void startRun() {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				int status = mPams3Hardware.ad7606_read(VibSampleData, VIB_SAMP_LENGTH);
				if (bVibComp) {
					for (int i = 0; i < VIB_SAMP_LENGTH; i++) {
						pSampleDataAcc[i] = (float) ((VibSampleData[0 * VIB_SAMP_LENGTH + i]));
						pSampleDataVel[i] = (float) ((VibSampleData[1 * VIB_SAMP_LENGTH + i]));
						pSampleDataDis[i] = (float) ((VibSampleData[2 * VIB_SAMP_LENGTH + i]));
					}
				} else {
					for (int i = 0; i < VIB_SAMP_LENGTH; i++) {
						pSampleDataAcc[i] = (float) ((VibSampleData[0 * VIB_SAMP_LENGTH + i]) / Sensity * CompAcc);
						pSampleDataVel[i] = (float) ((VibSampleData[1 * VIB_SAMP_LENGTH + i]) / Sensity * CompVel);
						pSampleDataDis[i] = (float) ((VibSampleData[2 * VIB_SAMP_LENGTH + i]) / Sensity * CompDis);
					}
				}
				Log.e("Sensity", CompAcc + "");
				Log.e("Sensity", CompVel + "");
				Log.e("Sensity", CompDis + "");
				RemoveConst(pSampleDataAcc, VIB_SAMP_LENGTH);
				RemoveConst(pSampleDataVel, VIB_SAMP_LENGTH);
				RemoveConst(pSampleDataDis, VIB_SAMP_LENGTH);
				mVibInterface.informVib(getAccValue(), getVelValue(), getDisValue(), pSampleDataAcc, pSampleDataVel, pSampleDataDis);
			}
		};
		timer.schedule(task, 0, 5000);
	}

	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public float ADtoVolt(float point)
	{
		float retvalue;
		retvalue = (float) (point * AD_MAX_VOLT / AD_MAX_DIGITAL);
		retvalue = (float) (retvalue * VOLT2MINIVOLT);
		return retvalue;
	}

	public void RemoveConst(float[] Signal, int N) {
		float E = 0.0f;
		for (int i = 0; i < N; i++)
			E += Signal[i] / (float) N;

		for (int i = 0; i < N; i++)
			Signal[i] -= E;
	}

	// 加速度
	public float getAccValue() {
		float ret = (float) (1.414213562 * SP.Rms(pSampleDataAcc, VIB_SAMP_LENGTH));
		return ret;
	}

	// 速度
	public float getVelValue() {
		float ret = (float) (SP.Rms(pSampleDataVel, VIB_SAMP_LENGTH));
		return ret;
	}

	// 位移
	public float getDisValue() {
		float ret = (float) (2.828427125 * SP.Rms(pSampleDataDis, VIB_SAMP_LENGTH));
		return ret;
	}
}
