package lj.cms.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import lj.cms.R;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.DESUtil;
import lj.cms.common.ObjectSerialize;
import lj.cms.common.StaticData;
import lj.cms.constant.AppConstant;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.BeforeAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.model.UserInfo;
import lj.cms.service.MessageService;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.LoginLoadDataAnalyzeHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	ActivityManager am=null;//activity������
	
	AutoCompleteTextView loginAy_restaurantIdAt=null;//����ID
	AutoCompleteTextView loginAy_userNameAt=null;//�û���
	EditText loginAy_passwdEt=null;//����
	CheckBox loginAy_autoLoginCb=null;//�Զ���¼
	Button loginAy_loginBt=null;//��¼
	Button loginAy_registerBt=null;//ע��
	DataLoadHelper dlHelper=null;//���ݽ���������
	
	ActivityCallBackInterface acbi=null; 
	boolean fromBt=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*����ǰactivity��ӵ�activitymanager��*/
        am=ActivityManager.getInstance();
        am.addActivity(this);
		//���ý��沼���ļ�
		setContentView(R.layout.activity_login);
		
		
		//��ǰһ�������ȡ��ת�
		this.acbi=AcvivityLoginGoto.getAcbi();
		
		initInstance();
		registerEvent();
		
		//����һ�������ȡ�ӵ�¼��ť����ı�ʾ
		Intent preIntent=getIntent();
		fromBt=preIntent.getBooleanExtra(AppConstant.IntentExtraName.IN_LOGIN_FROM_BT, false);
		loadData();//��������
	}
	/*************************************
	 *��ʼ���ؼ����� 
	 *********************************/
	private void initInstance()
	{
		loginAy_restaurantIdAt=(AutoCompleteTextView) findViewById(R.id.loginAy_restaurantIdAt);//����ID
		loginAy_userNameAt=(AutoCompleteTextView) findViewById(R.id.loginAy_userNameAt);//�û���
		loginAy_passwdEt=(EditText) findViewById(R.id.loginAy_passwdEt);//����
		loginAy_autoLoginCb=(CheckBox) findViewById(R.id.loginAy_autoLoginCb);//�Զ���¼
		loginAy_loginBt=(Button) findViewById(R.id.loginAy_loginBt);//��¼
		loginAy_registerBt=(Button) findViewById(R.id.loginAy_registerBt);
	}
	/*************************
	 *ע���¼�������
	 **************************/
	private void registerEvent()
	{
		loginAy_loginBt.setOnClickListener(new LoginButtonOnClickListener());
		loginAy_registerBt.setOnClickListener(new LoginButtonOnClickListener());
	}
	class LoginButtonOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				switch(v.getId())
				{
				case R.id.loginAy_loginBt:
					//Toast.makeText(LoginActivity.this, "���Ե�¼", Toast.LENGTH_SHORT).show();
					//��¼��ɽ���¼��Ϣд���ڴ������У�Ȼ��finish�����õĽ���ˢ�´��ڴ��л�ȡ��¼��Ϣ
					String restaurantId=loginAy_restaurantIdAt.getText().toString();
					String userName=loginAy_userNameAt.getText().toString();
					String passWd="";
					try {
						//passWd = DESUtil.encryptDES(loginAy_passwdEt.getText().toString(),"3EDCVFRT");
						passWd = DESUtil.encryptDES(loginAy_passwdEt.getText().toString(),AppConstant.AppSafety.USER_SECRET_KEY);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("pwd:"+loginAy_passwdEt.getText().toString());
					System.out.println("pwd:"+passWd);
					login(restaurantId,userName, passWd);
					break;
				case R.id.loginAy_registerBt:
					Uri uri=Uri.parse("http://user.xjmei.com/person/reg");
					Intent it=new Intent(Intent.ACTION_VIEW, uri);
					LoginActivity.this.startActivity(it);
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	private void login(String restaurantId,String userName,String passWd)
	{
		String url_s=AppConstant.UrlStrs.URL_LOGIN;
		ParamCollect pc=new ParamCollect();		
		String appkey=AppConstant.AppSafety.USER_APPKEY;
		pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_RESTAURANT_ID, restaurantId);
		pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_USERNAME, userName);
		pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_PASSWORD, passWd);
		pc.addOrSetParam(AppConstant.HttpRequestParamName.LOGIN_APPKEY, appkey);
		
		LoginLoadDataAnalyzeHelper analyzeHelper=new LoginLoadDataAnalyzeHelper();
		LoginLoadDataAfterAction afterAction=new LoginLoadDataAfterAction(analyzeHelper,LoginActivity.this);
		dlHelper=new DataLoadHelper(url_s, LoginActivity.this, pc, null, analyzeHelper, afterAction);
		dlHelper.load("���ڵ�½�����Ժ�...", true);
		//dlHelper.load();
	}
	//���ݽ���������
	class LoginLoadDataAfterAction extends AfterAction{
		
		public LoginLoadDataAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
			super(analyzeHelper, a);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void doSome() {
			// TODO Auto-generated method stub
			if(!isServerReturnOK())
			{
				return;
			}
			LoginLoadDataAnalyzeHelper lanalyzeHelper=((LoginLoadDataAnalyzeHelper)this.analyzeHelper);
//			LoginResponseInfo lrInfo=lanalyzeHelper.getLrInfo();
//			//System.out.println(lrInfo.toString());
//			if(lrInfo==null)
//			{
//				if(lanalyzeHelper.recode==-1){
//					Toast.makeText(LoginActivity.this, "�û������������", Toast.LENGTH_SHORT).show();					
//					return;
//				}
//				Toast.makeText(LoginActivity.this, "�ܱ�Ǹ��������δ֪����������", Toast.LENGTH_SHORT).show();
//				return;
//			}
			if(lanalyzeHelper.recode==AppConstant.ReCodeFinal.OK.code)
			{//��¼�ɹ�
				//Toast.makeText(LoginActivity.this, lrInfo.getMsg(), Toast.LENGTH_SHORT).show();
				//System.out.println("msg------"+lrInfo.getMsg());
				//���û���Ϣ���뾲̬������
				//StaticData sd=StaticData.getInstance();
				UserInfo ui=lanalyzeHelper.userInfo;
				//sd.setUi(ui);
				ui.writeToSharedPreferences(getApplicationContext());
				//����ַ��Ϣд�뾲̬������
//				ArrayList<AddressInfo> addessInfoListTemp=lanalyzeHelper.addressList;
//				if(addessInfoListTemp!=null&&addessInfoListTemp.size()>0)
//					sd.setAddressList(addessInfoListTemp);
				
				//������Ϣ����
				Intent service=new Intent();
				service.setClass(getApplicationContext(), MessageService.class);
				startService(service);
				
//				if(loginAy_autoLoginCb.isChecked())
//				{
					//����Ϣд��SharedPreferences 
					ui.writeToSharedPreferences(getApplicationContext());
//				}
//				else
//				{
//					//���û���Ϣ��SharedPreferences�����
//					ui.clearFromSharedPreferences(a);
//				}
				if(LoginActivity.this.acbi==null)
				{
					//��ת����
					Intent mainIntent=new Intent();
					mainIntent.setClass(LoginActivity.this, MainActivity.class);
					a.startActivity(mainIntent);
					a.finish();
				}
				else
				{
					a.finish();
					LoginActivity.this.acbi.loginSuccessCallBack();
					
				}
			}
			else 
			{//��¼ʧ��
				Toast.makeText(LoginActivity.this, lanalyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				System.out.println("ErrorCode---"+lanalyzeHelper.recode+";;msg------"+lanalyzeHelper.remsg);
			}
			
		}
		
	}
	/*********************************
	 * ��������,����ϴε�¼��ѡ���Զ���¼���Զ���¼
	 *********************************/
	void loadData(){
		//����ڴ����û���Ϣ��ֱ�ӽ�����Ӧ����
		//StaticData sd=StaticData.getInstance();
		//UserInfo ui=sd.getUi();
		//if(sd.getUi()!=null&&(!this.fromBt)){// ����ְλ�����жϽ���Ľ���
		UserInfo ui=new UserInfo();
		ui.readFromSharedPreferences(getApplicationContext());
		if(ui.userId>0&&(!this.fromBt)){// ����ְλ�����жϽ���Ľ���
			//������Ϣ����
			Intent service=new Intent();
			service.setClass(getApplicationContext(), MessageService.class);
			startService(service);
			//��ת����
			Intent mainIntent=new Intent();
			mainIntent.setClass(LoginActivity.this, MainActivity.class);
			startActivity(mainIntent);
			finish();
			return;
		}
		//UserInfo ui=new UserInfo();
		//ui.readFromSharedPreferences(this);
		if(ui.getUserId()>0&&(!this.fromBt))
		{
			loginAy_restaurantIdAt.setText(ui.getRestaurantId());
			loginAy_userNameAt.setText(ui.getUsername());
			try {
				//loginAy_passwdEt.setText(DESUtil.decryptDES(ui.getPassword(),"3EDCVFRT"));
				loginAy_passwdEt.setText(DESUtil.decryptDES(ui.getPassword(),AppConstant.AppSafety.USER_SECRET_KEY));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			login(ui.getRestaurantId(),ui.getUsername(), ui.getPassword());
		}
		else
		{
			loginAy_restaurantIdAt.setText(ui.getRestaurantId());
			loginAy_userNameAt.setText(ui.getUsername());
			try {
				if(!ui.getPassword().equals(""))
					//loginAy_passwdEt.setText(DESUtil.decryptDES(ui.getPassword(),"3EDCVFRT"));
					loginAy_passwdEt.setText(DESUtil.decryptDES(ui.getPassword(),AppConstant.AppSafety.USER_SECRET_KEY));
				else
					loginAy_passwdEt.setText("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		am.removeActivity(this);
		super.onDestroy();
	}
	
}
