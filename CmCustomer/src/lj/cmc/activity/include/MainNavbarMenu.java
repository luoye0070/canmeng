package lj.cmc.activity.include;

import java.util.Timer;
import java.util.TimerTask;

import lj.cmc.common.ActivityCallBackInterface;
import lj.cmc.common.AcvivityLoginGoto;
import lj.cmc.common.StaticData;
import lj.cmc.constant.AppConstant;

import lj.cmc.R;
import lj.cmc.activity.LoginActivity;
import lj.cmc.activity.MainActivity;
import lj.cmc.activity.MyCollectActivity;
import lj.cmc.activity.MyOrdersActivity;
import lj.cmc.activity.ReserveActivity;
import android.app.Activity;
import android.content.Intent;
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

	Activity a;//�������
	int aIndex;//��������
	
	//ImageView mainNavbar_mainPageIv=null;
	Button mainNavbar_loginBt=null;//��¼
	Button mainNavbar_mainPageBt=null;//��ҳ
	Button mainNavbar_myOrderBt=null;//�ҵĶ���
	Button mainNavbar_myCollectBt=null;//�ҵ��ղ�
	Button mainNavbar_reserveBt=null;// Ԥ����λ
	
	
	TextView mainNavbar_mainPageNotifyTv=null;//��ҳ
	TextView mainNavbar_myOrderNotifyTv=null;//�ҵĶ���
	TextView mainNavbar_myCollectNotifyTv=null;//�ҵ��ղ�
	TextView mainNavbar_reserveNotifyTv=null;// Ԥ����λ
	
	
	FrameLayout mainNavbar_mainPageFl=null;//��ҳ
	FrameLayout mainNavbar_myOrderFl=null;//�ҵĶ���
	FrameLayout mainNavbar_myCollectFl=null;//�ҵ��ղ�
	FrameLayout mainNavbar_reserveFl=null;// Ԥ����λ
	
	FrameLayout []fl=null;
	
	ImageView mainNavbar_mmlIv=null;
	ImageView mainNavbar_mmrIv=null;
	
	HorizontalScrollView mainNavbar_hsv=null;
	
	int hsvWidth=0;//�����ؼ����
	int btWidth=0;//�˵�����
	int count=0;//�����ؼ�������ʾ�Ĳ˵���ĸ���
	int sx=-1;//��ʼ����λ��
	
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
		StaticData sd=StaticData.getInstance();
		if(sd.getUi()!=null){
			mainNavbar_loginBt.setText("���û�");
		}
	}
	/********************************************************
	 * ��ʼ�������˵�
	 *******************************************************/
	private void initMenu()
	{
		mainNavbar_loginBt=(Button) a.findViewById(R.id.mainNavbar_loginBt);
		
		refreshView();
		
		mainNavbar_mainPageBt=(Button) a.findViewById(R.id.mainNavbar_mainPageBt);
		mainNavbar_myOrderBt=(Button) a.findViewById(R.id.mainNavbar_myOrderBt);;//�ҵĶ���
		mainNavbar_myCollectBt=(Button) a.findViewById(R.id.mainNavbar_myCollectBt);;//�ҵ��ղ�
		mainNavbar_reserveBt=(Button) a.findViewById(R.id.mainNavbar_reserveBt);;// Ԥ����λ
		
		switch(this.aIndex)
		{
		case AppConstant.MNMAIndex.MainActivity:			
			mainNavbar_mainPageBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_bt_bk_press));
			break;
		case AppConstant.MNMAIndex.MyOrdersActivity:			
			mainNavbar_myOrderBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_bt_bk_press));
			break;
		case AppConstant.MNMAIndex.MyCollectActivity:			
			mainNavbar_myCollectBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_bt_bk_press));
			break;
		case AppConstant.MNMAIndex.ReserveActivity:			
			mainNavbar_reserveBt.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.navbar_menu_bt_bk_press));
			break;
		}
		
		mainNavbar_mainPageNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_mainPageNotifyTv);
		mainNavbar_myOrderNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_myOrderNotifyTv);//�ҵĶ���
		mainNavbar_myCollectNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_myCollectNotifyTv);//�ҵ��ղ�
		mainNavbar_reserveNotifyTv=(TextView) a.findViewById(R.id.mainNavbar_reserveNotifyTv);// Ԥ����λ
		
		mainNavbar_mainPageFl=(FrameLayout) a.findViewById(R.id.mainNavbar_mainPageFl);
		mainNavbar_myOrderFl=(FrameLayout) a.findViewById(R.id.mainNavbar_myOrderFl);
		mainNavbar_myCollectFl=(FrameLayout) a.findViewById(R.id.mainNavbar_myCollectFl);
		mainNavbar_reserveFl=(FrameLayout) a.findViewById(R.id.mainNavbar_reserveFl);
		
		
		fl=new FrameLayout[]{
				mainNavbar_mainPageFl,//��ҳ      
				mainNavbar_myOrderFl,//�ҵĶ��� 
				mainNavbar_myCollectFl,//�ҵ��ղ�   
				mainNavbar_reserveFl,// Ԥ����λ            
				};
		
		mainNavbar_mmlIv=(ImageView) a.findViewById(R.id.mainNavbar_mmlIv);
		mainNavbar_mmrIv=(ImageView) a.findViewById(R.id.mainNavbar_mmrIv);
		
		mainNavbar_hsv=(HorizontalScrollView) a.findViewById(R.id.mainNavbar_hsv);
		
	}
	
	/********************************************************
	 * ע�����¼�������
	 *******************************************************/
	private void registerEvent()
	{
		//��¼
		mainNavbar_loginBt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(a instanceof ActivityCallBackInterface)
					AcvivityLoginGoto.setAcbi((ActivityCallBackInterface)a);
				Intent loginIntent=new Intent();
				loginIntent.setClass(a, LoginActivity.class);
				loginIntent.putExtra(AppConstant.IntentExtraName.IN_LOGIN_FROM_BT, true);
				a.startActivity(loginIntent);
			}
		});
		
		//��ҳ
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
		//�ҵĶ���
		mainNavbar_myOrderBt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(a, "hello:1:"+aIndex, Toast.LENGTH_SHORT).show();
				Intent myorderIntent=new Intent();
				myorderIntent.setClass(a.getApplicationContext(), MyOrdersActivity.class);
				a.startActivity(myorderIntent);
				a.finish();
			}
		});
		//�ҵ��ղ�
		mainNavbar_myCollectBt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(a, "hello:0:"+aIndex, Toast.LENGTH_SHORT).show();
				Intent mycollectIntent=new Intent();
				mycollectIntent.setClass(a.getApplicationContext(), MyCollectActivity.class);
				a.startActivity(mycollectIntent);
				a.finish();
			}
		});
		//��λԤ��
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
						case AppConstant.MNMAIndex.MyOrdersActivity:
							sx=((btWidth*1)<(btWidth*(fl.length-count)))?(btWidth*1):(btWidth*(fl.length-count));
							break;
						case AppConstant.MNMAIndex.MyCollectActivity:
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
	 *@param ai ������ʾ�Ĳ˵������� 
	 *@param notifyTxt ���õ���ʾ��Ϣ��һ����һ�����֣����notifyTxtΪnull�����ؿؼ�
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
		case AppConstant.MNMAIndex.MyOrdersActivity:			
			if(notifyTxt==null)
			{
				mainNavbar_myOrderNotifyTv.setVisibility(View.GONE);
			}
			else
			{
				mainNavbar_myOrderNotifyTv.setText(notifyTxt);
				mainNavbar_myOrderNotifyTv.setVisibility(View.VISIBLE);
			}
			break;
		case AppConstant.MNMAIndex.MyCollectActivity:			
			if(notifyTxt==null)
			{
				mainNavbar_myCollectNotifyTv.setVisibility(View.GONE);
			}
			else
			{
				mainNavbar_myCollectNotifyTv.setText(notifyTxt);
				mainNavbar_myCollectNotifyTv.setVisibility(View.VISIBLE);
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
