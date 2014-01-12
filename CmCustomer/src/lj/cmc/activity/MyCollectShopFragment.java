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
import lj.cmc.model.RestaurantCollectInfo;
import lj.cmc.string_analysis.AnalyzeHelper;
import lj.cmc.string_analysis.DataOfCollectShopAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class MyCollectShopFragment extends Fragment {
	MyCollectActivity mcay = null;

	TextView fscFm_noNotifyTv = null;// �޼�¼��ʾ
	ListView fscFm_shopListLv = null;// �����ղ��б�
	Button fscFm_submitBt = null;// �ύ��ť
	Button fscFm_cancelBt = null;// ȡ����ť

	ArrayList<HashMap<String, Object>> list = null;
	ITBCListAdapter iatclAdapter = null;

	ArrayList<RestaurantCollectInfo> dataList = null;
	int visibleLastIndex = 0;

	int pageNum;// ҳ��
	final static int PAGE_MAX = 10;// ÿҳ��¼��
	int totalCount;// ������

	View loadmoreView = null;
	TextView loadingMoreTv = null;
	ProgressBar progressBar1 = null;

	ArrayList<HashMap<String, Long>> cList = null;

	public MyCollectShopFragment() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("MyCollectShopFragment--init");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("MyCollectShopFragment--create");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		System.out.println("MyCollectShopFragment--createView");
		View view = inflater.inflate(R.layout.fragment_shop_collect, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("MyCollectShopFragment--ActivityCreated");
		super.onActivityCreated(savedInstanceState);

		mcay = (MyCollectActivity) getActivity();

		fscFm_shopListLv = (ListView) mcay.findViewById(R.id.fscFm_shopListLv);
		loadmoreView = mcay.getLayoutInflater().inflate(
				R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv = (TextView) loadmoreView
				.findViewById(R.id.loadingMoreTv);
		progressBar1 = (ProgressBar) loadmoreView
				.findViewById(R.id.progressBar1);
		fscFm_shopListLv.addFooterView(loadmoreView);// �����б�ײ���ͼ
		fscFm_noNotifyTv = (TextView) mcay.findViewById(R.id.fscFm_noNotifyTv);
		fscFm_shopListLv.setOnScrollListener(new LvOnScrollListener());
		if (iatclAdapter == null) {
			loadData();
		} else {
			fscFm_shopListLv.setAdapter(iatclAdapter);
			iatclAdapter.notifyDataSetChanged();
			if (dataList.size() < totalCount) {
				Log.i("doSome:", "setHasMore");
				setHasMore();
			} else {
				Log.i("doSome:", "setNoMore");
				setNoMore();
			}
		}

		fscFm_submitBt = (Button) mcay.findViewById(R.id.fscFm_submitBt);// �ύ��ť
		fscFm_cancelBt = (Button) mcay.findViewById(R.id.fscFm_cancelBt);// ȡ����ť
		fscFm_submitBt.setOnClickListener(new BTOnClickListener());
		fscFm_cancelBt.setOnClickListener(new BTOnClickListener());

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
			case R.id.fscFm_submitBt:
				submitDel();
				break;
			case R.id.fscFm_cancelBt:
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
				((LvItemCbAttribute) list.get(idx).get("liscli_isChecked")).isChecked = false;
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
			fscFm_noNotifyTv.setVisibility(View.VISIBLE);
			fscFm_shopListLv.setVisibility(View.GONE);
		}
		String url_s = AppConstant.UrlStrs.URL_MY_COLLECT;
		ParamCollect pc = new ParamCollect();
		pc.addOrSetParam("type", "restaurant");//��ѯ�����ղ�

		pc.addOrSetParam("offset", (PAGE_MAX * pageNum) + "");
		pc.addOrSetParam("max", PAGE_MAX + "");
		DataOfCollectShopAnalyzeHelper analyzeHelper = new DataOfCollectShopAnalyzeHelper();
		LoadDataAfterAction afterAction = new LoadDataAfterAction(
				analyzeHelper, mcay);
		DataLoadHelper dataLoader = new DataLoadHelper(url_s, pc, afterAction);
		if (this.pageNum == 0&&showProgress) {
			dataLoader.load("���ڼ����ղصķ��꣬���Ժ�...", true, true);
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
					fscFm_noNotifyTv.setVisibility(View.VISIBLE);
					fscFm_shopListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfCollectShopAnalyzeHelper analyzeHelper = (DataOfCollectShopAnalyzeHelper) this.analyzeHelper;
			if (analyzeHelper.recode == AppConstant.ReCodeFinal.OK.code) {// �ɹ�,��������

				if (dataList == null) {
					dataList = new ArrayList<RestaurantCollectInfo>();
					totalCount = analyzeHelper.totalCount;
					System.out.println("totalCount-->" + totalCount);
				}

				ArrayList<RestaurantCollectInfo> shopListTemp = analyzeHelper.restaurantCollectInfoList;
				if (shopListTemp == null || shopListTemp.size() == 0) {
					if (list == null) {
						fscFm_noNotifyTv.setVisibility(View.VISIBLE);
						fscFm_shopListLv.setVisibility(View.GONE);
					}
					return;
				}
				else{
					fscFm_noNotifyTv.setVisibility(View.GONE);
					fscFm_shopListLv.setVisibility(View.VISIBLE);
				}
				dataList.addAll(shopListTemp);

				/*************** ��ʾ���� ***********************/
				if (list == null) {
					list = new ArrayList<HashMap<String, Object>>();
				}
				// �������
				int size = shopListTemp.size();
				int idxOffset = list.size();
				for (int i = 0; i < size; i++) {
					RestaurantCollectInfo shopInfo = shopListTemp.get(i);
					HashMap<String, Object> hashTemp = new HashMap<String, Object>();
					hashTemp.put("liscli_isChecked",
							new LvItemCbOnCheckedChangeListener(shopInfo.id,
									idxOffset + i));
					hashTemp.put("liscli_shopName",
							new LvItemBtOnClickListener(shopInfo.id, idxOffset
									+ i, shopInfo.name));
					list.add(hashTemp);
				}
				// ����������
				if (null == iatclAdapter) {
					iatclAdapter = new ITBCListAdapter(mcay, fscFm_shopListLv,
							list, R.layout.lvitem_shop_collect_list_item,
							new String[] { "liscli_isChecked",
									"liscli_shopName" },
							new int[] { R.id.liscli_isChecked,
									R.id.liscli_shopName });
					fscFm_shopListLv.setAdapter(iatclAdapter);
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
		long restaurantId = 0;
		int idx = 0;

		public LvItemBtOnClickListener(long restaurantId, int idx, String txt) {
			this.restaurantId = restaurantId;
			this.idx = idx;
			this.btTxt = txt;
		}

		public LvItemBtOnClickListener(long restaurantId, int idx) {
			this.restaurantId = restaurantId;
			this.idx = idx;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.liscli_shopName:
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
				((LvItemCbAttribute) list.get(idx).get("liscli_isChecked")).isChecked = true;
			} else {
				if (cList != null) {
					HashMap<String, Long> cMap = getC(idx);
					if (cMap != null) {
						cList.remove(cMap);
					}
				}
				((LvItemCbAttribute) list.get(idx).get("liscli_isChecked")).isChecked = false;
			}
			System.out.println("cb--i-->" + idx);
			iatclAdapter.notifyDataSetChanged();
		}

	}

	/********************* �ύɾ������ *******************************/
	void submitDel() {
		String url_s = AppConstant.UrlStrs.URL_DEL_COLLECT;
		ParamsCollect pc = new ParamsCollect();
		pc.addParam("type", "restaurant");//��ѯ�����ղ�
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
