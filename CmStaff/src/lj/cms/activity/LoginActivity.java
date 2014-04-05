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

	ActivityManager am=null;//activity管理类
	
	AutoCompleteTextView loginAy_restaurantIdAt=null;//饭店ID
	AutoCompleteTextView loginAy_userNameAt=null;//用户名
	EditText loginAy_passwdEt=null;//密码
	CheckBox loginAy_autoLoginCb=null;//自动登录
	Button loginAy_loginBt=null;//登录
	Button loginAy_registerBt=null;//注册
	DataLoadHelper dlHelper=null;//数据交互辅助类
	
	ActivityCallBackInterface acbi=null; 
	boolean fromBt=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*将当前activity添加到activitymanager中*/
        am=ActivityManager.getInstance();
        am.addActivity(this);
		//设置界面布局文件
		setContentView(R.layout.activity_login);
		
		
		//从前一个界面获取跳转活动
		this.acbi=AcvivityLoginGoto.getAcbi();
		
		initInstance();
		registerEvent();
		
		//从上一个界面获取从登录按钮进入的标示
		Intent preIntent=getIntent();
		fromBt=preIntent.getBooleanExtra(AppConstant.IntentExtraName.IN_LOGIN_FROM_BT, false);
		loadData();//加载数据
	}
	/*************************************
	 *初始化控件引用 
	 *********************************/
	private void initInstance()
	{
		loginAy_restaurantIdAt=(AutoCompleteTextView) findViewById(R.id.loginAy_restaurantIdAt);//饭店ID
		loginAy_userNameAt=(AutoCompleteTextView) findViewById(R.id.loginAy_userNameAt);//用户名
		loginAy_passwdEt=(EditText) findViewById(R.id.loginAy_passwdEt);//密码
		loginAy_autoLoginCb=(CheckBox) findViewById(R.id.loginAy_autoLoginCb);//自动登录
		loginAy_loginBt=(Button) findViewById(R.id.loginAy_loginBt);//登录
		loginAy_registerBt=(Button) findViewById(R.id.loginAy_registerBt);
	}
	/*************************
	 *注册事件监听器
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
					//Toast.makeText(LoginActivity.this, "测试登录", Toast.LENGTH_SHORT).show();
					//登录完成将登录信息写入内存数据中，然后finish，调用的界面刷新从内存中获取登录信息
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
		dlHelper.load("正在登陆，请稍后...", true);
		//dlHelper.load();
	}
	//数据交互后动作类
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
//					Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();					
//					return;
//				}
//				Toast.makeText(LoginActivity.this, "很抱歉，发生了未知错误，请重试", Toast.LENGTH_SHORT).show();
//				return;
//			}
			if(lanalyzeHelper.recode==AppConstant.ReCodeFinal.OK.code)
			{//登录成功
				//Toast.makeText(LoginActivity.this, lrInfo.getMsg(), Toast.LENGTH_SHORT).show();
				//System.out.println("msg------"+lrInfo.getMsg());
				//将用户信息存入静态数据中
				//StaticData sd=StaticData.getInstance();
				UserInfo ui=lanalyzeHelper.userInfo;
				//sd.setUi(ui);
				ui.writeToSharedPreferences(getApplicationContext());
				//将地址信息写入静态数据中
//				ArrayList<AddressInfo> addessInfoListTemp=lanalyzeHelper.addressList;
//				if(addessInfoListTemp!=null&&addessInfoListTemp.size()>0)
//					sd.setAddressList(addessInfoListTemp);
				
				//启动消息服务
				Intent service=new Intent();
				service.setClass(getApplicationContext(), MessageService.class);
				startService(service);
				
//				if(loginAy_autoLoginCb.isChecked())
//				{
					//将信息写入SharedPreferences 
					ui.writeToSharedPreferences(getApplicationContext());
//				}
//				else
//				{
//					//将用户信息从SharedPreferences中清除
//					ui.clearFromSharedPreferences(a);
//				}
				if(LoginActivity.this.acbi==null)
				{
					//跳转界面
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
			{//登录失败
				Toast.makeText(LoginActivity.this, lanalyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				System.out.println("ErrorCode---"+lanalyzeHelper.recode+";;msg------"+lanalyzeHelper.remsg);
			}
			
		}
		
	}
	/*********************************
	 * 加载数据,如果上次登录勾选了自动登录则自动登录
	 *********************************/
	void loadData(){
		//如果内存中用户信息则直接进入相应界面
		//StaticData sd=StaticData.getInstance();
		//UserInfo ui=sd.getUi();
		//if(sd.getUi()!=null&&(!this.fromBt)){// 根据职位类型判断进入的界面
		UserInfo ui=new UserInfo();
		ui.readFromSharedPreferences(getApplicationContext());
		if(ui.userId>0&&(!this.fromBt)){// 根据职位类型判断进入的界面
			//启动消息服务
			Intent service=new Intent();
			service.setClass(getApplicationContext(), MessageService.class);
			startService(service);
			//跳转界面
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
