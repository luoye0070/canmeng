package lj.cms.custom_view;

import java.util.ArrayList;

import lj.cms.constant.AppConstant;
import lj.cms.model.AdInfo;

import lj.cms.R;
import lj.cms.adapter.AsyncImageLoaderEnhance;
import lj.cms.adapter.ImageAdapterEnhance;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GalleryExpand extends FlowLayout {
	
	Context context=null;
	
	Button cv_ge_ivl=null;
	Button cv_ge_ivr=null;
	Gallery cv_ge_adG=null;
	HorizontalScrollView cv_ge_hsv=null;
	LinearLayout cv_ge_ll=null;
	
	ArrayList<AdInfo> aiList=null;
	Button [] promptBts=null;
	
	int selectIdx=0;
	
	Thread adThread=null;
	boolean thIsActive=false;
	
	boolean showArrows=false;
	boolean showIndexPrompt=false;
	public boolean isShowArrows() {
		return showArrows;
	}
	public void setShowArrows(boolean showArrows) {
		this.showArrows = showArrows;
	}
	public boolean isShowIndexPrompt() {
		return showIndexPrompt;
	}
	public void setShowIndexPrompt(boolean showIndexPrompt) {
		this.showIndexPrompt = showIndexPrompt;
	}
	
	public GalleryExpand(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	public GalleryExpand(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub		
		initView(context, attrs);
	}
	private void initView(Context context, AttributeSet attrs){
		//设置属性
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_view_gallery_expandParams);
		try {
			//imgbehind = a.getBoolean(R.attr.layout_imageBehind, false);
			showArrows = a.getBoolean(R.styleable.custom_view_gallery_expandParams_cvge_showArrows, false);
			showIndexPrompt=a.getBoolean(R.styleable.custom_view_gallery_expandParams_cvge_showIndexPrompt, false);			
		} finally {
			a.recycle();
		}
		// 导入布局   
		LayoutInflater.from(context).inflate(R.layout.custom_view_gallery_expand, this, true);		
		
		//取到控件引用
		cv_ge_ivl=(Button) findViewById(R.id.cv_ge_ivl);
		cv_ge_ivr=(Button) findViewById(R.id.cv_ge_ivr);
		cv_ge_adG=(Gallery) findViewById(R.id.cv_ge_adG);
		cv_ge_hsv=(HorizontalScrollView) findViewById(R.id.cv_ge_hsv);
		cv_ge_ll=(LinearLayout) findViewById(R.id.cv_ge_ll);
		
		//设置控件属性
		if(showArrows)
		{
			cv_ge_ivl.setVisibility(View.GONE);
			cv_ge_ivl.setOnClickListener(new ViewOnClickListener());
			cv_ge_ivr.setOnClickListener(new ViewOnClickListener());			
		}
		else{
			cv_ge_ivl.setVisibility(View.GONE);
			cv_ge_ivr.setVisibility(View.GONE);
		}
		if(showIndexPrompt){
			cv_ge_hsv.setVisibility(View.VISIBLE);
		}
		else{
			cv_ge_hsv.setVisibility(View.GONE);
		}
		//cv_ge_adG.setOnItemClickListener(new GyOnItemClickListener());
		cv_ge_adG.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub				
				if(showArrows){
					selectIdx=arg2;
					if(arg2==0){
						cv_ge_ivl.setVisibility(View.GONE);
					}
					else{
						cv_ge_ivl.setVisibility(View.VISIBLE);
					}
					if(arg2>=aiList.size()-1){
						cv_ge_ivr.setVisibility(View.GONE);
					}
					else{
						cv_ge_ivr.setVisibility(View.VISIBLE);
					}
				}
				if(showIndexPrompt){
					for(int i=0;i<aiList.size();i++){
						promptBts[i].setBackgroundResource(R.drawable.ge_prompt_bt_unpress_bk);
						}
					promptBts[arg2].setBackgroundResource(R.drawable.ge_prompt_bt_bk);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.context=context;
	}
	//按钮被点击事件处理
	class ViewOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(cv_ge_adG!=null&&aiList!=null){
				int vid=v.getId();	
				switch(vid){
				case R.id.cv_ge_ivl:
					selectIdx--;
					if(selectIdx<0){
						selectIdx=0;
					}					
					break;
				case R.id.cv_ge_ivr:
					selectIdx++;
					if(selectIdx>=aiList.size()){
						selectIdx=aiList.size()-1;
					}
					break;
				}
				if(selectIdx<aiList.size()&&selectIdx>=0){
					cv_ge_adG.setSelection(selectIdx,true);
				}				
			}
		}
	}
	
	public void setGyOnItemClickListener(OnItemClickListener gyOnItemClickListener){
		cv_ge_adG.setOnItemClickListener(gyOnItemClickListener);
	}
	
	//设置要展示的广告列表
	public void setAdList(ArrayList<AdInfo> aiListP,int screenWidth){
		this.aiList=aiListP;
		
		if(this.aiList!=null&&this.aiList.size()>0){
			ArrayList<String> urls=new ArrayList<String>();
			for(AdInfo ai:this.aiList)
			{
				urls.add(ai.getResUrl());
				
			}
			float gkb=((float)this.aiList.get(0).getResHeight())/this.aiList.get(0).getResWidth();
			ImageAdapterEnhance iadapter=new ImageAdapterEnhance(this.context, this.cv_ge_adG, urls,screenWidth-10,gkb,R.drawable.sjgw_ai);
			this.cv_ge_adG.setAdapter(iadapter);
			
			if(showIndexPrompt){
				this.cv_ge_ll.removeAllViews();
				promptBts=new Button[this.aiList.size()];
				for(int i=0;i<this.aiList.size();i++){
					promptBts[i]=new Button(context);
					//promptBts[i]=(Button) ((Activity)context).getLayoutInflater().inflate(R.layout.dynamicload_ge_prompt_list_item_button, null);
					promptBts[i].setBackgroundResource(R.drawable.ge_prompt_bt_unpress_bk);
					int ww=screenWidth/25;
					int hh=ww/2;
					this.cv_ge_ll.addView(promptBts[i],new LayoutParams(ww, hh));
					//this.cv_ge_ll.addView(promptBts[i]);
				}
			}
		}
	}
	
	/********************************
	 * 启动一个线程去滚动加载图片
	 *******************************/
	public void rollAd(final long sleepTime){
		if(adThread!=null){
        	return;
        }
		//启动一个线程去滚动加载图片
        final Handler hdler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==0)
				{
					int dId=(Integer) msg.obj;
					if(aiList!=null&&aiList.size()>dId)
					{
						cv_ge_adG.setSelection(dId);
					}
				}
				super.handleMessage(msg);
			}
        	
        };
        thIsActive=true;
        adThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg=null;
				
				//for(int i=0;i<100000;i++)
				int i=0;
				while(thIsActive)
				{
			        try {
			        	//int j=adImgArray.length;
						//Thread.sleep(3000);
						if(aiList!=null&&aiList.size()>0)
						{
							int j=aiList.size();
							msg=new Message();
							msg.what=0;
							//msg.obj=adImgArray[i%j];
							msg.obj=i%j;
							//selectedAdImgIndex=i%j;
							
							hdler.sendMessage(msg);
							Thread.sleep(sleepTime);
							i++;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					//mainAy_adIs.setImageResource(R.drawable.xjmeizs);
				}
			}
		});
        adThread.start();
	}

}
