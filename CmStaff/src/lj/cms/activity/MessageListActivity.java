package lj.cms.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.OrderListActivity.LoadDataAfterAction;
import lj.cms.activity.OrderListActivity.LvOnItemClickListener;
import lj.cms.activity.OrderListActivity.LvOnScrollListener;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.broadcast.BroadcastReceiverCustom;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.OptionsMenuProcess;
import lj.cms.constant.AppConstant;
import lj.cms.constant.OrderStatus;
import lj.cms.constant.OrderValid;
import lj.cms.database.DatabaseHelper;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.model.MessageInfo;
import lj.cms.model.OrderInfo;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfOrdersAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class MessageListActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity������
	//MainSearchBar msb=null;//������
	MainNavbarMenu mnm=null;//�����˵�������
	
	TextView mlAy_noNotifyTv=null;//�޼�¼��ʾ
	ListView mlAy_msgListLv=null;//�����б�
	
	ArrayList<HashMap<String, Object>> list=null;
	ITBCListAdapter iatclAdapter=null;
	
	ArrayList<MessageInfo> dataList=null;
	int visibleLastIndex=0;
	
	int pageNum;//ҳ��
	final static int PAGE_MAX=10;//ÿҳ��¼��
	int totalCount;//������
	
	View loadmoreView=null;
	TextView loadingMoreTv=null;
	ProgressBar progressBar1=null;
	
	BroadcastReceiverCustom brc=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*����ǰactivity��ӵ�activitymanager��*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //���ò���
		setContentView(R.layout.activity_message_list);
		
		//��ʼ��һ��������
        //msb=new MainSearchBar(this);
        //��ʼ��һ�������˵�
        //mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.OrderListActivity);
        initInstance();//��ʼ���ؼ�
        registerEvent();//ע���¼�
        loadData();//��������
        
        
      //ע��㲥������
		brc=new BroadcastReceiverCustom(this);
		registerReceiver(brc, new IntentFilter(AppConstant.BroadcastActions.UPDATE_MSG_VIEW));
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	int menuId=item.getItemId();
    	OptionsMenuProcess omp=new OptionsMenuProcess(this);
    	omp.mainOptionsDosome(menuId, this);
		return super.onOptionsItemSelected(item);
	}
	void initInstance(){
		mlAy_noNotifyTv=(TextView) findViewById(R.id.mlAy_noNotifyTv);//�޼�¼��ʾ
		mlAy_msgListLv=(ListView) findViewById(R.id.mlAy_msgListLv);//��Ϣ�б�
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		//mlAy_msgListLv.addFooterView(loadmoreView);//�����б�ײ���ͼ
	}
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
	 ****************************************/
	void registerEvent(){
		mlAy_msgListLv.setOnItemClickListener(new LvOnItemClickListener());
		mlAy_msgListLv.setOnScrollListener(new LvOnScrollListener());
	}
	/**************�������¼�������***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub						
			//���붩����ϸ��Ϣ����
			long orderId=dataList.get(arg2).orderId;
			Intent intent=new Intent();
			intent.setClass(MessageListActivity.this, OrderDetailActivity.class);
			intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
			startActivity(intent);
			//�������ݿ�����Ϣ״̬
			long id=dataList.get(arg2).id;
			DatabaseHelper dh=new DatabaseHelper(getApplicationContext());
			dh.updateStatus(id);
			//�����㲥֪ͨ�������
			Intent intent1=new Intent(AppConstant.BroadcastActions.UPDATE_MSG_VIEW);
			sendBroadcast(intent1);
		}
		
	}
	/***********************�����б�����¼�������**********************/
	class LvOnScrollListener implements OnScrollListener{
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			visibleLastIndex=firstVisibleItem+visibleItemCount-1;
		}
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if(dataList==null)
			{
				return;
			}
			int itemsLastIndex = dataList.size()-1;  //���ݼ����һ�������    
			int lastIndex = itemsLastIndex + 1;  
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE  
					&& visibleLastIndex >= lastIndex) {  
				// ������Զ�����,��������������첽�������ݵĴ���  
				if(dataList.size()<totalCount)
					loadData();
			}  

		}
		
	}	
	//����ListView FooterView��ʾû��
	void setNoMore()
	{
		loadingMoreTv.setText("û�и�����Ϣ��Ŷ");
		progressBar1.setVisibility(View.GONE);
	}
	//����ListView FooterView��ʾ���ڼ�������
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *������Ϣ�б����ݣ������ݿ����
	 ****************************************/
	void loadData(){
		System.out.println("��������");
		UserInfo ui=new UserInfo();
		ui.readFromSharedPreferences(getApplicationContext());
		if(ui.getUserId()==0)
		{//û�е�¼��ת����¼����
			Intent intent = new Intent(MessageListActivity.this,LoginActivity.class);
			startActivity(intent);
			return;
		}
		//�����ݿ��ж�ȡδ����Ϣ 
		DatabaseHelper dh=new DatabaseHelper(getApplicationContext());
		dataList=dh.getMsgNoReadList(ui.getUserId());
		if(dataList==null||dataList.isEmpty()){//û��δ����Ϣ
			mlAy_noNotifyTv.setVisibility(View.VISIBLE);//�޼�¼��ʾ
			mlAy_msgListLv.setVisibility(View.GONE);//��Ϣ�б�
		}
		else{
			mlAy_noNotifyTv.setVisibility(View.GONE);//�޼�¼��ʾ
			mlAy_msgListLv.setVisibility(View.VISIBLE);//��Ϣ�б�
			int size=dataList.size();
			/***************��ʾ����***********************/
			if(list==null){
				list=new ArrayList<HashMap<String,Object>>();
			}
			//�������
			//int idxOffset=list.size();
			for(int i=0;i<size;i++){
				SimpleDateFormat sdfDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				MessageInfo msgInfo=dataList.get(i);
				HashMap<String, Object> hashTemp=new HashMap<String, Object>();
				hashTemp.put("recTime", new SpannableString(sdfDateTime.format(msgInfo.recTime)));
				hashTemp.put("content", new SpannableString(Html.fromHtml(msgInfo.content)));
				list.add(hashTemp);
			}		
			//����������
			if(null==iatclAdapter){
				iatclAdapter=new ITBCListAdapter(MessageListActivity.this,MessageListActivity.this.mlAy_msgListLv, list,R.layout.lvitem_msg_list_item, 
						new String[]{"recTime","content"},
						new int[]{R.id.lmli_rtimeTv,R.id.lmli_contentTv});
				MessageListActivity.this.mlAy_msgListLv.setAdapter(iatclAdapter);
			}
			iatclAdapter.notifyDataSetChanged();			
		}
	}
	
	public void refreshData(){
		list=null;
		iatclAdapter=null;		
		dataList=null;
		visibleLastIndex=0;		
		pageNum=0;//ҳ��
		loadData();
	}
	@Override
	public void loginSuccessCallBack() {
		// TODO Auto-generated method stub
		refreshData();
	}
	@Override
	protected void onDestroy() {
		//am.removeActivity(this);
		
		unregisterReceiver(brc);
		super.onDestroy();
	}
}
