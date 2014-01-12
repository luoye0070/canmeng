package lj.cmc.activity;

import com.zxing.activity.CaptureActivity;

import lj.cmc.activity.LoginActivity;
import lj.cmc.common.ActivityCallBackInterface;
import lj.cmc.common.AcvivityLoginGoto;
import lj.cmc.common.StaticData;
import lj.cmc.dataload.AfterAction;
import lj.cmc.dataload.DataLoadHelper;
import lj.cmc.internet.ParamCollect;
import lj.cmc.string_analysis.AnalyzeHelper;
import lj.cmc.string_analysis.CreateOrderResponseDataAnalyzeHelper;
import lj.cmc.constant.AppConstant;
import lj.cmc.activity.include.MainNavbarMenu;
import lj.cmc.activity.include.MainSearchBar;
import lj.cmc.common.ActivityManager;
import lj.cmc.R;
import lj.cmc.R.layout;
import lj.cmc.R.menu;
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
	ActivityManager am=null;//activity������
	//MainSearchBar msb=null;//������
	MainNavbarMenu mnm=null;//�����˵�������
	
	EditText mainAy_shopIdEt=null;//����ID
	EditText mainAy_tableIdEt=null;//��λID
	Button mainAy_scanBt=null;//ɨ�谴ť
	Button mainAy_createOrderBt=null;//����������ť
	
	EditText mainAy_orderIdEt=null;//����ID
	EditText mainAy_partakeCodeEt=null;//������֤��
	Button mainAy_dishBt=null;//�����˰�ť
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*����ǰactivity��ӵ�activitymanager��*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //���ò���
		setContentView(R.layout.activity_main);
		
		//��ʼ��һ��������
        //msb=new MainSearchBar(this);
        //��ʼ��һ�������˵�
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.MainActivity);
        initInstance();//��ʼ���ؼ�
        registerEvent();//ע���¼�
        //loadData();//��������
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/********************
	 *��ʼ���ؼ� 
	 *********************/
	private void initInstance(){
		mainAy_shopIdEt=(EditText) findViewById(R.id.mainAy_shopIdEt);//����ID
		mainAy_tableIdEt=(EditText) findViewById(R.id.mainAy_tableIdEt);//��λID
		mainAy_scanBt=(Button) findViewById(R.id.mainAy_scanBt);
		mainAy_createOrderBt=(Button) findViewById(R.id.mainAy_createOrderBt);//����������ť
		
		mainAy_orderIdEt=(EditText) findViewById(R.id.mainAy_orderIdEt);//����ID
		mainAy_partakeCodeEt=(EditText) findViewById(R.id.mainAy_partakeCodeEt);//������֤��
		mainAy_dishBt=(Button) findViewById(R.id.mainAy_dishBt);//�����˰�ť
	}
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
	 ****************************************/
	private void registerEvent(){
		mainAy_scanBt.setOnClickListener(new BTOnClickListener());
		mainAy_createOrderBt.setOnClickListener(new BTOnClickListener());
		mainAy_dishBt.setOnClickListener(new BTOnClickListener());
	}
	class BTOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.mainAy_scanBt:
				//��ɨ�����ɨ����������ά��
				Intent openCameraIntent = new Intent(MainActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
				break;
			case R.id.mainAy_createOrderBt:
				MainActivity.this.createOrder();
				break;
			case R.id.mainAy_dishBt:
				long orderId=0;
				try{orderId=Long.parseLong(mainAy_orderIdEt.getText().toString());}catch(Exception ex){}
				String partakeCode=mainAy_partakeCodeEt.getText().toString();
				System.out.println("partakeCode-->"+partakeCode);
				if(orderId==0){
					Toast.makeText(MainActivity.this, "�����Ų���Ϊ��", Toast.LENGTH_LONG).show();
					return;
				}
				if(partakeCode==null||"".equals(partakeCode)){
					Toast.makeText(MainActivity.this, "��˲����벻��Ϊ��", Toast.LENGTH_LONG).show();
					return;
				}
				Intent dishIntent=new Intent();
				dishIntent.setClass(MainActivity.this, DishActivity.class);
				dishIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
				dishIntent.putExtra(AppConstant.IntentExtraName.PARTAKE_CODE,partakeCode);
				startActivity(dishIntent);
				break;
			}
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//����ɨ�������ڽ�������ʾ��
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			Toast.makeText(MainActivity.this, "ɨ������"+scanResult, Toast.LENGTH_SHORT).show();
			String []ids=scanResult.split("\\|");
			System.out.println("ids-->"+ids[0]);
			if(ids!=null&&ids.length>0)
				mainAy_shopIdEt.setText(ids[0]);
			if(ids!=null&&ids.length>1)
				mainAy_tableIdEt.setText(ids[1]);
		}
	}
	/********************************
	 * ��������
	 * ********************************/
	private void createOrder(){
		String url_s=AppConstant.UrlStrs.URL_CREATE_ORDER;
		ParamCollect pc=new ParamCollect();
		String shopId=mainAy_shopIdEt.getText().toString();
		String tableId=mainAy_tableIdEt.getText().toString();
		if("".equals(shopId)){
			Toast.makeText(this, "����Ų���Ϊ��", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(tableId)){
			Toast.makeText(this, "��λ�Ų���Ϊ��", Toast.LENGTH_LONG).show();
			return;
		}
		pc.addOrSetParam("restaurantId", shopId);
		pc.addOrSetParam("tableId", tableId);
		CreateOrderResponseDataAnalyzeHelper analyzeHelper=new CreateOrderResponseDataAnalyzeHelper();
		CreateOrderAfterAction afterAction=new CreateOrderAfterAction(analyzeHelper, this);
		DataLoadHelper goodsLoader=new DataLoadHelper(url_s, this, pc, null, analyzeHelper, afterAction);
		goodsLoader.load("���ڴ������������Ժ�...", true);	
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
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//���������ɹ�,��ת����˽���
				Intent dishIntent=new Intent();
				dishIntent.setClass(MainActivity.this, DishActivity.class);
				dishIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, analyzeHelper.orderId);
				dishIntent.putExtra(AppConstant.IntentExtraName.IS_OWNER, true);
				startActivity(dishIntent);
			}
			else if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code){//û�е�¼,��ת����¼����
				AcvivityLoginGoto.setAcbi(MainActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(MainActivity.this, LoginActivity.class);
				MainActivity.this.startActivity(loginIntent);
				return;
			}
			else{//����������ʾ����
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				return;
			}
		}		
	}
	@Override
	public void loginSuccessCallBack() {//��¼�ɹ�������������
		// TODO Auto-generated method stub
		if(mnm!=null)
			mnm.refreshView();
	}
}
