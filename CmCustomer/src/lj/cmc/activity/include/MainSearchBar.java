package lj.cmc.activity.include;

import lj.cmc.constant.*;
import lj.cmc.R;
import android.app.Activity;
import android.app.TabActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.content.Intent;

public class MainSearchBar {
	
	Activity a;//�������
	//private int aIndex;//��������
	//private boolean rgChangeTab;//�Ƿ�ע���л���ǩ�¼�������
	TabHost th;
	int tabIdx=0;
	
	Spinner mainSB_classSelectSp=null;
	AutoCompleteTextView mainSB_keyWordActv=null;
	Button mainSB_submitBt=null;
	
	public MainSearchBar(Activity a) {
		super();
		this.a = a;
		//this.aIndex = aIndex;
		this.th=null;
		initSearchBar();
		registerEvent();
	}
	public MainSearchBar(Activity a,TabHost th) {
		super();
		this.a = a;
		//this.aIndex = aIndex;
		this.th=th;
		initSearchBar();
		registerEvent();
	}
	
	/**********************
	 *���ùؼ���������йؼ���
	 *@param keyWord,����Ĺؼ��� 
	 ************************/
	public void setKeyWordTvTxt(String keyWord)
	{
		mainSB_keyWordActv.setText(keyWord);
	}
	
	/********************************************************
	 * ��ʼ��������
	 *******************************************************/
	private void initSearchBar()
	{
		mainSB_classSelectSp=(Spinner) a.findViewById(R.id.mainSB_classSelectSp);
		mainSB_keyWordActv=(AutoCompleteTextView) a.findViewById(R.id.mainSB_keyWordActv);
		mainSB_submitBt=(Button) a.findViewById(R.id.mainSB_submitBt);
	}
	
	/********************************************************
	 * ע�����¼�������
	 *******************************************************/
	private void registerEvent()
	{
		mainSB_submitBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int ssclassSelectedItemPosition=mainSB_classSelectSp.getSelectedItemPosition();
				String keyWordStr=mainSB_keyWordActv.getText().toString();
//				if(ssclassSelectedItemPosition==0)
//				{//������Ʒ
//					//Toast.makeText(a, "������Ʒ", Toast.LENGTH_SHORT).show();
//				}
//				else if(ssclassSelectedItemPosition==1)
//				{//��������
//					Toast.makeText(a, "��������", Toast.LENGTH_SHORT).show();
//				}
				Intent intent=new Intent();
				//intent.setClass(a, SearchActivity.class);
				intent.putExtra(AppConstant.IntentExtraName.SEARCH_CLASSIDX, ssclassSelectedItemPosition);
				intent.putExtra(AppConstant.IntentExtraName.SEARCH_KEYWORD, keyWordStr);
				a.startActivity(intent);
				if(th!=null)
				{
					a.finish();
				}
			}
		});
		
		if(th!=null)
		{
			mainSB_classSelectSp.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					if(tabIdx==0)
						th.setCurrentTab(arg2);
					else
						th.setCurrentTab((arg2+1)%2);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(a, "onNothingSelected",Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	public void setSpSelectedIndex(int idx)
	{
		this.tabIdx=idx;
		mainSB_classSelectSp.setSelection(idx);
	}
}
