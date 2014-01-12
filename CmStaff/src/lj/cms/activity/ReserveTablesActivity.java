package lj.cms.activity;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.ReserveActivity.LvOnItemClickListener;
import lj.cms.activity.ReserveActivity.LvOnScrollListener;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.adapter.LvItemCbAttribute;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.constant.AppConstant;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamsCollect;
import lj.cms.model.ReserveRMsg;
import lj.cms.model.TableInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfCollectFoodAnalyzeHelper;
import lj.cms.string_analysis.DataOfReserveResponseAnalyzeHelper;
import lj.cms.string_analysis.DataOfTablesAnalyzeHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ReserveTablesActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity管理类
	
	DatePicker rtAy_reserveDateDp=null;
	Spinner rtAy_reserveTypeSp=null;
	Button rtAy_ssBt=null;
	
	TextView rtAy_noNotifyTv=null;
	ListView rtAy_tableListLv=null;
	
	Button rtAy_submitBt=null;
	Button rtAy_cancelBt=null;
	
	ArrayList<HashMap<String, Object>> list = null;
	ITBCListAdapter iatclAdapter = null;

	ArrayList<TableInfo> dataList = null;
	int visibleLastIndex = 0;

	int pageNum;// 页号
	final static int PAGE_MAX = 10;// 每页记录数
	int totalCount;// 总数量

	View loadmoreView = null;
	TextView loadingMoreTv = null;
	ProgressBar progressBar1 = null;

	ArrayList<HashMap<String, Long>> tableList = null;
	
	long restaurantId=0;//饭店ID
	String date="";//预订时间
	int reserveType=1;//预订类型
	
	
	String time="";
	String phone="";
	int personCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*将当前activity添加到activitymanager中*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //设置布局
		setContentView(R.layout.activity_reserve_tables);
		
		//从上一个activity获取数据
		Intent preIntent=getIntent();
		restaurantId=preIntent.getLongExtra(AppConstant.IntentExtraName.RESTAURANT_ID, 0);
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        initInstance();//初始化控件
        registerEvent();//注册事件
        //loadData();//加载数据
	}
	void initInstance(){
		rtAy_reserveDateDp=(DatePicker) findViewById(R.id.rtAy_reserveDateDp);
		rtAy_reserveTypeSp=(Spinner) findViewById(R.id.rtAy_reserveTypeSp);
		rtAy_ssBt=(Button) findViewById(R.id.rtAy_ssBt);
		rtAy_noNotifyTv=(TextView) findViewById(R.id.rtAy_noNotifyTv);//无记录提示
		rtAy_tableListLv=(ListView) findViewById(R.id.rtAy_tableListLv);//订单列表
		rtAy_submitBt=(Button) findViewById(R.id.rtAy_submitBt);
		rtAy_cancelBt=(Button) findViewById(R.id.rtAy_cancelBt);
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		rtAy_tableListLv.addFooterView(loadmoreView);//设置列表底部视图
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	void registerEvent(){
		rtAy_ssBt.setOnClickListener(new BtOnClickListener());
		rtAy_submitBt.setOnClickListener(new BtOnClickListener());
		rtAy_cancelBt.setOnClickListener(new BtOnClickListener());
		rtAy_tableListLv.setOnItemClickListener(new LvOnItemClickListener());
		rtAy_tableListLv.setOnScrollListener(new LvOnScrollListener());
	}
	class BtOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.rtAy_ssBt:
				date=rtAy_reserveDateDp.getYear()+"-"+(rtAy_reserveDateDp.getMonth()+1)+"-"+rtAy_reserveDateDp.getDayOfMonth();
				reserveType=rtAy_reserveTypeSp.getSelectedItemPosition()+1;
				list=null;
				iatclAdapter=null;		
				dataList=null;
				visibleLastIndex=0;		
				pageNum=0;//页号
				tableList=null;
				loadData();
				break;
			case R.id.rtAy_submitBt:
				submitReserveDialog();
				break;
			case R.id.rtAy_cancelBt:
				cancelAll();
				break;
			}
		}
		
	}
	/**************子项点击事件监听器***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if(list!=null&&list.size()>arg2){
				HashMap<String, Object> hashTemp =list.get(arg2);				
				LvItemCbOnCheckedChangeListener checkboxAttr=(LvItemCbOnCheckedChangeListener) hashTemp.get("litli_checkedCb");
				if(checkboxAttr.isChecked){
					//从tableList移除
					HashMap<String, Long> table=getTable(arg2);
					if(table!=null){
						tableList.remove(table);
					}
					checkboxAttr.isChecked=false;
				}
				else{//添加到tableList
					if(tableList==null){
						tableList=new ArrayList<HashMap<String,Long>>();
					}
					HashMap<String, Long> table=getTable(arg2);
					if(table==null){
						table=new HashMap<String, Long>();						
					}
					table.put("idx", (long)arg2);
					table.put("tableId", checkboxAttr.tableId);
					tableList.add(table);
					checkboxAttr.isChecked=true;
				}
				if(iatclAdapter!=null){
					iatclAdapter.notifyDataSetChanged();
				}
			}
		}
		
	}
	/********
	 * 获取取消列表中的收藏map
	 * *********************/
	HashMap<String, Long> getTable(long idx) {
		HashMap<String, Long> hm = null;
		if (tableList != null) {
			int size = tableList.size();
			for (int i = 0; i < size; i++) {
				HashMap<String, Long> hmt = tableList.get(i);
				if (hmt.get("idx") == idx) {
					hm = hmt;
					break;
				}
			}
		}
		return hm;
	}
	/********
	 * 获取取消列表中的收藏map
	 * *********************/
	HashMap<String, Long> getTableByTableId(long tableId) {
		HashMap<String, Long> hm = null;
		if (tableList != null) {
			int size = tableList.size();
			for (int i = 0; i < size; i++) {
				HashMap<String, Long> hmt = tableList.get(i);
				if (hmt.get("tableId") == tableId) {
					hm = hmt;
					break;
				}
			}
		}
		return hm;
	}
	/****** 取消所有选择 *******/
	void cancelAll() {
		if (tableList != null) {
			int size = tableList.size();
			for (int i = 0; i < size; i++) {
				HashMap<String, Long> hmt = tableList.get(i);
				int idx = hmt.get("idx").intValue();
				((LvItemCbAttribute) list.get(idx).get("litli_checkedCb")).isChecked = false;
			}
		}
		if (iatclAdapter != null)
			iatclAdapter.notifyDataSetChanged();
		tableList = null;
	}
	/*********************** 列表滚动事件监听器 **********************/
	class LvOnScrollListener implements OnScrollListener {
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if (dataList == null) {
				return;
			}
			int itemsLastIndex = dataList.size() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1;
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& visibleLastIndex >= lastIndex) {
				// 如果是自动加载,可以在这里放置异步加载数据的代码
				if (dataList.size() < totalCount)
					loadData();
			}

		}

	}

	// 设置ListView FooterView显示没了
	void setNoMore() {
		loadingMoreTv.setText("没有更多桌位哦");
		progressBar1.setVisibility(View.GONE);
	}

	// 设置ListView FooterView显示正在加载数据
	void setHasMore() {
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}

	/*****************************************
	 * 加载订单和菜品列表数据
	 ****************************************/
	private void loadData(){
		loadData(true);
	}
	private void loadData(boolean showProgress) {
		if (list == null) {
			rtAy_noNotifyTv.setVisibility(View.VISIBLE);
			rtAy_tableListLv.setVisibility(View.GONE);
		}
		String url_s = AppConstant.UrlStrs.URL_TABLE_LIST;
		ParamCollect pc = new ParamCollect();
		pc.addOrSetParam("restaurantId", restaurantId+"");
		pc.addOrSetParam("date", date);
		pc.addOrSetParam("reserveType", reserveType+"");
		

		pc.addOrSetParam("offset", (PAGE_MAX * pageNum) + "");
		pc.addOrSetParam("max", PAGE_MAX + "");
		DataOfTablesAnalyzeHelper analyzeHelper = new DataOfTablesAnalyzeHelper();
		LoadDataAfterAction afterAction = new LoadDataAfterAction(
				analyzeHelper, ReserveTablesActivity.this);
		DataLoadHelper dataLoader = new DataLoadHelper(url_s, pc, afterAction);
		if (this.pageNum == 0&&showProgress) {
			dataLoader.load("正在加载桌位，请稍后...", true);
		} else {
			dataLoader.load();
		}
	}

	/**********
	 * 数据访问后事件处理
	 * ******/
	class LoadDataAfterAction extends AfterAction {

		public LoadDataAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
			super(analyzeHelper, a);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doSome() {
			// TODO Auto-generated method stub
			if (!this.isServerReturnOK()) {
				if (list == null) {
					rtAy_noNotifyTv.setVisibility(View.VISIBLE);
					rtAy_tableListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfTablesAnalyzeHelper analyzeHelper = (DataOfTablesAnalyzeHelper) this.analyzeHelper;
			if (analyzeHelper.recode == AppConstant.ReCodeFinal.OK.code) {// 成功,加载数据

				if (dataList == null) {
					dataList = new ArrayList<TableInfo>();
					totalCount = analyzeHelper.totalCount;
					System.out.println("totalCount-->" + totalCount);
				}

				ArrayList<TableInfo> tableListTemp = analyzeHelper.tableList;
				if (tableListTemp == null || tableListTemp.size() == 0) {
					if (list == null) {
						rtAy_noNotifyTv.setVisibility(View.VISIBLE);
						rtAy_tableListLv.setVisibility(View.GONE);
					}
					return;
				}
				else{
					rtAy_noNotifyTv.setVisibility(View.GONE);
					rtAy_tableListLv.setVisibility(View.VISIBLE);
				}
				dataList.addAll(tableListTemp);

				/*************** 显示数据 ***********************/
				if (list == null) {
					list = new ArrayList<HashMap<String, Object>>();
				}
				// 添加数据
				int size = tableListTemp.size();
				int idxOffset = list.size();
				for (int i = 0; i < size; i++) {
					TableInfo tableInfo = tableListTemp.get(i);
					HashMap<String, Object> hashTemp = new HashMap<String, Object>();
					hashTemp.put("litli_checkedCb",
							new LvItemCbOnCheckedChangeListener(tableInfo.id,
									idxOffset + i));
					hashTemp.put("litli_nameTv",
							new SpannableString(tableInfo.name));
					hashTemp.put("litli_infoTv", new SpannableString(""));
					
					list.add(hashTemp);
				}
				// 更新适配器
				if (null == iatclAdapter) {
					iatclAdapter = new ITBCListAdapter(ReserveTablesActivity.this, rtAy_tableListLv,
							list, R.layout.lvitem_table_list_item,
							new String[] { "litli_checkedCb","litli_nameTv","litli_infoTv"},
							new int[] { R.id.litli_checkedCb,R.id.litli_nameTv,R.id.litli_infoTv});
					rtAy_tableListLv.setAdapter(iatclAdapter);
				}
				iatclAdapter.notifyDataSetChanged();

				// 更新底部和页号
				if (dataList.size() >= totalCount) {
					Log.i("doSome:", "setNoMore");
					setNoMore();
				}
				pageNum++;
			} 
			else {// 其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}

	}
	
	/*********
	 * listView中CheckBox的点击事件监听器
	 * ************/
	class LvItemCbOnCheckedChangeListener extends LvItemCbAttribute {
		long tableId = 0;
		int idx = 0;

		LvItemCbOnCheckedChangeListener(long tableId, int idx) {
			this.tableId = tableId;
			this.idx = idx;
		}

		LvItemCbOnCheckedChangeListener(long tableId, int idx, boolean checked) {
			this.tableId = tableId;
			this.idx = idx;
			this.isChecked = checked;
		}

	}
	//提交预定
	void submitReserveDialog(){
		AlertDialog dialog=new AlertDialog.Builder(this).create();
		View view=getLayoutInflater().inflate(R.layout.dialog_reserve_table, null);
		final TimePicker rtDg_timeTp=(TimePicker) view.findViewById(R.id.rtDg_timeTp);
		final EditText rtDg_phoneEt=(EditText) view.findViewById(R.id.rtDg_phoneEt);
		final EditText rtDg_personCountEt=(EditText) view.findViewById(R.id.rtDg_personCountEt);
		rtDg_timeTp.setIs24HourView(true);
		dialog.setView(view);
		dialog.setTitle("预定信息");
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		});
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				time=rtDg_timeTp.getCurrentHour()+":"+rtDg_timeTp.getCurrentMinute()+":00";
				phone=rtDg_phoneEt.getText().toString();
				personCount=0;
				try{personCount=Integer.parseInt(rtDg_personCountEt.getText().toString());}catch(Exception ex){}
				dialog.dismiss();
				submitReserve();
			}
		});
		dialog.show();
		
	}
	//提交预定
	void submitReserve(){
		String url_s = AppConstant.UrlStrs.URL_TABLE_RESERVE;
		ParamsCollect pc = new ParamsCollect();
		pc.addParam("restaurantId", restaurantId+"");
		pc.addParam("date", date);
		pc.addParam("reserveType", reserveType+"");
		pc.addParam("phone", phone);
		pc.addParam("time", time);
		pc.addParam("personCount", personCount+"");
		if(tableList==null||tableList.size()<=0){
			Toast.makeText(ReserveTablesActivity.this, "请选择要预定的桌位", Toast.LENGTH_LONG).show();
			return;
		}
		int size=tableList.size();
		for(int i=0;i<size;i++){
			pc.addParam("tableId", tableList.get(i).get("tableId")+"");
		}
		DataOfReserveResponseAnalyzeHelper analyzeHelper=new DataOfReserveResponseAnalyzeHelper();
		SubmitAfterAction afterAction=new SubmitAfterAction(analyzeHelper, ReserveTablesActivity.this);
		DataLoadHelper dataLoader = new DataLoadHelper(url_s,pc,afterAction);
		dataLoader.load("正在预定中，请稍后...", true, true);
	}
	/**********
	 * 数据访问后事件处理
	 * ******/
	class SubmitAfterAction extends AfterAction{

		public SubmitAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
			super(analyzeHelper, a);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doSome() {
			// TODO Auto-generated method stub
			if(!isServerReturnOK()){
				return;
			}
			DataOfReserveResponseAnalyzeHelper analyzeHelper=(DataOfReserveResponseAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){
				//ArrayList<ReserveRMsg> okList=analyzeHelper.okList;
				ArrayList<ReserveRMsg> errorList=analyzeHelper.errorList;
				//全部设置提示为预定成功
				if(tableList!=null){
					int size=tableList.size();
					for(int i=0;i<size;i++){
						HashMap<String, Long> hm=tableList.get(i);
						int idx=hm.get("idx").intValue();
						list.get(idx).put("litli_infoTv", new SpannableString(Html.fromHtml("<font color=green>预定成功</font>")));
						((LvItemCbAttribute)list.get(idx).get("litli_checkedCb")).isChecked=false;
					}
				}
				
				//重新设置失败的为预定失败
				if(errorList!=null&&errorList.size()>0){
					int size=errorList.size();
					for(int i=0;i<size;i++){
						ReserveRMsg rrm=errorList.get(i);
						HashMap<String, Long> hm=getTableByTableId(rrm.tableId);
						if(hm!=null){
							int idx=hm.get("idx").intValue();
							list.get(idx).put("litli_infoTv", new SpannableString(Html.fromHtml("<font color=red>预定失败："+rrm.label+"</font>")));			
						}
					}
				}
				if(iatclAdapter!=null){
					iatclAdapter.notifyDataSetChanged();
				}
				tableList=null;//预定后设置空
			}
			else if (analyzeHelper.recode == AppConstant.ReCodeFinal.NOT_LOGIN.code) {// 没有登录,跳转到登录界面
				AcvivityLoginGoto.setAcbi(ReserveTablesActivity.this);
				Intent loginIntent = new Intent();
				loginIntent.setClass(ReserveTablesActivity.this, LoginActivity.class);
				ReserveTablesActivity.this.startActivity(loginIntent);
				return;
			} else {// 其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}
		
	}
	@Override
	public void loginSuccessCallBack() {
		// TODO Auto-generated method stub
		
	} 
}
