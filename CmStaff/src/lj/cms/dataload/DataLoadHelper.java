package lj.cms.dataload;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.activity.LoginActivity;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.StaticData;
import lj.cms.model.UserInfo;

import lj.cms.R;

import lj.cms.constant.AppConstant;
import lj.cms.internet.HttpClientHelper;
import lj.cms.internet.HttpConnectionHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamCollectAbstract;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.LoginLoadDataAnalyzeHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class DataLoadHelper {
	
	private String url_s;
	private Activity a;
	private ParamCollectAbstract params;
	private BeforeAction beforeAction;
	private AnalyzeHelper analyzeHelper;
	private AfterAction afterAction;
	private Handler handler;
	
	public String getUrl_s() {
		return url_s;
	}
	public void setUrl_s(String url_s) {
		this.url_s = url_s;
	}
	public Activity getA() {
		return a;
	}
	public void setA(Activity a) {
		this.a = a;
	}
	public ParamCollectAbstract getParams() {
		return params;
	}
	public void setParams(ParamCollectAbstract params) {
		this.params = params;
	}
	public BeforeAction getBeforeAction() {
		return beforeAction;
	}
	public void setBeforeAction(BeforeAction beforeAction) {
		this.beforeAction = beforeAction;
	}
	public AnalyzeHelper getAnalyzeHelper() {
		return analyzeHelper;
	}
	public void setAnalyzeHelper(AnalyzeHelper analyzeHelper) {
		this.analyzeHelper = analyzeHelper;
	}
	public AfterAction getAfterAction() {
		return afterAction;
	}
	public void setAfterAction(AfterAction afterAction) {
		this.afterAction = afterAction;
	}
	
	/*********************************
	 *���캯��
	 *@param url_s,�������ݽ�����url��ַ
	 *@param a,Activity����
	 *@param params,�����б�
	 *@param beforeAction,���ݽ���ǰ���еĶ�������
	 *@param analyzeHelper,�Դӷ��������ص����ݽ������ݽ����İ�������
	 *@param afterAction,���ݽ�������еĶ������� 
	 **********************************/
	public DataLoadHelper(String url_s, Activity a, ParamCollectAbstract params,
			BeforeAction beforeAction, AnalyzeHelper analyzeHelper,
			AfterAction afterAction) {
		super();
		this.url_s = url_s;
		this.a = a;
		this.params = params;
		this.beforeAction = beforeAction;
		this.analyzeHelper = analyzeHelper;
		this.afterAction = afterAction;
	}
	/*********************************
	 *���캯��
	 *@param url_s,�������ݽ�����url��ַ
	 *@param a,Activity����
	 *@param params,�����б�
	 *@param beforeAction,���ݽ���ǰ���еĶ�������
	 *@param analyzeHelper,�Դӷ��������ص����ݽ������ݽ����İ�������
	 *@param afterAction,���ݽ�������еĶ������� 
	 **********************************/
	public DataLoadHelper(String url_s, Activity a, ParamCollectAbstract params,
			AnalyzeHelper analyzeHelper,
			AfterAction afterAction) {
		super();
		this.url_s = url_s;
		this.a = a;
		this.params = params;
		this.beforeAction = null;
		this.analyzeHelper = analyzeHelper;
		this.afterAction = afterAction;
	}
	/*********************************
	 *���캯��
	 *@param url_s,�������ݽ�����url��ַ
	 *@param params,�����б�
	 *@param beforeAction,���ݽ���ǰ���еĶ�������
	 *@param afterAction,���ݽ�������еĶ������� 
	 **********************************/
	public DataLoadHelper(String url_s,ParamCollectAbstract params,
			BeforeAction beforeAction,
			AfterAction afterAction) {
		super();
		this.url_s = url_s;
		this.a = afterAction.a;
		this.params = params;
		this.beforeAction = beforeAction;
		this.analyzeHelper = afterAction.analyzeHelper;
		this.afterAction = afterAction;
	}
	/*********************************
	 *���캯��
	 *@param url_s,�������ݽ�����url��ַ
	 *@param params,�����б�
	 *@param afterAction,���ݽ�������еĶ������� 
	 **********************************/
	public DataLoadHelper(String url_s,ParamCollectAbstract params,
			AfterAction afterAction) {
		super();
		this.url_s = url_s;
		this.a = afterAction.a;
		this.params = params;
		this.beforeAction = null;
		this.analyzeHelper = afterAction.analyzeHelper;
		this.afterAction = afterAction;
	}
	/*********************************
	 *���캯��
	 *@param url_s,�������ݽ�����url��ַ
	 *@param a,Activity����
	 *@param params,�����б�
	 *@param beforeAction,���ݽ���ǰ���еĶ������� 
	 **********************************/
	public DataLoadHelper(String url_s, Activity a, ParamCollectAbstract params,
			BeforeAction beforeAction) {
		super();
		this.url_s = url_s;
		this.a = a;
		this.params = params;
		this.beforeAction = beforeAction;
		this.afterAction = new SubmitDataAfterAction(a);
		this.analyzeHelper = this.afterAction.analyzeHelper;		
	}
	/*********************************
	 *���캯��
	 *@param url_s,�������ݽ�����url��ַ
	 *@param a,Activity����
	 *@param params,�����б� 
	 **********************************/
	public DataLoadHelper(String url_s, Activity a, ParamCollectAbstract params) {
		super();
		this.url_s = url_s;
		this.a = a;
		this.params = params;
		this.beforeAction = null;
		this.afterAction = new SubmitDataAfterAction(a);
		this.analyzeHelper = this.afterAction.analyzeHelper;		
	}
	
	/***********
	 * ͨ���������ݼ���û��Ƿ��Ѿ���¼
	 * ***************/
	private boolean chkLogin(){
		//�Ӿ�̬������ȡ���û���Ϣ
		//StaticData sd=StaticData.getInstance();
		//UserInfo ui=sd.getUi();
		//if(ui!=null&&ui.getUserId()>0)
		UserInfo ui=new UserInfo();
		ui.readFromSharedPreferences(a.getApplicationContext());
		System.out.println("ui-->"+ui);
		if(ui.getUserId()<=0)
		{//û�е�¼��ת����¼����
			if(this.a instanceof ActivityCallBackInterface)
				AcvivityLoginGoto.setAcbi((ActivityCallBackInterface)this.a);
			Intent loginIntent=new Intent();
			loginIntent.setClass(this.a, LoginActivity.class);
			this.a.startActivity(loginIntent);
			return false;
		}
		return true;
	}
	/*******************************
	 *�������� 
	 *@param chkLogin,��������ǰ�Ƿ��ڱ��ؼ���û��Ƿ��¼
	 *********************************/
	public void load(boolean chkLogin){
		if(chkLogin){
			if(!chkLogin()){
				return;
			}
		}
		load();
	}
	/*******************************
	 *�������� 
	 *********************************/
	public void load(){		
		if(beforeAction!=null)
		{
			if(beforeAction.doSome()<0)
				return;
		}
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what)
				{
				case 0:
					afterAction.doSome();
					break;
				}
			}
		};		
		//����һ���߳�ȥ��ѯ������Ϣ
		queryThread=new QueryThread();
		queryThread.start();
	}
	/*******************************
	 *�������� 
	 *@param pdNotify���������Ի�����ʾ�ı�
	 *@param canCancle,�Ƿ������û�ȡ�����ݽ���
	 *@param ConfirmTitle��ȷ�϶Ի������
	 *@param ConfirmMsg,ȷ�϶Ի�������
	 *@param chkLogin,��������ǰ�Ƿ��ڱ��ؼ���û��Ƿ��¼
	 *********************************/
	public void load(final String pdNotify,final boolean canCancle,String ConfirmTitle,String ConfirmMsg,boolean chkLogin){
		if(chkLogin){
			if(!chkLogin()){
				return;
			}
		}
		load(pdNotify,canCancle,ConfirmTitle,ConfirmMsg);
	}
	/*******************************
	 *�������� 
	 *@param pdNotify���������Ի�����ʾ�ı�
	 *@param canCancle,�Ƿ������û�ȡ�����ݽ���
	 *@param ConfirmTitle��ȷ�϶Ի������
	 *@param ConfirmMsg,ȷ�϶Ի�������
	 *********************************/
	public void load(final String pdNotify,final boolean canCancle,String ConfirmTitle,String ConfirmMsg){
		AlertDialog.Builder adb=new AlertDialog.Builder(a);
		adb.setTitle(ConfirmTitle);
		adb.setMessage(ConfirmMsg);
		adb.setPositiveButton(R.string.DgPositiveBtTxt, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				load(pdNotify,canCancle);
			}
		});
		adb.setNegativeButton(R.string.DgNegativeBtTxt, null);
		adb.show();
	}
	
	/*******************************
	 *�������� 
	 *@param pdNotify���������Ի�����ʾ�ı�
	 *@param canCancle,�Ƿ������û�ȡ�����ݽ���
	 *@param chkLogin,��������ǰ�Ƿ��ڱ��ؼ���û��Ƿ��¼
	 *********************************/
	public void load(String pdNotify,boolean canCancle,boolean chkLogin){
		if(chkLogin){
			if(!chkLogin()){
				return;
			}
		}
		load(pdNotify,canCancle);
	}
	ProgressDialog pd=null;
	QueryThread queryThread=null;
	//boolean isStopByUser=false;
	/*******************************
	 *�������� 
	 *@param pdNotify���������Ի�����ʾ�ı�
	 *@param canCancle,�Ƿ������û�ȡ�����ݽ���
	 *********************************/
	public void load(String pdNotify,boolean canCancle){
		
		if(beforeAction!=null)
		{
			if(beforeAction.doSome()<0)
				return;
		}
		
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what)
				{
				case 0:
					pd.dismiss();
					afterAction.doSome();					
					break;
				}
			}
		};		
		
		queryThread=null;
		//��ʾ���ȶԻ��򣬵ȴ���ѯ
		pd=ProgressDialog.show(a, null, pdNotify);
		//��ѯ�����ܱ��ж�			
		pd.setCancelable(canCancle);
		//isStopByUser=false;
		pd.setOnCancelListener(new DialogInterface.OnCancelListener() {			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub	
				afterAction.setReInfo(AppConstant.VisitServerResultCode.RESULT_CODE_STOPBYUSER, "");
				if(queryThread!=null&&queryThread.getState()==Thread.State.RUNNABLE)
					queryThread.setActive(false);
//				Message msg=new Message();
//				msg.what=0;
//				handler.sendMessage(msg);
				handler.sendEmptyMessage(0);
			}
		});
		pd.setOnDismissListener(new DialogInterface.OnDismissListener() {			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub				
			}
		});				
		
		//����һ���߳�ȥ��ѯ������Ϣ
		queryThread=new QueryThread();
		queryThread.start();
	}
	
	class QueryThread extends Thread{
		boolean isActive=true;
		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//ȥ��������ѯ��������
			//HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
			HttpClientHelper httpConnHelper=HttpClientHelper.getInstance();
			ArrayList<HashMap<String, String>> paramList=null;
			if(params!=null&&params.getParamList().size()>0)
			{
				paramList=params.getParamList();
				System.out.println(paramList.toString());
			}			
			String responseStr="";
			if(!isActive)
				return;
			responseStr=httpConnHelper.getResponseStr(url_s, paramList);//getString(R.string.urlOrderQuery)
			System.out.println(responseStr);
			//test
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			afterAction.setReInfo(httpConnHelper.getResultCode(), "");			
			if(httpConnHelper.getResultCode()==AppConstant.VisitServerResultCode.RESULT_CODE_OK)
			{
				//�����ӷ��������ص��ַ���
				if(!isActive)
					return;
				analyzeHelper.analyze(responseStr);
				//orderQueryResult=new OrderQueryResult();
				//analyzeHelper.getOrderInfoList(responseStr,orderQueryResult);
				if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code){
					//�Ӿ�̬������ȡ���û���Ϣ
					//StaticData sd=StaticData.getInstance();
					//UserInfo ui=sd.getUi();
					//if(ui!=null&&ui.getUserId()>0)
						UserInfo ui=new UserInfo();
					ui.readFromSharedPreferences(a.getApplicationContext());
					if(ui.getUserId()>0)
					{//�Զ���¼,Ȼ�����¼�������
						String url_s=AppConstant.UrlStrs.URL_LOGIN;
						ParamCollect pc=new ParamCollect();		
						String appkey=AppConstant.AppSafety.USER_APPKEY;
						pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_RESTAURANT_ID, ui.restaurantId);
						pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_USERNAME, ui.username);
						pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_PASSWORD, ui.password);
						pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_APPKEY, appkey);
						responseStr=httpConnHelper.getResponseStr(url_s, pc.getParamList());
						LoginLoadDataAnalyzeHelper analyzeHelper=new LoginLoadDataAnalyzeHelper();
						analyzeHelper.analyze(responseStr);
						if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//�Զ���¼�ɹ�
							run();
							return;
						}												
					}
				}
			}
//			Message msg=new Message();
//			msg.what=0;
//			handler.sendMessage(msg);
			if(!isActive)
				return;
			handler.sendEmptyMessage(0);
		}
	}

}
