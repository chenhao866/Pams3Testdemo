package com.expert.pams;

public class Pams3Hardware {

	static {
		System.loadLibrary("pamshardware");
	}

	/**
	 * 初始化操作
	 *
	 */
	public static native int init_native();
	/**
	 *
	 */
	public static native int pams_test();

	/**
	 * io操作函数
	 */
	public static native int Io_AnalogPwrEnable();

	/**
	 * 采集
	 */
	public static native int Io_Dc18VEnable();

	/**
	 *
	 * @param jread_arr
	 *            length = 数组 len*
	 * @param len4
	 *            采样点数
	 * @return
	 */
	public static native int ad7606_read(float[] jread_arr, int len);

	/**
	 *停止
	 */
	public static native int Io_Dc18VDisable();

	/**
	 *退出界面
	 */
	public static native int Io_AnalogPwrDisable();

	/**
	 * io操作函数
	 */
	public static native int Io_TempPwrEnable();

	/**
	 * 采集
	 */
	public static native int Power_On_Laser();

	/**
	 * 设置发射率
	 */
	public static native float Set_Temp_Emis(float emis);

	/**
	 * 读取数据
	 *
	 */
	public static native float Read_Temp();

	/**
	 * 停止
	 */
	public static native int Shut_Off_Laser();

	/**
	 *
	 * 退出界面
	 */
	public static native int Io_TempPwrDisable();

	/**
	 * io操作函数
	 *
	 */
	public static native int Io_RfidPwrEnable();

	/**
	 * 读卡操作函数
	 */
	public static native int wiegand_read_car_num();

	/**
	 * 退出界面
	 */
	public static native int Io_RfidPwrDisable();

	/**
	 * 按键灯开
	 */
	public static native int Io_KeyledEnable();

	/**
	 * 按键灯关
	 */
	public static native int Io_KeyledDisable();

	/**
	 * GPS电源开
	 */
	public static native int Io_GpsPwrEnable();

	/**
	 * GPS电源关
	 */
	public static native int Io_GpsPwrDisable();

	/**
	 * 蜂鸣器开
	 */
	public static native int Io_BuzzerEnable();

	/**
	 * 蜂鸣器关
	 */
	public static native int Io_BuzzerDisable();

	/**
	 * 4G模块复位
	 */
	public static native int Io_G4Reset();

	private static native int deinit_native();

	/**
	 * 温度串口
	 */
	public static native float Read_Temp_Emis();

	/**
	 * 高温校准
	 */
	public static native int Obj_Temp_Cal(int tmp);

	/**
	 * 常温校准
	 */
	public static native int Amb_Temp_Cal();

	/**
	 * 常温校准
	 */
	public static native int Amb_Temp_Cal2(int tmp);

	/**
	 * 闪光灯开
	 */
	public static native int Io_CameraLedEnable();

	/**
	 * 闪光灯关
	 */
	public static native int Io_CameraLedDisable();

	/**
	 * 4G电源开
	 */
	public static native int Io_4GPwrOn();

	/**
	 * 4G电源关
	 */
	public static native int Io_4GPwrOff();
}
