package lj.cmc.activity;

import java.io.File;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import lj.cmc.R;
import lj.cmc.common.ActivityManager;
import lj.cmc.constant.AppConstant;
import lj.cmc.filerw.FileReadWrite;
import lj.cmc.internet.HttpConnectionHelper;
import lj.cmc.model.ApplicationVersionInfo;
import lj.cmc.string_analysis.AnalyzeXmlHelper;
import lj.cmc.string_analysis.AppVersionInfoAnalyzeHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Html;
import android.util.Config;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomActivity extends Activity {
	
	ActivityManager am=null;//activity������
	
	ImageView image=null;
	final int SPLASH_DISPLAY_LENGHT=2000;
	ApplicationVersionInfo newAppVInfo=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//ȥ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��״̬��
		int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window window=this.getWindow();
		window.setFlags(flag, flag);
		/*����ǰactivity��ӵ�activitymanager��*/
        am=ActivityManager.getInstance();
        am.addActivity(this);
        
		setContentView(R.layout.activity_welcom);
		
		HandlerThread handlerThread=new HandlerThread("checkedUpdate");
		handlerThread.start();
		new Handler(handlerThread.getLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//����������
				boolean reCode=WelcomActivity.this.checkUpdate();
				System.out.println("checkUpdate:"+reCode);
				if(reCode)
				{//����и��£������Ի���ѯ���û��Ƿ�Ҫ����
					WelcomActivity.this.updateApp();
				}
				else
				{
					System.out.println("keepActivityThenStart");
					WelcomActivity.this.keepActivityThenStart(1000);
				}
			}
		});
		
		//keepActivityThenStart(2000);
		
