package lj.cms.service;

import lj.cms.activity.LoginActivity;
import lj.cms.activity.OrderDetailActivity;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.StaticData;
import lj.cms.constant.AppConstant;
import lj.cms.internet.HttpClientHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamsCollect;
import lj.cms.model.MessageInfo;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.DataOfMessageAnalyzeHelper;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class MessageService extends Service {
	
	private boolean isActive=false;
	private MessageInfo currentMsg=null;
	Handler handler=null;
	Thread thread=null;
	private int nId=0;
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		startThread();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(thread!=null&&!thread.isInterrupted())
		{
			thread.interrupt();
		}
		super.onDestroy();
	}
	private void startThread(){
		isActive=true;
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
			}
		};
		thread=new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				recMsg();
			}
		});
		thread.start();
	}
	private void stopThread(){
		
	}
	
	
	private void recMsg(){
		while(isActive){
			try {
				Thread.sleep(20000);//休眠20秒重新接受消息
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//通过本地检查用户是否登录
			StaticData sd=StaticData.getInstance();
			UserInfo ui=sd.getUi();
			if(ui==null||ui.getUserId()==0)
			{//没有登录，转到登录界面
				Toast.makeText(getApplicationContext(), "您没有登录，请先登录", Toast.LENGTH_LONG).show();
				continue;
			}
			
			String url_s=AppConstant.UrlStrs.URL_GET_MESSAGE;
			ParamCollect pc=new ParamCollect();
			if(currentMsg!=null){
				pc.addOrSetParam("msgId", currentMsg.id+"");
			}
			
			HttpClientHelper httpConnHelper=HttpClientHelper.getInstance();
			String responseStr="";
			if(!isActive)
				return;
			responseStr=httpConnHelper.getResponseStr(url_s, pc.paramList);//getString(R.string.urlOrderQuery)
			System.out.println(responseStr);
			if(httpConnHelper.getResultCode()==AppConstant.VisitServerResultCode.RESULT_CODE_OK)
			{
				//解析从服务器返回的字符串
				if(!isActive)
					return;
				DataOfMessageAnalyzeHelper analyzeHelper=new DataOfMessageAnalyzeHelper();
				analyzeHelper.analyze(responseStr);
				if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){
					currentMsg=analyzeHelper.messageInfo;
					notifyMsg();
				}
			}
			
			
		}
	}
	private void notifyMsg(){
		//显示一个通知
		//创建一个NotificationManager的引用

		String ns = Context.NOTIFICATION_SERVICE;

		NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);

		//定义Notification的各种属性

		int icon = android.R.drawable.stat_notify_chat; //通知图标

		CharSequence tickerText = "餐萌消息"; //状态栏显示的通知文本提示

		long when = System.currentTimeMillis(); //通知产生的时间，会在通知信息里显示

		//用上面的属性初始化Nofification

		Notification notification = new Notification(icon,tickerText,when);
		notification.defaults |=Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		/*

		* 添加声音

		* notification.defaults |=Notification.DEFAULT_SOUND;

		* 或者使用以下几种方式

		* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");

		* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");

		* 如果想要让声音持续重复直到用户对通知做出反应，则可以在notification的flags字段增加"FLAG_INSISTENT"

		* 如果notification的defaults字段包括了"DEFAULT_SOUND"属性，则这个属性将覆盖sound字段中定义的声音

		*/

		/*

		* 添加振动

		* notification.defaults |= Notification.DEFAULT_VIBRATE;

		* 或者可以定义自己的振动模式：

		* long[] vibrate = {0,100,200,300}; //0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒

		* notification.vibrate = vibrate;

		* long数组可以定义成想要的任何长度

		* 如果notification的defaults字段包括了"DEFAULT_VIBRATE",则这个属性将覆盖vibrate字段中定义的振动

		*/

		/*

		* 添加LED灯提醒

		* notification.defaults |= Notification.DEFAULT_LIGHTS;

		* 或者可以自己的LED提醒模式:

		* notification.ledARGB = 0xff00ff00;

		* notification.ledOnMS = 300; //亮的时间

		* notification.ledOffMS = 1000; //灭的时间

		* notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		*/

		/*

		* 更多的特征属性

		* notification.flags |= FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知

		* notification.flags |= FLAG_INSISTENT; //重复发出声音，直到用户响应此通知

		* notification.flags |= FLAG_ONGOING_EVENT; //将此通知放到通知栏的"Ongoing"即"正在运行"组中

		* notification.flags |= FLAG_NO_CLEAR; //表明在点击了通知栏中的"清除通知"后，此通知不清除，

		* //经常与FLAG_ONGOING_EVENT一起使用

		* notification.number = 1; //number字段表示此通知代表的当前事件数量，它将覆盖在状态栏图标的顶部

		* //如果要使用此字段，必须从1开始

		* notification.iconLevel = ; //

		*/

		//设置通知的事件消息

		Context context = getApplicationContext(); //上下文

		CharSequence contentTitle = "餐萌消息"; //通知栏标题

		CharSequence contentText = currentMsg.content; //通知栏内容
		if(currentMsg.orderId!=0){

			Intent notificationIntent = new Intent(this,OrderDetailActivity.class); //点击该通知后要跳转的Activity
			notificationIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, currentMsg.orderId);
			
			PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
	
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		}

		//把Notification传递给NotificationManager

		mNotificationManager.notify(nId,notification);
		nId++;
	}
}
