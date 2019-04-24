package zzept.com.pams3testdemo;
/**
 * 扫描rfid卡回调接口
 * @author Yuan
 *
 */
public interface RFIDInterface {

	public void infromResult(int result);

	public void informState(boolean state);
}
