package lj.cms.activity;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.adapter.LvItemBtAttribute;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.StaticData;
import lj.cms.constant.AppConstant;
import lj.cms.constant.DishesStatus;
import lj.cms.constant.DishesValid;
import lj.cms.constant.PositionType;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.dataload.SubmitDataAfterAction;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamsCollect;
import lj.cms.model.DishesInfo;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfDishListAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class DishListActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity������
	//MainSearchBar msb=null;//������
	MainNavbarMenu mnm=null;//�����˵�������
	
	TextView dlAy_noNotifyTv=null;//�޼�¼��ʾ
	ListView dlAy_dishListLv=null;//�����б�
	
	ArrayList<HashMap<String, Object>> list=null;
	ITBCListAdapter iatclAdapter=null;
	
	ArrayList<DishesInfo> dataList=null;
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
		setContentView(R.layout.activity_dish_list);
		
		//��ʼ��һ��������
        //msb=new MainSearchBar(this);
        //��ʼ��һ�������˵�
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.DishListActivity);
        initInstance();//��ʼ���ؼ�
        registerEvent();//ע���¼�
        loadData();//��������
	}
	void initInstance(){
		dlAy_noNotifyTv=(TextView) findViewById(R.id.dlAy_noNotifyTv);//�޼�¼��ʾ
		dlAy_dishListLv=(ListView) findViewById(R.id.dlAy_dishListLv);//�����б�
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		dlAy_dishListLv.addFooterView(loadmoreView);//�����б�ײ���ͼ
	}
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
	 ****************************************/
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
	 ****************************************/
	private void registerEvent(){
		dlAy_dishListLv.setOnScrollListener(new LvOnScrollListener());
	}

	/***********************����б�����¼�������**********************/
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
		loadingMoreTv.setText("û�и�����Ŷ");
		progressBar1.setVisibility(View.GONE);
	}
	//����ListView FooterView��ʾ���ڼ�������
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *���ص���б�����
	 ****************************************/
	private void loadData(){
		String url_s=AppConstant.UrlStrs.URL_DISH_LIST;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfDishListAnalyzeHelper analyzeHelper=new DataOfDishListAnalyzeHelper();
		LoadDataAfterAction afterAction=new LoadDataAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc, afterAction);		
		if(this.pageNum==0)
		{
			dataLoader.load("���ڼ��ص����Ϣ�����Ժ�...", true,true);	
		}
		else
		{
			dataLoader.load();
		}
	}
	/**********
	 * ���ݷ��ʺ��¼�����
	 * ******/
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
				if(DishListActivity.this.list==null)
				{
					dlAy_noNotifyTv.setVisibility(View.VISIBLE);
					dlAy_dishListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfDishListAnalyzeHelper analyzeHelper=(DataOfDishListAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//�ɹ�,��������				
				
				if(dataList==null){
					dataList=new ArrayList<DishesInfo>();					
					totalCount=analyzeHelper.totalCount;
					System.out.println("totalCount-->"+totalCount);
				}
				
				ArrayList<DishesInfo> dataListTemp=analyzeHelper.dishesList;				
				if(dataListTemp==null||dataListTemp.size()==0)
				{
					if(DishListActivity.this.list==null)
					{
						dlAy_noNotifyTv.setVisibility(View.VISIBLE);
						dlAy_dishListLv.setVisibility(View.GONE);
					}
					return;
				}
				dataList.addAll(dataListTemp);
				
				
				/***************��ʾ����***********************/
				if(list==null){
					list=new ArrayList<HashMap<String,Object>>();
				}
				//�������
				int size=dataListTemp.size();
				for(int i=0;i<size;i++){
					DishesInfo dishesInfo=dataListTemp.get(i);
					HashMap<String,Object> hashTemp=new HashMap<String, Object>();
					hashTemp.put("lidlis_dishCzQxBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,0));
					hashTemp.put("lidlis_dishCzBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,1));
					hashTemp.put("lidlis_foodNameTv", new SpannableString("����:"+dishesInfo.foodName+""));
					hashTemp.put("lidlis_countTv", new SpannableString("����:"+dishesInfo.num+""));
					hashTemp.put("lidlis_statusTv", new SpannableString("״̬:"+DishesStatus.getLable(dishesInfo.status)));//
					list.add(hashTemp);
				}		
				//����������
				if(null==iatclAdapter){
					iatclAdapter=new ITBCListAdapter(DishListActivity.this, dlAy_dishListLv, list,R.layout.lvitem_dish_list_item_simple, 
							new String[]{"lidlis_dishCzQxBt","lidlis_dishCzBt","lidlis_foodNameTv","lidlis_countTv","lidlis_statusTv"},
							new int[]{R.id.lidlis_dishCzQxBt,R.id.lidlis_dishCzBt,R.id.lidlis_foodNameTv,R.id.lidlis_countTv,R.id.lidlis_statusTv});
					DishListActivity.this.dlAy_dishListLv.setAdapter(iatclAdapter);
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
			else if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code){//û�е�¼,��ת����¼����
				AcvivityLoginGoto.setAcbi(DishListActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(DishListActivity.this, LoginActivity.class);
				DishListActivity.this.startActivity(loginIntent);
				return;
			}
			else{//����������ʾ����
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(dataList==null)
					DishListActivity.this.finish();
				return;
			}
		}
		
	}
	/*********
	 * listView��Button�ĵ���¼�������
	 * ************/
	class LvItemBtOnClickListener extends LvItemBtAttribute{
		long dishIds=0;//����id
		int status=0;//״̬
		int valid=0;//��Ч��
		String url_str="";
		int type=0;//0ȡ����ť��1�ύ��ť
		public LvItemBtOnClickListener(long dishIds,int status,int valid,int type){
			this.dishIds=dishIds;
			this.status=status;
			this.valid=valid;
			this.type=type;
			//����״̬����Ч�����ð�ť��ʾ�ķ�ʽ�Լ��ύ�ĵ�ַ			
			this.btV=View.GONE;
//			if(orderInfo.valid>=OrderValid.USER_CANCEL_VALID.code||orderInfo.status>=OrderStatus.SHIPPING_STATUS.code){
//				return;
//			}
			StaticData sd=StaticData.getInstance();
			UserInfo ui=sd.getUi();
			ArrayList<Integer> positionList=ui.positionTypeList;
			if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.WAITER.code , positionList) || PositionType.isInList(PositionType.WAITER_HEADER.code , positionList)) {   //����Ա
                if (valid == DishesValid.ORIGINAL_VALID.code && status == DishesStatus.ORIGINAL_STATUS.code) {//��ʼ̬����ȡ����ȷ�ϵ��
                    if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>ȡ��</a>&nbsp;&nbsp;";
                    if(type==1){
                    	this.btTxt="ȷ�ϵ��";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_AFFIRM_DISH;
                    }
                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "affirmDish", params: [dishIds: id, orderId: orderId]) + "'>ȷ�ϵ��</a>&nbsp;&nbsp;";
                }
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.COOKED_STATUS.code) {//������ɿ����ϲ�
                	if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.GONE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	if(type==1){
                    	this.btTxt="�ϲ����";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_COMPLETE_SERVE_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeServe", params: [dishIds: id, orderId: orderId]) + "'>�ϲ����</a>&nbsp;&nbsp;";
                }
            }
            if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.COOK.code , positionList)) {
                //��ʦ
                //println("chushi");
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.VERIFYING_STATUS.code) {//��ʼ̬����ȡ���Ϳ�ʼ����
                	if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>ȡ��</a>&nbsp;&nbsp;";
                    if(type==1){
                    	this.btTxt="��ʼ����";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_BEGIN_COOK_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>ȡ��</a>&nbsp;&nbsp;";
                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "beginCook", params: [dishIds: id, orderId: orderId]) + "'>��ʼ����</a>&nbsp;&nbsp;";
                }
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.COOKING_ORDERED_STATUS.code) {//�����е���Ч�ĵ�˿���������ɲ���
                	if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.GONE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	if(type==1){
                    	this.btTxt="�������";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_COMPLETE_COOK_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeCook", params: [dishIds: id, orderId: orderId]) + "'>�������</a>&nbsp;&nbsp;";
                }
            }
            
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url_s=this.url_str;
			ParamsCollect pc=new ParamsCollect();
			pc.addParam("dishIds", this.dishIds+"");//���ID	
			SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(DishListActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
				@Override
				public void doAfterSuccess() {//�ύ�����ɹ���ˢ������
					// TODO Auto-generated method stub
					list=null;
					iatclAdapter=null;		
					dataList=null;
					visibleLastIndex=0;		
					pageNum=0;//ҳ��
					loadData();
				}				
				@Override
				public void doAfterFail() {
					// TODO Auto-generated method stub
					
				}
			});
			DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
			//dataLoader.load("���ڵ�ˣ����Ժ�...", true);	
			dataLoader.load("�����ύ�����Ժ�...", true,"ȷ�ϵ�˲���","��ȷ�϶Ե���������Ĳ�����",true);
		}		
	} 
	@Override
	public void loginSuccessCallBack() {
		// TODO Auto-generated method stub
		list=null;
		iatclAdapter=null;		
		dataList=null;
		visibleLastIndex=0;		
		pageNum=0;//ҳ��
		loadData();
	}
}
