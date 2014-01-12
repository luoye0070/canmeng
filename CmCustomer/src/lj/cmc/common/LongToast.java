package lj.cmc.common;

import java.util.Timer;
import java.util.TimerTask;

import android.widget.Toast;

public class LongToast {
	private Toast showToast=null;
	private int showTime;
	private int showCount;
	public LongToast(){this.showCount=0;}
	/*********************************
	 * ������κ���
	 * @param showToast,��ʾ������Ϣ��Toast����
	 * @param showTime,������Ϣ��ʾ��ʱ�䣬����Ϊ��λ
	 ********************************/
	public LongToast(Toast showToast, int showTime) {
		super();
		this.showToast = showToast;
		this.showTime = showTime;
		this.showCount=0;
	}

	public Toast getShowToast() {
		return showToast;
	}
	public void setShowToast(Toast showToast) {
		this.showToast = showToast;
	}
	public int getShowTime() {
		return showTime;
	}
	public void setShowTime(int showTime) {
		this.showTime = showTime;
	}
	
	public void show()
	{
		if(this.showCount>=this.showTime)
			return;
		this.showToast.show();
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//LongToast.this.showToast.show();
				LongToast.this.show();
				LongToast.this.showCount++;
			}
		},1000);
	}
}
