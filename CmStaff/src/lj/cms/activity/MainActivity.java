package lj.cms.activity;

import com.zxing.activity.CaptureActivity;

import lj.cms.activity.LoginActivity;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.StaticData;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.CreateOrderResponseDataAnalyzeHelper;
import lj.cms.constant.AppConstant;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.activity.include.MainSearchBar;
import lj.cms.common.ActivityManager;
import lj.cms.R;
import lj.cms.R.layout;
import lj.cms.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity管理类
	//MainSearchBar msb=null;//搜索栏
	MainNavbarMenu mnm=null;//导航菜单管理类
	
	EditText mainAy_shopIdEt=null;//饭店ID
	EditText mainAy_tableIdEt=null;//桌位ID
	Button mainAy_scanBt=null;//扫描按钮
	Button mainAy_createOrderBt=null;//创建订单按钮
	
	Button mainAy_reserveBt=null;//参与点菜按钮
	EditText mainAy_orderIdEt=null;//订单ID
	Button mainAy_accountBt=null;//结账按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*将当前activity添加到activitymanager中*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //设置布局
		setContentView(R.layout.activity_main);
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        //初始化一个导航菜单
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.MainActivity);
        initInstance();//初始化控件
        registerEvent();//注册事件
        //loadData();//加载数据
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/********************
	 *初始化控件 
	 *********************/
	private void initInstance(){
		mainAy_shopIdEt=(EditText) findViewById(R.id.mainAy_shopIdEt);//饭店ID
		mainAy_tableIdEt=(EditText) findViewById(R.id.mainAy_tableIdEt);//桌位ID
		mainAy_scanBt=(Button) findViewById(R.id.mainAy_scanBt);
		mainAy_createOrderBt=(Button) findViewById(R.id.mainAy_createOrderBt);//创建订单按钮
		
		mainAy_reserveBt=(Button) findViewById(R.id.mainAy_reserveBt);//参与点菜按钮
		mainAy_orderIdEt=(EditText) findViewById(R.id.mainAy_orderIdEt);//订单ID
		mainAy_accountBt=(Button) findViewById(R.id.mainAy_accountBt);//结账按钮
		
		//从工作人员信息中获取饭店ID
		StaticData sd=StaticData.getInstance();
		UserInfo ui=sd.getUi();
		if(ui!=null)
			mainAy_shopIdEt.setText(ui.restaurantId);
		else{//没有登录跳转到登录界面
			AcvivityLoginGoto.setAcbi(MainActivity.this);
			Intent loginIntent=new Intent();
			loginIntent.setClass(MainActivity.this, LoginActivity.class);
			MainActivity.this.startActivity(loginIntent);
			return;
		}
			
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	private void registerEvent(){
		mainAy_scanBt.setOnClickListener(new BTOnClickListener());
		mainAy_createOrderBt.setOnClickListener(new BTOnClickListener());
		mainAy_reserveBt.setOnClickListener(new BTOnClickListener());
		mainAy_accountBt.setOnClickListener(new BTOnClickListener());
	}
	class BTOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.mainAy_scanBt:
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(MainActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
				break;
			case R.id.mainAy_createOrderBt:
				MainActivity.this.createOrder();
				break;
			case R.id.mainAy_reserveBt:
				//跳转到预定界面
				//从工作人员信息中获取饭店ID
				StaticData sd=StaticData.getInstance();
				UserInfo ui=sd.getUi();
				if(ui!=null){
					long shopId=0;
					try{shopId=Integer.parseInt(ui.restaurantId);}catch(Exception ex){}
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, ReserveTablesActivity.class);
					intent.putExtra(AppConstant.IntentExtraName.RESTAURANT_ID, shopId);
					startActivity(intent);
				}
				else{//没有登录跳转到登录界面
					AcvivityLoginGoto.setAcbi(MainActivity.this);
					Intent loginIntent=new Intent();
					loginIntent.setClass(MainActivity.this, LoginActivity.class);
					MainActivity.this.startActivity(loginIntent);
					return;
				}
				
				break;
			case R.id.mainAy_accountBt:
				//进入订单详细信息界面
				long orderId=0;
				try{orderId=Long.parseLong(mainAy_orderIdEt.getText().toString());}catch(Exception ex){}
				if(orderId==0){
					Toast.makeText(MainActivity.this, "请输入正确的订单ID", Toast.LENGTH_LONG);
					return;
				}
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, OrderDetailActivity.class);
				intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
				startActivity(intent);
				break;
			}
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			Toast.makeText(MainActivity.this, "扫描结果："+scanResult, Toast.LENGTH_SHORT).show();
			String []ids=scanResult.split("\\|");
			System.out.println("ids-->"+ids[0]);
			//if(ids!=null&&ids.length>0)
			//	mainAy_shopIdEt.setText(ids[0]);
			if(ids!=null&&ids.length>1)
				mainAy_tableIdEt.setText(ids[1]);
		}
	}
	/********************************
	 * 加载数据
	 * ********************************/
	private void createOrder(){
		String url_s=AppConstant.UrlStrs.URL_CREATE_ORDER;
		ParamCollect pc=new ParamCollect();
		String shopId=mainAy_shopIdEt.getText().toString();
		String tableId=mainAy_tableIdEt.getText().toString();
		if("".equals(shopId)){
			Toast.makeText(this, "饭店ID不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(tableId)){
			Toast.makeText(this, "桌位ID不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		pc.addOrSetParam("restaurantId", shopId);
		pc.addOrSetParam("tableId", tableId);
		CreateOrderResponseDataAnalyzeHelper analyzeHelper=new CreateOrderResponseDataAnalyzeHelper();
		CreateOrderAfterAction afterAction=new CreateOrderAfterAction(analyzeHelper, this);
		DataLoadHelper goodsLoader=new DataLoadHelper(url_s, this, pc, null, analyzeHelper, afterAction);
		goodsLoader.load("正在创建订单，请稍后...", true);	
	}
	class CreateOrderAfterAction extends AfterAction{
		public CreateOrderAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
			super(analyzeHelper, a);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void doSome() {
			// TODO Auto-generated method stub
			if(!this.isServerReturnOK())
			{
				//Toast.makeText(a, "ceshi", Toast.LENGTH_SHORT).show();
				return;
			}
			CreateOrderResponseDataAnalyzeHelper analyzeHelper=(CreateOrderResponseDataAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//创建订单成功,跳转到点菜界面
				Intent dishIntent=new Intent();
				dishIntent.setClass(MainActivity.this, DishActivity.class);
				dishIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, analyzeHelper.orderId);
				dishIntent.putExtra(AppConstant.IntentExtraName.IS_OWNER, true);
				startActivity(dishIntent);
			}
			else if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code){//没有登录,跳转到登录界面
				AcvivityLoginGoto.setAcbi(MainActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(MainActivity.this, LoginActivity.class);
				MainActivity.this.startActivity(loginIntent);
				return;
			}
			else{//其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				return;
			}
		}		
	}
	@Override
	public void loginSuccessCallBack() {//登录成功后所做的事情
		// TODO Auto-generated method stub
		if(mnm!=null)
			mnm.refreshView();
	}
}
