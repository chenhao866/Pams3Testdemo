package zzept.com.pams3testdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Activity_Dialog extends Activity implements OnClickListener {

	/**
	 * 确定返回值
	 */
	public static final int RESULT_SURE = 0;
	/**
	 * 取消返回值
	 */
	public static final int RESULT_CANCEL = 1;
	/**
	 * 直线式确定按钮
	 */
	private int BUTTON_ONLY_SURE = 0;

	private UIHolder uiHolder = new UIHolder();

	private String strTitle = "";

	private String strContent = "";

	private int sort = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.layout_dialog);
		initUI();
		initData();
	}

	private void initData() {
		try {
			Intent intent = getIntent();
			strTitle = intent.getStringExtra("strTitle");
			strContent = intent.getStringExtra("strContent");
			sort = intent.getIntExtra("sort", -1);
			uiHolder.tvTitle.setText(strTitle);
			uiHolder.tvContent.setText(strContent);
			if (sort == BUTTON_ONLY_SURE) {
				uiHolder.btnCancle.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initUI() {
		uiHolder.tvContent = (TextView) findViewById(R.id.dialog_content);
		uiHolder.tvTitle = (TextView) findViewById(R.id.dialog_title);
		uiHolder.btnSure = (Button) findViewById(R.id.btn_sure);
		uiHolder.btnCancle = (Button) findViewById(R.id.btn_back);
		uiHolder.btnSure.setOnClickListener(this);
		uiHolder.btnCancle.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_sure:
			setResult(RESULT_SURE);
			finish();
		case R.id.btn_back:
			setResult(RESULT_CANCEL);
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP) {
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_ENTER:
				setResult(RESULT_SURE);
				finish();
				return true;
			case KeyEvent.KEYCODE_BACK:
				setResult(RESULT_CANCEL);
				finish();
				return true;
			}
		}

		return super.dispatchKeyEvent(event);
	}

	private class UIHolder {
		TextView tvTitle;
		TextView tvContent;
		Button btnSure;
		Button btnCancle;
	}

}
