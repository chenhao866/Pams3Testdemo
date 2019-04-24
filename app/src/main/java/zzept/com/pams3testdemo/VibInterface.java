package zzept.com.pams3testdemo;

public interface VibInterface {

	/**
	 *
	 * @param acc
	 *            加速度
	 * @param vel
	 *            速度
	 * @param dis
	 *            位移
	 */
	public void informVib(float acc, float vel, float dis, float[] sampleDataAcc, float[] sampleDataVel, float[] sampleDataDis);

}
