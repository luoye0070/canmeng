package lj.cms.activity;

import lj.cms.R;
import lj.cms.common.ActivityManager;
import lj.cms.constant.AppConstant;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class FoodDetailActivity extends Activity {
	ActivityManager am=null;//activity管理类
	
	WebView fdAy_htmlWv=null;
	
	long foodId;
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
		setContentView(R.layout.activity_food_detail);
		
		//从上一个activity获取数据
		Intent preIntent=getIntent();
		foodId=preIntent.getLongExtra(AppConstant.IntentExtraName.FOOD_ID, 0);
		
		fdAy_htmlWv=(WebView) findViewById(R.id.fdAy_htmlWv);
		
		//设置能缩放
		fdAy_htmlWv.getSettings().setSupportZoom(true);
		fdAy_htmlWv.getSettings().setBuiltInZoomControls(true);
		//goodsItdetailsAy_contentWv.getSettings().setDisplayZoomControls(true);
		
		//设置能执行js
		fdAy_htmlWv.getSettings().setJavaScriptEnabled(true);
		//设置加载过程
		final ProgressDialog pd=new ProgressDialog(this);
		//显示进度对话框，等待查询
		pd.setMessage("信息加载中，请稍后...");
		//查询过程能被中断			
		pd.setCancelable(true);
		pd.show();
		fdAy_htmlWv.setWebChromeClient(new WebChromeClient() {
		   public void onProgressChanged(WebView view, int progress) {
		     // Activities and WebViews measure progress with different scales.
		     // The progress meter will automatically disappear when we reach 100%			 
			 System.out.println("progress:"+progress);
		     if(progress>=100)
		     {
		    	 pd.dismiss();
		     }
		   }
		 });
		fdAy_htmlWv.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(FoodDetailActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		     pd.dismiss();
		   }
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				pd.dismiss();
			}
		   
		 });
		
		fdAy_htmlWv.loadUrl(AppConstant.UrlStrs.URL_FOOD_DETAIL+"?id="+foodId);
	}

}
