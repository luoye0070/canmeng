package lj.cms.activity.include;

import java.util.Timer;
import java.util.TimerTask;

import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.StaticData;
import lj.cms.constant.AppConstant;
import lj.cms.model.UserInfo;

import lj.cms.R;
import lj.cms.activity.DishListActivity;
import lj.cms.activity.LoginActivity;
import lj.cms.activity.MainActivity;
import lj.cms.activity.OrderListActivity;
import lj.cms.activity.ReserveActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.*;

public class MainNavbarMenu {

	Activity a;//界面对象
	int aIndex;//界面索引
	
	//ImageView mainNavbar_mainPageIv=null;
	Button mainNavbar_loginBt=null;//登录
	Button mainNavbar_mainPageBt=null;//首页
	Button mainNavbar_orderListBt=null;//订单列表
	Button mainNavbar_dishListBt=null;//点菜列表
	Button mainNavbar_reserveBt=null;// 预定桌位
	
	
	TextView mainNavbar_mainPageNotifyTv=null;//首页
	TextView mainNavbar_orderListNotifyTv=null;//我的订单
	TextView mainNavbar_dishListNotifyTv=null;//我的收藏
	TextView mainNavbar_reserveNotifyTv=null;// 预定桌位
	
	
	FrameLayout mainNavbar_mainPageFl=null;//首页
	FrameLayout mainNavbar_orderListFl=null;//我的订单
	FrameLayout mainNavbar_dishListFl=null;//我的收藏
	FrameLayout mainNavbar_reserveFl=null;// 预定桌位
	
	FrameLayout []fl=null;
	
	ImageView mainNavbar_mmlIv=null;
	ImageView mainNavbar_mmrIv=null;
	
	HorizontalScrollView mainNavbar_hsv=null;
	
	int hsvWidth=0;//滚动控件宽度
	int btWidth=0;//菜单项宽度
	int count=0;//滚动控件中能显示的菜单项的个数
	int sx=-1;//初始滚动位置
	
