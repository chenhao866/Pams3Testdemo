package zzept.com.pams3testdemo;

import java.util.Timer;
import java.util.TimerTask;

import com.expert.pams.Pams3Hardware;

public class TemTimer {

	private float temp = 0;

	private TemInterface mItemInterface;

	Pams3Hardware mPams3Hardware;

	private Timer timer;

	public TemTimer(TemInterface temInterface, Pams3Hardware pams3Hardware) {
		mItemInterface = temInterface;
		mPams3Hardware = pams3Hardware;
	}

	public void startRun() {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				temp = mPams3Hardware.Read_Temp();
				if (temp <= 420 && temp >= -20 && temp != 0) {
					mItemInterface.infromTem(temp);
				}
			}
		};
		timer.schedule(task, 0, 1000);
	}

	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
}
