package lj.cms.service;

import lj.cms.activity.LoginActivity;
import lj.cms.activity.MessageActivity;
import lj.cms.activity.MessageListActivity;
import lj.cms.activity.OrderDetailActivity;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.NotifyId;
import lj.cms.common.StaticData;
import lj.cms.constant.AppConstant;
import lj.cms.constant.MessageType;
import lj.cms.database.DatabaseHelper;
import lj.cms.internet.HttpClientHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamsCollect;
import lj.cms.model.MessageInfo;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.DataOfMessageAnalyzeHelper;
import lj.mina.client.AnalyzeHelper;
import lj.mina.client.MinaClient;
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
	MinaClient minaClient;
	Context context; //上下文
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//context = getApplicationContext(); //上下文
		context=this;
		if(minaClient!=null){
			minaClient.disconnect();
			minaClient=null;
		}
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
		if(minaClient!=null){
			minaClient.disconnect();
			minaClient=null;
		}
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
				Toast.makeText(getApplicationContext(), "餐萌登录已经失效，请重新登录", Toast.LENGTH_LONG).show();
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
		if(isActive){
			//通过本地检查用户是否登录
			UserInfo ui=new UserInfo();
			ui.readFromSharedPreferences(getApplicationContext());
			if(ui.getUserId()==0)
			{//没有登录，转到登录界面
				//handler.sendEmptyMessage(0);
				String content="餐萌登录已经失效，请重新登录"; //通知栏内容
				Intent notificationIntent = new Intent(context,LoginActivity.class); //点击该通知后要跳转的Activity
				notifyMsg(content,notificationIntent,0,false);
				return;
			}
			String serverIp=AppConstant.UrlStrs.IP_MESSAGE_SERVER;
			int serverPort=AppConstant.UrlStrs.PORT_MESSAGE_SERVER;
			int userType=0;//这里和服务端协定，0表示工作人员，1表示顾客
			minaClient=new MinaClient(serverIp, serverPort, 30000, "UTF-8", "\n", "\n", "sn123",true,ui.userId, userType, new AnalyzeHelper() {
				
				@Override
				public void analyze(String dataStr) {
					// TODO Auto-generated method stub
					//System.out.println(dataStr);
					DataOfMessageAnalyzeHelper analyzeHelper=new DataOfMessageAnalyzeHelper();
					analyzeHelper.analyze(dataStr);
					if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){
						currentMsg=analyzeHelper.messageInfo;
						System.out.println("currentMsg-->"+currentMsg);
						System.out.println("analyzeHelper.messageInfo-->"+analyzeHelper.messageInfo);
						if(currentMsg.type!=MessageType.ORDER_HANDLE_TYPE.code){//非订单处理类消息
							String content=currentMsg.content; //通知栏内容
							Intent notificationIntent = new Intent(context,MessageActivity.class); //点击该通知后要跳转的Activity
							notificationIntent.putExtra(AppConstant.IntentExtraName.MESSAGE_CONTENT, content);
							notifyMsg(content,notificationIntent,1,true);
							Intent intent=new Intent(AppConstant.BroadcastActions.UPDATE_MSG_VIEW);
							intent.putExtra(AppConstant.IntentExtraName.MESSAGE_CONTENT, content);
							sendBroadcast(intent);
						}else{
							//将消息写入数据,并发出广播
							DatabaseHelper dh=new DatabaseHelper(getApplicationContext());
							if(dh.insert(currentMsg)){
								Intent intent=new Intent(AppConstant.BroadcastActions.UPDATE_MSG_VIEW);
								sendBroadcast(intent);
								//在通知栏通知
								notifyMsg();
							}
						}
					}
				}
			});
			minaClient.connect();
			System.out.println("recMsg done");
//			try {
//				Thread.sleep(20000);//休眠20秒重新接受消息
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//通过本地检查用户是否登录
//			StaticData sd=StaticData.getInstance();
//			UserInfo ui=sd.getUi();
//			if(ui==null||ui.getUserId()==0)
//			{//没有登录，转到登录界面
//				Toast.makeText(getApplicationContext(), "您没有登录，请先登录", Toast.LENGTH_LONG).show();
//				continue;
//			}
//			
//			String url_s=AppConstant.UrlStrs.URL_GET_MESSAGE;
//			ParamCollect pc=new ParamCollect();
//			if(currentMsg!=null){
//				pc.addOrSetParam("msgId", currentMsg.id+"");
//			}
//			
//			HttpClientHelper httpConnHelper=HttpClientHelper.getInstance();
//			String responseStr="";
//			if(!isActive)
//				return;
//			responseStr=httpConnHelper.getResponseStr(url_s, pc.paramList);//getString(R.string.urlOrderQuery)
//			System.out.println(responseStr);
//			if(httpConnHelper.getResultCode()==AppConstant.VisitServerResultCode.RESULT_CODE_OK)
//			{
//				//解析从服务器返回的字符串
//				if(!isActive)
//					return;
//				DataOfMessageAnalyzeHelper analyzeHelper=new DataOfMessageAnalyzeHelper();
//				analyzeHelper.analyze(responseStr);
//				if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){
//					currentMsg=analyzeHelper.messageInfo;
//					notifyMsg();
//				}
//			}
			
			
		}
	}
	private void notifyMsg(){
		String content=currentMsg.content; //通知栏内容
		//Intent notificationIntent = new Intent(context,OrderDetailActivity.class); //点击该通知后要跳转的Activity
		Intent notificationIntent = new Intent(context,MessageListActivity.class); //点击该通知后要跳转的Activity
		notificationIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, currentMsg.orderId);
		//notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		notifyMsg(content,notificationIntent,NotifyId.getNId(currentMsg.orderId,currentMsg.type),false);
		
	}
	private void notifyMsg(String content,Intent intent,int nId,boolean canclear){
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
		if(!canclear)
			notification.flags |= Notification.FLAG_NO_CLEAR; //表明在点击了通知栏中的"清除通知"后，此通知不清除
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
		System.out.println("currentMsg-->"+currentMsg);

		//设置通知的事件消息

		//Context context = getApplicationContext(); //上下文

		CharSequence contentTitle = "餐萌消息"; //通知栏标题

		//CharSequence contentText = currentMsg.content; //通知栏内容
		CharSequence contentText = content; //通知栏内容
//		if(currentMsg.orderId!=0){
//
//			Intent notificationIntent = new Intent(this,OrderDetailActivity.class); //点击该通知后要跳转的Activity
//			notificationIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, currentMsg.orderId);
//			
//			PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//	
//			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
//		}
			PendingIntent contentIntent = PendingIntent.getActivity(context,nId,intent,PendingIntent.FLAG_ONE_SHOT);
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			
		//把Notification传递给NotificationManager

		mNotificationManager.notify(nId,notification);
	}
}
