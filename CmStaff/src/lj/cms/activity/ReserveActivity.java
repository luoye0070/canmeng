package lj.cms.activity;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.common.ActivityManager;
import lj.cms.constant.AppConstant;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.model.RestaurantInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfShopsAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
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

public class ReserveActivity extends Activity {
	ActivityManager am=null;//activity������
	//MainSearchBar msb=null;//������
	//MainNavbarMenu mnm=null;//�����˵�������
	
	TextView rAy_noNotifyTv=null;//�޼�¼��ʾ
	ListView rAy_shopListLv=null;//�����б�
	
	ArrayList<HashMap<String,SpannableString>> list=null;
	SimpleAdapter iatclAdapter=null;
	
	ArrayList<RestaurantInfo> dataList=null;
	int visibleLastIndex=0;
	
	int pageNum;//ҳ��
	final static int PAGE_MAX=10;//ÿҳ��¼��
	int totalCount;//������
	
	View loadmoreView=null;
	TextView loadingMoreTv=null;
	ProgressBar progressBar1=null;
	
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
		setContentView(R.layout.activity_reserve);
		
		//��ʼ��һ��������
        //msb=new MainSearchBar(this);
        //��ʼ��һ�������˵�
        //mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.ReserveActivity);
        initInstance();//��ʼ���ؼ�
        registerEvent();//ע���¼�
        loadData();//��������
	}
	void initInstance(){
		rAy_noNotifyTv=(TextView) findViewById(R.id.rAy_noNotifyTv);//�޼�¼��ʾ
		rAy_shopListLv=(ListView) findViewById(R.id.rAy_shopListLv);//�����б�
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		rAy_shopListLv.addFooterView(loadmoreView);//�����б�ײ���ͼ
	}
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
	 ****************************************/
	void registerEvent(){
		rAy_shopListLv.setOnItemClickListener(new LvOnItemClickListener());
		rAy_shopListLv.setOnScrollListener(new LvOnScrollListener());
	}
	/**************�������¼�������***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//����Ԥ������
			long shopId=dataList.get(arg2).id;
			Intent intent=new Intent();
			intent.setClass(ReserveActivity.this, ReserveTablesActivity.class);
			intent.putExtra(AppConstant.IntentExtraName.RESTAURANT_ID, shopId);
			startActivity(intent);
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
		loadingMoreTv.setText("û�и��෹��Ŷ");
		progressBar1.setVisibility(View.GONE);
	}
	//����ListView FooterView��ʾ���ڼ�������
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *���ط����б�����
	 ****************************************/
	void loadData(){
		String url_s=AppConstant.UrlStrs.URL_SHOP_LIST;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfShopsAnalyzeHelper analyzeHelper=new DataOfShopsAnalyzeHelper();
		LoadDataAfterAction afterAction=new LoadDataAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc, afterAction);		
		if(this.pageNum==0)
		{
			dataLoader.load("���ڼ��ط��꣬���Ժ�...", true);	
		}
		else
		{
			dataLoader.load();
		}
	}
	class LoadDataAfterAction extends AfterAction{

		public LoadDataAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
			super(analyzeHelper, a);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doSome() {
			// TODO Auto-generated method stub
			if(!this.isServerReturnOK())
			{
				if(ReserveActivity.this.list==null)
				{
					rAy_noNotifyTv.setVisibility(View.VISIBLE);
					rAy_shopListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfShopsAnalyzeHelper analyzeHelper=(DataOfShopsAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//�ɹ�,��������				
				ArrayList<RestaurantInfo> dataListTemp=analyzeHelper.shopList;				
				if(dataListTemp==null||dataListTemp.size()==0)
				{
					if(ReserveActivity.this.list==null)
					{
						rAy_noNotifyTv.setVisibility(View.VISIBLE);
						rAy_shopListLv.setVisibility(View.GONE);
					}
					return;
				}
				if(dataList==null){
					dataList=new ArrayList<RestaurantInfo>();
					totalCount=analyzeHelper.totalCount;
					System.out.println("totalCount-->"+totalCount);
				}
				dataList.addAll(dataListTemp);
				
				
				/***************��ʾ����***********************/
				if(list==null){
					list=new ArrayList<HashMap<String,SpannableString>>();
				}
				//�������
				int size=dataListTemp.size();
				int idxOffset=list.size();
				for(int i=0;i<size;i++){
					RestaurantInfo shopInfo=dataListTemp.get(i);
					HashMap<String,SpannableString> hashTemp=new HashMap<String, SpannableString>();
					hashTemp.put("lisli_nameTv", new SpannableString(""+shopInfo.name+""));
					hashTemp.put("lisli_addressTv", new SpannableString("�����ַ:"+shopInfo.province+" "+shopInfo.city+" "+shopInfo.area+" "+shopInfo.street));
					hashTemp.put("lisli_phoneTv", new SpannableString("��ϵ�绰:"+shopInfo.phone+""));
					hashTemp.put("lisli_shopHoursTv", new SpannableString("Ӫҵʱ��:"+shopInfo.shopHoursBeginTime+"-"+shopInfo.shopHoursEndTime));
					list.add(hashTemp);
				}		
				//����������
				if(null==iatclAdapter){
					iatclAdapter=new SimpleAdapter(ReserveActivity.this, list,R.layout.lvitem_shop_list_item, 
							new String[]{"lisli_nameTv","lisli_addressTv","lisli_phoneTv","lisli_shopHoursTv"},
							new int[]{R.id.lisli_nameTv,R.id.lisli_addressTv,R.id.lisli_phoneTv,R.id.lisli_shopHoursTv});
					ReserveActivity.this.rAy_shopListLv.setAdapter(iatclAdapter);
				}
				iatclAdapter.notifyDataSetChanged();
				
				//���µײ���ҳ��
				if(dataList.size()>=totalCount)
				{
					Log.i("doSome:", "setNoMore");
					setNoMore();
				}
				pageNum++;				
			}
			else{//����������ʾ����
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(ReserveActivity.this.list==null)
				{
					rAy_noNotifyTv.setVisibility(View.VISIBLE);
					rAy_shopListLv.setVisibility(View.GONE);
				}
				return;
			}
		}
		
	}

}
