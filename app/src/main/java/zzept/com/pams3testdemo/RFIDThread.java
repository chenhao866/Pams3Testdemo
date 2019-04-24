package zzept.com.pams3testdemo;

import android.util.Log;

import com.expert.pams.Pams3Hardware;

public class RFIDThread extends Thread {

    private RFIDInterface m_rfidInterface;

    private int nCounter = 0;

    public boolean isRunning = false;

    public RFIDThread(RFIDInterface rfidInterface) {
        m_rfidInterface = rfidInterface;
        open();
    }

    public void startRun() {
        if (isRunning == false) {
            // 判断当前蜂鸣器状态
            this.start();
            isRunning = true;
        }
    }

    private int open() {
        return Pams3Hardware.Io_RfidPwrEnable();
    }

    public int close() {
        return Pams3Hardware.Io_RfidPwrDisable();
    }

    @Override
    public void run() {
        super.run();
        try {
            while (isRunning) {
                int d = Pams3Hardware.wiegand_read_car_num();
                if (d != 0) {
                    m_rfidInterface.infromResult(d);
                    Pams3Hardware.Io_BuzzerEnable();
                    sleep(100);
                    Pams3Hardware.Io_BuzzerDisable();
                    Log.d("蜂鸣器", "  响了" + d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
