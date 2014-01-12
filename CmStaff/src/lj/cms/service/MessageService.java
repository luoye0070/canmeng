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
				Thread.sleep(20000);//����20�����½�����Ϣ
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ͨ�����ؼ���û��Ƿ��¼
			StaticData sd=StaticData.getInstance();
			UserInfo ui=sd.getUi();
			if(ui==null||ui.getUserId()==0)
			{//û�е�¼��ת����¼����
				Toast.makeText(getApplicationContext(), "��û�е�¼�����ȵ�¼", Toast.LENGTH_LONG).show();
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
				//�����ӷ��������ص��ַ���
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
		//��ʾһ��֪ͨ
		//����һ��NotificationManager������

		String ns = Context.NOTIFICATION_SERVICE;

		NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);

		//����Notification�ĸ�������

		int icon = android.R.drawable.stat_notify_chat; //֪ͨͼ��

		CharSequence tickerText = "������Ϣ"; //״̬����ʾ��֪ͨ�ı���ʾ

		long when = System.currentTimeMillis(); //֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ

		//����������Գ�ʼ��Nofification

		Notification notification = new Notification(icon,tickerText,when);
		notification.defaults |=Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		/*

		* �������

		* notification.defaults |=Notification.DEFAULT_SOUND;

		* ����ʹ�����¼��ַ�ʽ

		* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");

		* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");

		* �����Ҫ�����������ظ�ֱ���û���֪ͨ������Ӧ���������notification��flags�ֶ�����"FLAG_INSISTENT"

		* ���notification��defaults�ֶΰ�����"DEFAULT_SOUND"���ԣ���������Խ�����sound�ֶ��ж��������

		*/

		/*

		* �����

		* notification.defaults |= Notification.DEFAULT_VIBRATE;

		* ���߿��Զ����Լ�����ģʽ��

		* long[] vibrate = {0,100,200,300}; //0�����ʼ�񶯣���100�����ֹͣ���ٹ�200������ٴ���300����

		* notification.vibrate = vibrate;

		* long������Զ������Ҫ���κγ���

		* ���notification��defaults�ֶΰ�����"DEFAULT_VIBRATE",��������Խ�����vibrate�ֶ��ж������

		*/

		/*

		* ���LED������

		* notification.defaults |= Notification.DEFAULT_LIGHTS;

		* ���߿����Լ���LED����ģʽ:

		* notification.ledARGB = 0xff00ff00;

		* notification.ledOnMS = 300; //����ʱ��

		* notification.ledOffMS = 1000; //���ʱ��

		* notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		*/

		/*

		* �������������

		* notification.flags |= FLAG_AUTO_CANCEL; //��֪ͨ���ϵ����֪ͨ���Զ������֪ͨ

		* notification.flags |= FLAG_INSISTENT; //�ظ�����������ֱ���û���Ӧ��֪ͨ

		* notification.flags |= FLAG_ONGOING_EVENT; //����֪ͨ�ŵ�֪ͨ����"Ongoing"��"��������"����

		* notification.flags |= FLAG_NO_CLEAR; //�����ڵ����֪ͨ���е�"���֪ͨ"�󣬴�֪ͨ�������

		* //������FLAG_ONGOING_EVENTһ��ʹ��

		* notification.number = 1; //number�ֶα�ʾ��֪ͨ����ĵ�ǰ�¼�����������������״̬��ͼ��Ķ���

		* //���Ҫʹ�ô��ֶΣ������1��ʼ

		* notification.iconLevel = ; //

		*/

		//����֪ͨ���¼���Ϣ

		Context context = getApplicationContext(); //������

		CharSequence contentTitle = "������Ϣ"; //֪ͨ������

		CharSequence contentText = currentMsg.content; //֪ͨ������
		if(currentMsg.orderId!=0){

			Intent notificationIntent = new Intent(this,OrderDetailActivity.class); //�����֪ͨ��Ҫ��ת��Activity
			notificationIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, currentMsg.orderId);
			
			PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
	
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		}

		//��Notification���ݸ�NotificationManager

		mNotificationManager.notify(nId,notification);
		nId++;
	}
}
