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

	TextView ffcFm_noNotifyTv = null;// 无记录提示
	ListView ffcFm_foodListLv = null;// 菜谱收藏列表
	Button ffcFm_submitBt = null;// 提交按钮
	Button ffcFm_cancelBt = null;// 取消按钮

	ArrayList<HashMap<String, Object>> list = null;
	ITBCListAdapter iatclAdapter = null;

	ArrayList<FoodCollectInfo> dataList = null;
	int visibleLastIndex = 0;

	int pageNum;// 页号
	final static int PAGE_MAX = 10;// 每页记录数
	int totalCount;// 总数量

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
		ffcFm_foodListLv.addFooterView(loadmoreView);// 设置列表底部视图
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

		ffcFm_submitBt = (Button) mcay.findViewById(R.id.ffcFm_submitBt);// 提交按钮
		ffcFm_cancelBt = (Button) mcay.findViewById(R.id.ffcFm_cancelBt);// 取消按钮
		ffcFm_submitBt.setOnClickListener(new BTOnClickListener());
		ffcFm_cancelBt.setOnClickListener(new BTOnClickListener());

	}

	/********
	 * 获取取消列表中的收藏map
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

	/****** 取消所有选择 *******/
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
		loadingMoreTv.setText("没有更多菜谱哦");
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
			ffcFm_noNotifyTv.setVisibility(View.VISIBLE);
			ffcFm_foodListLv.setVisibility(View.GONE);
		}
		String url_s = AppConstant.UrlStrs.URL_MY_COLLECT;
		ParamCollect pc = new ParamCollect();
		pc.addOrSetParam("type", "food");//查询店铺收藏

		pc.addOrSetParam("offset", (PAGE_MAX * pageNum) + "");
		pc.addOrSetParam("max", PAGE_MAX + "");
		DataOfCollectFoodAnalyzeHelper analyzeHelper = new DataOfCollectFoodAnalyzeHelper();
		LoadDataAfterAction afterAction = new LoadDataAfterAction(
				analyzeHelper, mcay);
		DataLoadHelper dataLoader = new DataLoadHelper(url_s, pc, afterAction);
		if (this.pageNum == 0&&showProgress) {
			dataLoader.load("正在加载收藏的菜谱，请稍后...", true, true);
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
					ffcFm_noNotifyTv.setVisibility(View.VISIBLE);
					ffcFm_foodListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfCollectFoodAnalyzeHelper analyzeHelper = (DataOfCollectFoodAnalyzeHelper) this.analyzeHelper;
			if (analyzeHelper.recode == AppConstant.ReCodeFinal.OK.code) {// 成功,加载数据

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

				/*************** 显示数据 ***********************/
				if (list == null) {
					list = new ArrayList<HashMap<String, Object>>();
				}
				// 添加数据
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
				// 更新适配器
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

				// 更新底部和页号
				if (dataList.size() >= totalCount) {
					Log.i("doSome:", "setNoMore");
					setNoMore();
				}
				pageNum++;
			} else if (analyzeHelper.recode == AppConstant.ReCodeFinal.NOT_LOGIN.code) {// 没有登录,跳转到登录界面
				AcvivityLoginGoto.setAcbi(mcay);
				Intent loginIntent = new Intent();
				loginIntent.setClass(mcay, LoginActivity.class);
				mcay.startActivity(loginIntent);
				return;
			} else {// 其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}

	}

	/*********
	 * listView中Button的点击事件监听器
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
	 * listView中CheckBox的点击事件监听器
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

	/********************* 提交删除请求 *******************************/
	void submitDel() {
		String url_s = AppConstant.UrlStrs.URL_DEL_COLLECT;
		ParamsCollect pc = new ParamsCollect();
		pc.addParam("type", "food");//查询店铺收藏
		if(cList!=null&&cList.size()>0){
			int cSize=cList.size();
			for(int i=0;i<cSize;i++)
				pc.addParam("ids",cList.get(i).get("cId")+"");
		}
		SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(mcay,new SubmitDataAfterAction.SubmitCallBack() {				
			@Override
			public void doAfterSuccess() {//提交操作成功后，刷新数据
				// TODO Auto-generated method stub
				list=null;
				iatclAdapter=null;		
				dataList=null;
				visibleLastIndex=0;		
				pageNum=0;//页号
				cList=null;
				loadData(false);
			}				
			@Override
			public void doAfterFail() {}
		});
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
		//DataLoadHelper dataLoader = new DataLoadHelper(url_s, mcay, pc);
		dataLoader.load("正在提交删除，请稍后...", true, "确认删除", "您确认删除勾选的收藏吗？");
	}

	/*****************
	 * 刷新数据
	 * ****************/
	public void refreshData() {
		list = null;
		iatclAdapter = null;
		dataList = null;
		visibleLastIndex = 0;
		pageNum = 0;// 页号
		totalCount = 0;

		loadData();
	}
}
