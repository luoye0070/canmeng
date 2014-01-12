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
	ActivityManager am=null;//activity������
	
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

	int pageNum;// ҳ��
	final static int PAGE_MAX = 10;// ÿҳ��¼��
	int totalCount;// ������

	View loadmoreView = null;
	TextView loadingMoreTv = null;
	ProgressBar progressBar1 = null;

	ArrayList<HashMap<String, Long>> tableList = null;
	
	long restaurantId=0;//����ID
	String date="";//Ԥ��ʱ��
	int reserveType=1;//Ԥ������
	
	
	String time="";
	String phone="";
	int personCount;
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
		setContentView(R.layout.activity_reserve_tables);
		
		//����һ��activity��ȡ����
		Intent preIntent=getIntent();
		restaurantId=preIntent.getLongExtra(AppConstant.IntentExtraName.RESTAURANT_ID, 0);
		
		//��ʼ��һ��������
        //msb=new MainSearchBar(this);
        initInstance();//��ʼ���ؼ�
        registerEvent();//ע���¼�
        //loadData();//��������
	}
	void initInstance(){
		rtAy_reserveDateDp=(DatePicker) findViewById(R.id.rtAy_reserveDateDp);
		rtAy_reserveTypeSp=(Spinner) findViewById(R.id.rtAy_reserveTypeSp);
		rtAy_ssBt=(Button) findViewById(R.id.rtAy_ssBt);
		rtAy_noNotifyTv=(TextView) findViewById(R.id.rtAy_noNotifyTv);//�޼�¼��ʾ
		rtAy_tableListLv=(ListView) findViewById(R.id.rtAy_tableListLv);//�����б�
		rtAy_submitBt=(Button) findViewById(R.id.rtAy_submitBt);
		rtAy_cancelBt=(Button) findViewById(R.id.rtAy_cancelBt);
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		rtAy_tableListLv.addFooterView(loadmoreView);//�����б�ײ���ͼ
	}
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
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
				pageNum=0;//ҳ��
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
	/**************�������¼�������***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if(list!=null&&list.size()>arg2){
				HashMap<String, Object> hashTemp =list.get(arg2);				
				LvItemCbOnCheckedChangeListener checkboxAttr=(LvItemCbOnCheckedChangeListener) hashTemp.get("litli_checkedCb");
				if(checkboxAttr.isChecked){
					//��tableList�Ƴ�
					HashMap<String, Long> table=getTable(arg2);
					if(table!=null){
						tableList.remove(table);
					}
					checkboxAttr.isChecked=false;
				}
				else{//��ӵ�tableList
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
	 * ��ȡȡ���б��е��ղ�map
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
	 * ��ȡȡ���б��е��ղ�map
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
	/****** ȡ������ѡ�� *******/
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
	/*********************** �б�����¼������� **********************/
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
			int itemsLastIndex = dataList.size() - 1; // ���ݼ����һ�������
			int lastIndex = itemsLastIndex + 1;
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& visibleLastIndex >= lastIndex) {
				// ������Զ�����,��������������첽�������ݵĴ���
				if (dataList.size() < totalCount)
					loadData();
			}

		}

	}

	// ����ListView FooterView��ʾû��
	void setNoMore() {
		loadingMoreTv.setText("û�и�����λŶ");
		progressBar1.setVisibility(View.GONE);
	}

	// ����ListView FooterView��ʾ���ڼ�������
	void setHasMore() {
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}

	/*****************************************
	 * ���ض����Ͳ�Ʒ�б�����
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
			dataLoader.load("���ڼ�����λ�����Ժ�...", true);
		} else {
			dataLoader.load();
		}
	}

	/**********
	 * ���ݷ��ʺ��¼�����
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
			if (analyzeHelper.recode == AppConstant.ReCodeFinal.OK.code) {// �ɹ�,��������

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

				/*************** ��ʾ���� ***********************/
				if (list == null) {
					list = new ArrayList<HashMap<String, Object>>();
				}
				// �������
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
				// ����������
				if (null == iatclAdapter) {
					iatclAdapter = new ITBCListAdapter(ReserveTablesActivity.this, rtAy_tableListLv,
							list, R.layout.lvitem_table_list_item,
							new String[] { "litli_checkedCb","litli_nameTv","litli_infoTv"},
							new int[] { R.id.litli_checkedCb,R.id.litli_nameTv,R.id.litli_infoTv});
					rtAy_tableListLv.setAdapter(iatclAdapter);
				}
				iatclAdapter.notifyDataSetChanged();

				// ���µײ���ҳ��
				if (dataList.size() >= totalCount) {
					Log.i("doSome:", "setNoMore");
					setNoMore();
				}
				pageNum++;
			} 
			else {// ����������ʾ����
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}

	}
	
	/*********
	 * listView��CheckBox�ĵ���¼�������
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
	//�ύԤ��
	void submitReserveDialog(){
		AlertDialog dialog=new AlertDialog.Builder(this).create();
		View view=getLayoutInflater().inflate(R.layout.dialog_reserve_table, null);
		final TimePicker rtDg_timeTp=(TimePicker) view.findViewById(R.id.rtDg_timeTp);
		final EditText rtDg_phoneEt=(EditText) view.findViewById(R.id.rtDg_phoneEt);
		final EditText rtDg_personCountEt=(EditText) view.findViewById(R.id.rtDg_personCountEt);
		rtDg_timeTp.setIs24HourView(true);
		dialog.setView(view);
		dialog.setTitle("Ԥ����Ϣ");
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		});
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "ȷ��", new DialogInterface.OnClickListener() {
			
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
	//�ύԤ��
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
			Toast.makeText(ReserveTablesActivity.this, "��ѡ��ҪԤ������λ", Toast.LENGTH_LONG).show();
			return;
		}
		int size=tableList.size();
		for(int i=0;i<size;i++){
			pc.addParam("tableId", tableList.get(i).get("tableId")+"");
		}
		DataOfReserveResponseAnalyzeHelper analyzeHelper=new DataOfReserveResponseAnalyzeHelper();
		SubmitAfterAction afterAction=new SubmitAfterAction(analyzeHelper, ReserveTablesActivity.this);
		DataLoadHelper dataLoader = new DataLoadHelper(url_s,pc,afterAction);
		dataLoader.load("����Ԥ���У����Ժ�...", true, true);
	}
	/**********
	 * ���ݷ��ʺ��¼�����
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
				//ȫ��������ʾΪԤ���ɹ�
				if(tableList!=null){
					int size=tableList.size();
					for(int i=0;i<size;i++){
						HashMap<String, Long> hm=tableList.get(i);
						int idx=hm.get("idx").intValue();
						list.get(idx).put("litli_infoTv", new SpannableString(Html.fromHtml("<font color=green>Ԥ���ɹ�</font>")));
						((LvItemCbAttribute)list.get(idx).get("litli_checkedCb")).isChecked=false;
					}
				}
				
				//��������ʧ�ܵ�ΪԤ��ʧ��
				if(errorList!=null&&errorList.size()>0){
					int size=errorList.size();
					for(int i=0;i<size;i++){
						ReserveRMsg rrm=errorList.get(i);
						HashMap<String, Long> hm=getTableByTableId(rrm.tableId);
						if(hm!=null){
							int idx=hm.get("idx").intValue();
							list.get(idx).put("litli_infoTv", new SpannableString(Html.fromHtml("<font color=red>Ԥ��ʧ�ܣ�"+rrm.label+"</font>")));			
						}
					}
				}
				if(iatclAdapter!=null){
					iatclAdapter.notifyDataSetChanged();
				}
				tableList=null;//Ԥ�������ÿ�
			}
			else if (analyzeHelper.recode == AppConstant.ReCodeFinal.NOT_LOGIN.code) {// û�е�¼,��ת����¼����
				AcvivityLoginGoto.setAcbi(ReserveTablesActivity.this);
				Intent loginIntent = new Intent();
				loginIntent.setClass(ReserveTablesActivity.this, LoginActivity.class);
				ReserveTablesActivity.this.startActivity(loginIntent);
				return;
			} else {// ����������ʾ����
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
