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
	ActivityManager am=null;//activity������
	
	WebView fdAy_htmlWv=null;
	
	long foodId;
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
		setContentView(R.layout.activity_food_detail);
		
		//����һ��activity��ȡ����
		Intent preIntent=getIntent();
		foodId=preIntent.getLongExtra(AppConstant.IntentExtraName.FOOD_ID, 0);
		
		fdAy_htmlWv=(WebView) findViewById(R.id.fdAy_htmlWv);
		
		//����������
		fdAy_htmlWv.getSettings().setSupportZoom(true);
		fdAy_htmlWv.getSettings().setBuiltInZoomControls(true);
		//goodsItdetailsAy_contentWv.getSettings().setDisplayZoomControls(true);
		
		//������ִ��js
		fdAy_htmlWv.getSettings().setJavaScriptEnabled(true);
		//���ü��ع���
		final ProgressDialog pd=new ProgressDialog(this);
		//��ʾ���ȶԻ��򣬵ȴ���ѯ
		pd.setMessage("��Ϣ�����У����Ժ�...");
		//��ѯ�����ܱ��ж�			
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