	public MainNavbarMenu(Activity a,int aIndex) {
		// TODO Auto-generated constructor stub
		this.a=a;
		this.aIndex=aIndex;
		initMenu();
		registerEvent();
	}
	public void refreshView(){
		if(mainNavbar_loginBt==null){
			return;
		}
		//StaticData sd=StaticData.getInstance();
		//if(sd.getUi()!=null){
		UserInfo ui=new UserInfo();
		ui.readFromSharedPreferences(a.getApplicationContext());
		if(ui.getUserId()>0){
			mainNavbar_loginBt.setText("换用户");
		}
	}
	/********************************************************
	 * 初始化导航菜单
	 *******************************************************/
	private void initMenu()
	{
		mainNavbar_loginBt=(Button) a.findViewById(R.id.mainNavbar_loginBt);
		
		refreshView();
		
		mainNavbar_mainPageBt=(Button) a.findViewById(R.id.mainNavbar_mainPageBt);
		mainNavbar_orderListBt=(Button) a.findViewById(R.id.mainNavbar_orderListBt);;//我的订单
		mainNavbar_dishListBt=(Button) a.findViewById(R.id.mainNavbar_dishListBt);;//我的收藏
		mainNavbar_reserveBt=(Button) a.findViewById(R.id.mainNavbar_reserveBt);;// 预定桌位
		
		switch(this.aIndex)
		{
		case AppConstant.MNMAIndex.MainActivity:			
			mainNavbar_mainPageBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_mainpage_press));
			mainNavbar_mainPageBt.setTextColor(Color.parseColor("#e67323"));
			break;
		case AppConstant.MNMAIndex.OrderListActivity:			
			mainNavbar_orderListBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_myorder_press));
			mainNavbar_orderListBt.setTextColor(Color.parseColor("#e67323"));
			break;
		case AppConstant.MNMAIndex.DishListActivity:			
			mainNavbar_dishListBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_reserve_press));
			mainNavbar_dishListBt.setTextColor(Color.parseColor("#e67323"));
			break;
		case AppConstant.MNMAIndex.ReserveActivity:			
			mainNavbar_reserveBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_reserve_press));
			mainNavbar_reserveBt.setTextColor(Color.parseColor("#e67323"));
			break;
		}
		
		mainNavbar_mainPageNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_mainPageNotifyTv);
		mainNavbar_orderListNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_orderListNotifyTv);//我的订单
		mainNavbar_dishListNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_dishListNotifyTv);//我的收藏
		mainNavbar_reserveNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_reserveNotifyTv);// 预定桌位
		
		mainNavbar_mainPageFl=(FrameLayout) a.findViewById(R.id.mainNavbar_mainPageFl);
		mainNavbar_orderListFl=(FrameLayout) a.findViewById(R.id.mainNavbar_orderListFl);
		mainNavbar_dishListFl=(FrameLayout) a.findViewById(R.id.mainNavbar_dishListFl);
		mainNavbar_reserveFl=(FrameLayout) a.findViewById(R.id.mainNavbar_reserveFl);
		
		
		fl=new FrameLayout[]{
				mainNavbar_mainPageFl,//首页      
				mainNavbar_orderListFl,//我的订单 
				mainNavbar_dishListFl,//我的收藏   
				//mainNavbar_reserveFl,// 预定桌位            
				};
		
		mainNavbar_mmlIv=(ImageView) a.findViewById(R.id.mainNavbar_mmlIv);
		mainNavbar_mmrIv=(ImageView) a.findViewById(R.id.mainNavbar_mmrIv);
		
		mainNavbar_hsv=(HorizontalScrollView) a.findViewById(R.id.mainNavbar_hsv);
		
	}
	
	/********************************************************
	 * 注册点击事件处理函数
	 *******************************************************/
	private void registerEvent()
	{
		//登录
		mainNavbar_loginBt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(a instanceof ActivityCallBackInterface)
//					AcvivityLoginGoto.setAcbi((ActivityCallBackInterface)a);
				Intent loginIntent=new Intent();
				loginIntent.setClass(a, LoginActivity.class);
				loginIntent.putExtra(AppConstant.IntentExtraName.IN_LOGIN_FROM_BT, true);
				a.startActivity(loginIntent);
				a.finish();
			}
		});
		
		//首页
		mainNavbar_mainPageBt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(a, "hello:0:"+aIndex, Toast.LENGTH_SHORT).show();
				Intent mainIntent=new Intent();
				//mainIntent.setClass(a, MainActivity.class);
				mainIntent.setClass(a.getApplicationContext(), MainActivity.class);
				a.startActivity(mainIntent);
				a.finish();
			}
		});
		//订单列表
		mainNavbar_orderListBt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(a, "hello:1:"+aIndex, Toast.LENGTH_SHORT).show();
				Intent orderListIntent=new Intent();
				orderListIntent.setClass(a.getApplicationContext(), OrderListActivity.class);
				a.startActivity(orderListIntent);
				a.finish();
			}
		});
		//点菜列表
		mainNavbar_dishListBt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(a, "hello:0:"+aIndex, Toast.LENGTH_SHORT).show();
				Intent dishListIntent=new Intent();
				dishListIntent.setClass(a.getApplicationContext(), DishListActivity.class);
				a.startActivity(dishListIntent);
				a.finish();
			}
		});
		//桌位预定
		mainNavbar_reserveBt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(a, "hello:0:"+aIndex, Toast.LENGTH_SHORT).show();
				Intent reserveIntent=new Intent();
				reserveIntent.setClass(a.getApplicationContext(), ReserveActivity.class);
				a.startActivity(reserveIntent);
				a.finish();
			}
		});		
		if(fl!=null&&fl[0]!=null)
		{
			fl[0].getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
				
				@Override
				public boolean onPreDraw() {
					// TODO Auto-generated method stub
					if(btWidth==0){
						System.out.println("fl[0]:"+fl[0].getMeasuredWidth());
						btWidth=fl[0].getMeasuredWidth();
						
						count=hsvWidth/(btWidth);
						System.out.println("onWindowFocusChanged--->count:"+count);
						int cz=hsvWidth-(btWidth)*count;
						System.out.println("onWindowFocusChanged--->cz:"+cz);
						int pd=(cz/count);
						btWidth+=pd;
						System.out.println("onWindowFocusChanged--->pd:"+pd);
						for(int i=0;i<fl.length;i++){
							//fl[i].setLayoutParams(new LayoutParams(btWidth+pd, LayoutParams.WRAP_CONTENT));
							LayoutParams lp=fl[i].getLayoutParams();
							lp.width=btWidth;
							fl[i].setLayoutParams(lp);
						}						
					}
					return true;
				}
			});
			
			fl[0].getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				
				@Override
				public void onGlobalLayout() {
					// TODO Auto-generated method stub
					if(sx==-1&&btWidth!=0)
					{
						switch(aIndex)
						{
						case AppConstant.MNMAIndex.MainActivity:
							sx=(btWidth*0)<(btWidth*(fl.length-count))?(btWidth*0):(btWidth*(fl.length-count));
							break;
						case AppConstant.MNMAIndex.OrderListActivity:
							sx=((btWidth*1)<(btWidth*(fl.length-count)))?(btWidth*1):(btWidth*(fl.length-count));
							break;
						case AppConstant.MNMAIndex.DishListActivity:
							sx=((btWidth*2)<(btWidth*(fl.length-count)))?(btWidth*2):(btWidth*(fl.length-count));
							break;
						case AppConstant.MNMAIndex.ReserveActivity:
							sx=((btWidth*3)<(btWidth*(fl.length-count)))?(btWidth*3):(btWidth*(fl.length-count));
							break;
						}
						System.out.println("sx--->>>>>>>"+sx);
						mainNavbar_hsv.scrollTo(sx, 0);
						
						if(mainNavbar_hsv.getScrollX()>0){
							System.out.println("left:"+mainNavbar_hsv.getScrollX());
							mainNavbar_mmlIv.setVisibility(View.VISIBLE);
						}
						else{
							System.out.println("no-left:"+mainNavbar_hsv.getScrollX());
							mainNavbar_mmlIv.setVisibility(View.INVISIBLE);
						}
						if(mainNavbar_hsv.getScrollX()<=btWidth*(fl.length-count-1)){
							System.out.println("right:"+mainNavbar_hsv.getScrollX());
							mainNavbar_mmrIv.setVisibility(View.VISIBLE);
						}
						else{
							System.out.println("no-right:"+mainNavbar_hsv.getScrollX());
							mainNavbar_mmrIv.setVisibility(View.INVISIBLE);
						}
						
						if(mainNavbar_hsv.getScrollX()!=sx){
							sx=-1;
						}
					}
				}
			});
		}
		
		mainNavbar_hsv.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				if(hsvWidth==0){
					System.out.println("mta_hsv:"+mainNavbar_hsv.getMeasuredWidth());
					hsvWidth=mainNavbar_hsv.getMeasuredWidth();
				}
				return true;
			}
		});
		
		mainNavbar_hsv.setOnTouchListener(new OnTouchListener() {
//			private Timer timer=null;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_MOVE){
//					final View vf=v;
//					if(timer==null){
//						timer=new Timer();
//					}
//					timer.cancel();
//					timer.schedule(new TimerTask() {
//						
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							scrollView(vf);
//						}
//					}, 500);
				}
				else if(event.getAction()==MotionEvent.ACTION_DOWN){
					//beginSx=v.getScrollX();
					//System.out.println("beginSx="+beginSx);
				}
				else if(event.getAction()==MotionEvent.ACTION_UP){
					scrollView(v);
					final View fv=v;
					new Handler().postDelayed(new Runnable(){  
		                @Override  
		                public void run() {  
		                	scrollView(fv);
		                }  
		            }, 500);  
				}
				
				return false;
			}
			private void scrollView(View v){
				int endSx=v.getScrollX();
				System.out.println("endSx="+endSx);
				if(endSx<=(btWidth/2)){
					v.scrollTo(0, 0);
				}
				else if(endSx<=btWidth*1+(btWidth/2)){
					v.scrollTo(btWidth*1, 0);
				}
				else if(endSx<=btWidth*2+(btWidth/2)){
					v.scrollTo(btWidth*2, 0);
				}
				else if(endSx<=btWidth*3+(btWidth/2)){
					v.scrollTo(btWidth*3, 0);
				}
				else if(endSx<=btWidth*4+(btWidth/2)){
					v.scrollTo(btWidth*4, 0);
				}
				else if(endSx<=btWidth*5+(btWidth/2)){
					v.scrollTo(btWidth*5, 0);
				}
				else if(endSx<=btWidth*6+(btWidth/2)){
					v.scrollTo(btWidth*6, 0);
				}
				else if(endSx<=btWidth*7+(btWidth/2)){
					v.scrollTo(btWidth*7, 0);
				}
				else if(endSx<=btWidth*8+(btWidth/2)){
					v.scrollTo(btWidth*8, 0);
				}
				else if(endSx<=btWidth*9+(btWidth/2)){
					v.scrollTo(btWidth*9, 0);
				}
				else if(endSx<=btWidth*10+(btWidth/2)){
					v.scrollTo(btWidth*10, 0);
				}
				
				if(v.getScrollX()>0){
					System.out.println("left:"+v.getScrollX());
					mainNavbar_mmlIv.setVisibility(View.VISIBLE);
				}
				else{
					System.out.println("no-left:"+v.getScrollX());
					mainNavbar_mmlIv.setVisibility(View.INVISIBLE);
				}
				if(v.getScrollX()<=btWidth*(fl.length-count-1)){
					System.out.println("right:"+v.getScrollX());
					mainNavbar_mmrIv.setVisibility(View.VISIBLE);
				}
				else{
					System.out.println("no-right:"+v.getScrollX());
					mainNavbar_mmrIv.setVisibility(View.INVISIBLE);
				}
			}
		});
	}
	
	/*****************************
	 *@param ai 设置提示的菜单项索引 
	 *@param notifyTxt 设置的提示信息，一般是一个数字，如果notifyTxt为null则隐藏控件
	 *****************************/
	public void setNotifyText(int ai,String notifyTxt)
	{
		
		switch(ai)
		{
		case AppConstant.MNMAIndex.MainActivity:
			if(notifyTxt==null)
			{
				mainNavbar_mainPageNotifyTv.setVisibility(View.GONE);
			}
			else
			{
				mainNavbar_mainPageNotifyTv.setText(notifyTxt);
				mainNavbar_mainPageNotifyTv.setVisibility(View.VISIBLE);
			}
			break;
		case AppConstant.MNMAIndex.OrderListActivity:			
			if(notifyTxt==null)
			{
				mainNavbar_orderListNotifyTv.setVisibility(View.GONE);
			}
			else
			{
				mainNavbar_orderListNotifyTv.setText(notifyTxt);
				mainNavbar_orderListNotifyTv.setVisibility(View.VISIBLE);
			}
			break;
		case AppConstant.MNMAIndex.DishListActivity:			
			if(notifyTxt==null)
			{
				mainNavbar_dishListNotifyTv.setVisibility(View.GONE);
			}
			else
			{
				mainNavbar_dishListNotifyTv.setText(notifyTxt);
				mainNavbar_dishListNotifyTv.setVisibility(View.VISIBLE);
			}
			break;
		case AppConstant.MNMAIndex.ReserveActivity:			
			if(notifyTxt==null)
			{
				mainNavbar_reserveNotifyTv.setVisibility(View.GONE);
			}
			else
			{
				mainNavbar_reserveNotifyTv.setText(notifyTxt);
				mainNavbar_reserveNotifyTv.setVisibility(View.VISIBLE);
			}
			break;
		}
	}
}
