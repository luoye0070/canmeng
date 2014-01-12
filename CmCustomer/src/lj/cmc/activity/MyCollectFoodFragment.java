package lj.cmc.activity;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cmc.R;
import lj.cmc.adapter.ITBCListAdapter;
import lj.cmc.adapter.LvItemBtAttribute;
import lj.cmc.adapter.LvItemCbAttribute;
import lj.cmc.common.AcvivityLoginGoto;
import lj.cmc.constant.AppConstant;
import lj.cmc.dataload.AfterAction;
import lj.cmc.dataload.DataLoadHelper;
import lj.cmc.dataload.SubmitDataAfterAction;
import lj.cmc.internet.ParamCollect;
import lj.cmc.internet.ParamsCollect;
import lj.cmc.model.FoodCollectInfo;
import lj.cmc.string_analysis.AnalyzeHelper;
import lj.cmc.string_analysis.DataOfCollectFoodAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class MyCollectFoodFragment extends Fragment {
	MyCollectActivity mcay = null;

	TextView ffcFm_noNotifyTv = null;// �޼�¼��ʾ
	ListView ffcFm_foodListLv = null;// �����ղ��б�
	Button ffcFm_submitBt = null;// �ύ��ť
	Button ffcFm_cancelBt = null;// ȡ����ť

	ArrayList<HashMap<String, Object>> list = null;
	ITBCListAdapter iatclAdapter = null;

	ArrayList<FoodCollectInfo> dataList = null;
	int visibleLastIndex = 0;

	int pageNum;// ҳ��
	final static int PAGE_MAX = 10;// ÿҳ��¼��
	int totalCount;// ������

	View loadmoreView = null;
	TextView loadingMoreTv = null;
	ProgressBar progressBar1 = null;

	ArrayList<HashMap<String, Long>> cList = null;

	public MyCollectFoodFragment() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("MyCollectFoodFragment--init");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("MyCollectFoodFragment--create");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		System.out.println("MyCollectFoodFragment--createView");
		View view = inflater.inflate(R.layout.fragment_food_collect, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("MyCollectFoodFragment--ActivityCreated");
		super.onActivityCreated(savedInstanceState);

		mcay = (MyCollectActivity) getActivity();

		ffcFm_foodListLv = (ListView) mcay.findViewById(R.id.ffcFm_foodListLv);
		loadmoreView = mcay.getLayoutInflater().inflate(
				R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv = (TextView) loadmoreView
				.findViewById(R.id.loadingMoreTv);
		progressBar1 = (ProgressBar) loadmoreView
				.findViewById(R.id.progressBar1);
		ffcFm_foodListLv.addFooterView(loadmoreView);// �����б�ײ���ͼ
		ffcFm_noNotifyTv = (TextView) mcay.findViewById(R.id.ffcFm_noNotifyTv);
		ffcFm_foodListLv.setOnScrollListener(new LvOnScrollListener());
		if (iatclAdapter == null) {
			loadData();
		} else {
			ffcFm_foodListLv.setAdapter(iatclAdapter);
			iatclAdapter.notifyDataSetChanged();
			if (dataList.size() < totalCount) {
				Log.i("doSome:", "setHasMore");
				setHasMore();
			} else {
				Log.i("doSome:", "setNoMore");
				setNoMore();
			}
		}

		ffcFm_submitBt = (Button) mcay.findViewById(R.id.ffcFm_submitBt);// �ύ��ť
		ffcFm_cancelBt = (Button) mcay.findViewById(R.id.ffcFm_cancelBt);// ȡ����ť
		ffcFm_submitBt.setOnClickListener(new BTOnClickListener());
		ffcFm_cancelBt.setOnClickListener(new BTOnClickListener());

	}

	/********
	 * ��ȡȡ���б��е��ղ�map
	 * *********************/
	HashMap<String, Long> getC(long idx) {
		HashMap<String, Long> hm = null;
		if (cList != null) {
			int size = cList.size();
			for (int i = 0; i < size; i++) {
				HashMap<String, Long> hmt = cList.get(i);
				if (hmt.get("idx") == idx) {
					hm = hmt;
					break;
				}
			}
		}
		return hm;
	}

	class BTOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ffcFm_submitBt:
				submitDel();
				break;
			case R.id.ffcFm_cancelBt:
				cancelAll();
				break;
			}
		}
	}

	/****** ȡ������ѡ�� *******/
	void cancelAll() {
		if (cList != null) {
			int size = cList.size();
			for (int i = 0; i < size; i++) {
				HashMap<String, Long> hmt = cList.get(i);
				int idx = hmt.get("idx").intValue();
				((LvItemCbAttribute) list.get(idx).get("lifcli_checkedCb")).isChecked = false;
			}
		}
		if (iatclAdapter != null)
			iatclAdapter.notifyDataSetChanged();
		cList = null;
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
		loadingMoreTv.setText("û�и������Ŷ");
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
			ffcFm_noNotifyTv.setVisibility(View.VISIBLE);
			ffcFm_foodListLv.setVisibility(View.GONE);
		}
		String url_s = AppConstant.UrlStrs.URL_MY_COLLECT;
		ParamCollect pc = new ParamCollect();
		pc.addOrSetParam("type", "food");//��ѯ�����ղ�

		pc.addOrSetParam("offset", (PAGE_MAX * pageNum) + "");
		pc.addOrSetParam("max", PAGE_MAX + "");
		DataOfCollectFoodAnalyzeHelper analyzeHelper = new DataOfCollectFoodAnalyzeHelper();
		LoadDataAfterAction afterAction = new LoadDataAfterAction(
				analyzeHelper, mcay);
		DataLoadHelper dataLoader = new DataLoadHelper(url_s, pc, afterAction);
		if (this.pageNum == 0&&showProgress) {
			dataLoader.load("���ڼ����ղصĲ��ף����Ժ�...", true, true);
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
					ffcFm_noNotifyTv.setVisibility(View.VISIBLE);
					ffcFm_foodListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfCollectFoodAnalyzeHelper analyzeHelper = (DataOfCollectFoodAnalyzeHelper) this.analyzeHelper;
			if (analyzeHelper.recode == AppConstant.ReCodeFinal.OK.code) {// �ɹ�,��������

				if (dataList == null) {
					dataList = new ArrayList<FoodCollectInfo>();
					totalCount = analyzeHelper.totalCount;
					System.out.println("totalCount-->" + totalCount);
				}

				ArrayList<FoodCollectInfo> foodListTemp = analyzeHelper.foodCollectInfoList;
				if (foodListTemp == null || foodListTemp.size() == 0) {
					if (list == null) {
						ffcFm_noNotifyTv.setVisibility(View.VISIBLE);
						ffcFm_foodListLv.setVisibility(View.GONE);
					}
					return;
				}
				else{
					ffcFm_noNotifyTv.setVisibility(View.GONE);
					ffcFm_foodListLv.setVisibility(View.VISIBLE);
				}
				dataList.addAll(foodListTemp);

				/*************** ��ʾ���� ***********************/
				if (list == null) {
					list = new ArrayList<HashMap<String, Object>>();
				}
				// �������
				int size = foodListTemp.size();
				int idxOffset = list.size();
				for (int i = 0; i < size; i++) {
					FoodCollectInfo foodInfo = foodListTemp.get(i);
					HashMap<String, Object> hashTemp = new HashMap<String, Object>();
					hashTemp.put("lifcli_checkedCb",
							new LvItemCbOnCheckedChangeListener(foodInfo.id,
									idxOffset + i));
					hashTemp.put("lifcli_foodImgIv",foodInfo.image);
					hashTemp.put("lifcli_foodNameBt",
							new LvItemBtOnClickListener(foodInfo.id, idxOffset
									+ i, foodInfo.name));
					hashTemp.put("lifcli_priceTv",
							new SpannableString(""));
					
					list.add(hashTemp);
				}
				// ����������
				if (null == iatclAdapter) {
					iatclAdapter = new ITBCListAdapter(mcay, ffcFm_foodListLv,
							list, R.layout.lvitem_food_collect_list_item,
							new String[] { "lifcli_checkedCb",
									"lifcli_foodImgIv","lifcli_foodNameBt","lifcli_priceTv" },
							new int[] { R.id.lifcli_checkedCb,
									R.id.lifcli_foodImgIv,R.id.lifcli_foodNameBt,R.id.lifcli_priceTv });
					ffcFm_foodListLv.setAdapter(iatclAdapter);
				}
				iatclAdapter.notifyDataSetChanged();

				// ���µײ���ҳ��
				if (dataList.size() >= totalCount) {
					Log.i("doSome:", "setNoMore");
					setNoMore();
				}
				pageNum++;
			} else if (analyzeHelper.recode == AppConstant.ReCodeFinal.NOT_LOGIN.code) {// û�е�¼,��ת����¼����
				AcvivityLoginGoto.setAcbi(mcay);
				Intent loginIntent = new Intent();
				loginIntent.setClass(mcay, LoginActivity.class);
				mcay.startActivity(loginIntent);
				return;
			} else {// ����������ʾ����
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}

	}

	/*********
	 * listView��Button�ĵ���¼�������
	 * ************/
	class LvItemBtOnClickListener extends LvItemBtAttribute {
		long foodId = 0;
		int idx = 0;

		public LvItemBtOnClickListener(long foodId, int idx, String txt) {
			this.foodId = foodId;
			this.idx = idx;
			this.btTxt = txt;
		}

		public LvItemBtOnClickListener(long foodId, int idx) {
			this.foodId = foodId;
			this.idx = idx;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.lifcli_foodNameBt:
				Intent intent=new Intent();
				intent.setClass(mcay, FoodDetailActivity.class);
				intent.putExtra(AppConstant.IntentExtraName.FOOD_ID, foodId);
				startActivity(intent);
				break;
			}

		}
	}

	/*********
	 * listView��CheckBox�ĵ���¼�������
	 * ************/
	class LvItemCbOnCheckedChangeListener extends LvItemCbAttribute implements
			OnClickListener {
		long cId = 0;
		int idx = 0;

		LvItemCbOnCheckedChangeListener(long cId, int idx) {
			this.cId = cId;
			this.idx = idx;
		}

		LvItemCbOnCheckedChangeListener(long cId, int idx, boolean checked) {
			this.cId = cId;
			this.idx = idx;
			this.isChecked = checked;
		}

		@Override
		// public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		public void onClick(View v) { // TODO Auto-generated method stub
			boolean arg1 = ((CheckBox) v).isChecked();
			if (arg1 == true) {
				HashMap<String, Long> cMap = null;
				if (cList == null) {
					cList = new ArrayList<HashMap<String, Long>>();
					cMap = new HashMap<String, Long>();
					cMap.put("idx", (long) idx);
					cMap.put("cId", (long) cId);
					cList.add(cMap);
				} else {
					cMap = getC(idx);
					if (cMap == null) {
						cMap = new HashMap<String, Long>();
						cMap.put("idx", (long) idx);
						cMap.put("cId", (long) cId);
						cList.add(cMap);
					}
				}
				((LvItemCbAttribute) list.get(idx).get("lifcli_checkedCb")).isChecked = true;
			} else {
				if (cList != null) {
					HashMap<String, Long> cMap = getC(idx);
					if (cMap != null) {
						cList.remove(cMap);
					}
				}
				((LvItemCbAttribute) list.get(idx).get("lifcli_checkedCb")).isChecked = false;
			}
			System.out.println("cb--i-->" + idx);
			iatclAdapter.notifyDataSetChanged();
		}

	}

	/********************* �ύɾ������ *******************************/
	void submitDel() {
		String url_s = AppConstant.UrlStrs.URL_DEL_COLLECT;
		ParamsCollect pc = new ParamsCollect();
		pc.addParam("type", "food");//��ѯ�����ղ�
		if(cList!=null&&cList.size()>0){
			int cSize=cList.size();
			for(int i=0;i<cSize;i++)
				pc.addParam("ids",cList.get(i).get("cId")+"");
		}
		SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(mcay,new SubmitDataAfterAction.SubmitCallBack() {				
			@Override
			public void doAfterSuccess() {//�ύ�����ɹ���ˢ������
				// TODO Auto-generated method stub
				list=null;
				iatclAdapter=null;		
				dataList=null;
				visibleLastIndex=0;		
				pageNum=0;//ҳ��
				cList=null;
				loadData(false);
			}				
			@Override
			public void doAfterFail() {}
		});
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
		//DataLoadHelper dataLoader = new DataLoadHelper(url_s, mcay, pc);
		dataLoader.load("�����ύɾ�������Ժ�...", true, "ȷ��ɾ��", "��ȷ��ɾ����ѡ���ղ���");
	}

	/*****************
	 * ˢ������
	 * ****************/
	public void refreshData() {
		list = null;
		iatclAdapter = null;
		dataList = null;
		visibleLastIndex = 0;
		pageNum = 0;// ҳ��
		totalCount = 0;

		loadData();
	}
}