//		Intent intent=new Intent();
//		intent.setClass(this, MainTabActivity.class);
//		startActivity(intent);
//		finish();
	}
	
	
	private void keepActivityThenStart(int keepTime)
	{
		//ͣ��2���Ӻ���ת����ҳ����
		try {
			new Handler().postDelayed(new Runnable(){      
	        @Override   
	        public void run() { 
	        	System.out.println("keepActivityThenStart>Run");
	            //Intent mainIntent = new Intent(WelcomActivity.this,MainActivity.class); 
	            Intent mainIntent = new Intent(WelcomActivity.this,MainActivity.class); 
	            //Intent mainIntent = new Intent(WelcomActivity.this,TabsMainActivity.class); 
	            WelcomActivity.this.startActivity(mainIntent);   
	            WelcomActivity.this.finish();   
	        }        
	       }, keepTime);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//����������
	private boolean checkUpdate()
	{
		System.out.println("begin checkUpdate");
		boolean needNew=false;
		//��ȡ���������Ϣ
		HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
		httpConnHelper.setOuttime(1000);//���ó�ʱʱ��Ϊ1��
		System.out.println("checkUpdateUrl:"+AppConstant.UrlStrs.URL_VERSIONINFO);//getString(R.string.urlVersionInfo)
		String responseStr=httpConnHelper.getResponseStr(AppConstant.UrlStrs.URL_VERSIONINFO, null);//getString(R.string.urlVersionInfo)
		System.out.println(responseStr);
		if(AppConstant.VisitServerResultCode.RESULT_CODE_OK==httpConnHelper.getResultCode())
		{//�ɹ���ȡ�汾��Ϣ�������汾��Ϣ�����ȶ԰汾��Ϣ
			try {
//				SAXParserFactory saxPF=SAXParserFactory.newInstance();
//				XMLReader xmlR;
//				xmlR = saxPF.newSAXParser().getXMLReader();
//				AnalyzeXmlHelper axh=new AnalyzeXmlHelper();
//				xmlR.setContentHandler(axh);
//				xmlR.parse(new InputSource(new StringReader(responseStr)));
//				newAppVInfo=axh.getAppVInfo();
				AppVersionInfoAnalyzeHelper aviah=new AppVersionInfoAnalyzeHelper();
				aviah.analyze(responseStr);
				newAppVInfo=aviah.appVInfo;
				if(newAppVInfo==null)
					return false;
				System.out.println("newVersionInfo"+newAppVInfo.toString());
				int vCode=this.getPackageManager().getPackageInfo("lj.cmc", 0).versionCode;
				System.out.println("oldVersionCode:"+vCode);
				int vCodeNew=Integer.parseInt(newAppVInfo.getVerCode());
				if(vCodeNew>vCode)
				{
					needNew=true;
				}
				
//			} catch (SAXException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return needNew;
	}
	
	private void updateApp()
	{
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle(R.string.dialogUpdateSoftTitleTxt);		
		//dialog.setMessage(R.string.dialogUpdateSoftMsgTxt);
		//TextView tv=new TextView(getApplicationContext());
		String updateInfo=newAppVInfo.getUpdateInfo();
		View view=getLayoutInflater().inflate(R.layout.dialog_update_info, null);
		TextView tv=(TextView) view.findViewById(R.id.uiDg_updateInfo);
		tv.setText(Html.fromHtml(updateInfo));
		dialog.setView(view);
		dialog.setPositiveButton(R.string.dialogUpdateSoftPositiveTxt, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.out.println("begin Update Software");
				downLoadUpdate();
			}
		});
		dialog.setNegativeButton(R.string.dialogUpdateSoftNegativeTxt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				WelcomActivity.this.keepActivityThenStart(1);
			}
		});
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				WelcomActivity.this.keepActivityThenStart(1);
			}
		});
		dialog.show();
	}
	
	boolean isCancel=false;
	//�������
	private int downLoadUpdate()
	{
		
		int reCode=-1;
		final ProgressDialog pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(getString(R.string.pdUpdateSoftMsgTxt));
		pd.setCancelable(false);//�ļ����ص��в���ȡ��
		int filesize=Integer.parseInt(this.newAppVInfo.getApkSize());
		pd.setMax(filesize);
		pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if(isCancel)
				{
					WelcomActivity.this.finish();
				}
				else
				{
					updateSoft();
					WelcomActivity.this.finish();
				}
				
			}
		});
		pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				isCancel=true;
			}
		});
		pd.show();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
				System.out.println("downLoadUpdate:"+AppConstant.UrlStrs.URL_UPDATEFILEPATH);//getString(R.string.urlUpdateFilePath)
				String apkupdateUrl=newAppVInfo.getApkUrl();
				if(apkupdateUrl.equals(""))
				{
					apkupdateUrl=AppConstant.UrlStrs.URL_UPDATEFILEPATH+newAppVInfo.getApkName();
				}
				System.out.println("apkupdateUrl:"+apkupdateUrl);
				int reCode=httpConnHelper.downloadUpdateFile(apkupdateUrl, null,
						AppConstant.FileNames.UPDATE_SOFTWARE_NAME,AppConstant.DirNames.UPDATE_SOFTWARE_DIR_NAME,pd);//getString(R.string.urlUpdateFilePath)
				if(AppConstant.VisitServerResultCode.RESULT_CODE_OK==reCode)
				{//���سɹ����������
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pd.dismiss();
				}
				else
				{
					pd.cancel();
				}
			}
		}).start();
		
		
		return reCode;
	}
	
	//��װ�����
	private int updateSoft()
	{
		int reCode=-1;
		try {
			FileReadWrite frw=new FileReadWrite();
			String filePath=frw.getSDCardRoot()+AppConstant.DirNames.UPDATE_SOFTWARE_DIR_NAME+AppConstant.FileNames.UPDATE_SOFTWARE_NAME;
			Intent intent = new Intent(Intent.ACTION_VIEW);
			System.out.println(filePath);
		    intent.setDataAndType(Uri.fromFile(new File(filePath)),
		                "application/vnd.android.package-archive");
		    startActivity(intent);
		    reCode=0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return reCode;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		am.removeActivity(this);
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //���µ������BACK��ͬʱû���ظ�
			System.out.println("onKeyDown:�������˳�");
			onBackPressed();
		    return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//����һ���㲥�ر����н���
		System.out.println("�������˳�");
		am.exitApp();
		super.onBackPressed();
	}
}
