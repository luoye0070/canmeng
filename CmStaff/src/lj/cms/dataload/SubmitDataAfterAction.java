package lj.cms.dataload;

import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.constant.AppConstant;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.SubmitResponseDataAnalyzeHelper;
import lj.cms.activity.LoginActivity;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class SubmitDataAfterAction extends AfterAction {

	public SubmitDataAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
		super(analyzeHelper, a);
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(AnalyzeHelper analyzeHelper, Activity a,SubmitCallBack scb) {
		super(analyzeHelper, a);
		this.scb=scb;
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(AnalyzeHelper analyzeHelper, Activity a,SubmitCallBack scb,String successMsg) {
		super(analyzeHelper, a);
		this.scb=scb;
		this.successMsg=successMsg;
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(AnalyzeHelper analyzeHelper, Activity a,SubmitCallBack scb,boolean gotoLogin) {
		super(analyzeHelper, a);
		this.scb=scb;
		this.gotoLogin=gotoLogin;
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(AnalyzeHelper analyzeHelper, Activity a,SubmitCallBack scb,boolean gotoLogin,String successMsg) {
		super(analyzeHelper, a);
		this.scb=scb;
		this.gotoLogin=gotoLogin;
		this.successMsg=successMsg;
		// TODO Auto-generated constructor stub
	}
	
	
	public SubmitDataAfterAction(Activity a) {
		super(new SubmitResponseDataAnalyzeHelper(), a);
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(Activity a,SubmitCallBack scb) {
		super(new SubmitResponseDataAnalyzeHelper(), a);
		this.scb=scb;
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(Activity a,SubmitCallBack scb,String successMsg) {
		super(new SubmitResponseDataAnalyzeHelper(), a);
		this.scb=scb;
		this.successMsg=successMsg;
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(Activity a,SubmitCallBack scb,boolean gotoLogin) {
		super(new SubmitResponseDataAnalyzeHelper(), a);
		this.scb=scb;
		this.gotoLogin=gotoLogin;
		// TODO Auto-generated constructor stub
	}
	public SubmitDataAfterAction(Activity a,SubmitCallBack scb,boolean gotoLogin,String successMsg) {
		super(new SubmitResponseDataAnalyzeHelper(), a);
		this.scb=scb;
		this.gotoLogin=gotoLogin;
		this.successMsg=successMsg;
		// TODO Auto-generated constructor stub
	}
	private SubmitCallBack scb=null;
	private boolean gotoLogin=true;//用户没有登录是否跳转到登录界面
	public String successMsg=null;//成功后显示的信息
	@Override
	public void doSome() {
		// TODO Auto-generated method stub
		if(!isServerReturnOK())
		{
			return;
		}	
		SubmitResponseDataAnalyzeHelper analyzeHelper=(SubmitResponseDataAnalyzeHelper)this.analyzeHelper;
		if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code)
		{//用户没有登录，转到登录界面
			//Toast.makeText(MyxjmeiOrderManageActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
			if(gotoLogin){
				if(a instanceof ActivityCallBackInterface)
					AcvivityLoginGoto.setAcbi((ActivityCallBackInterface) a);
				Intent loginIntent=new Intent();
				loginIntent.setClass(a, LoginActivity.class);
				a.startActivity(loginIntent);
			}
			else{
				Toast.makeText(a, "您还没有登录，请先登录", 1).show();
			}
			return;				
		}
		else if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code)
		{//成功
			if(this.successMsg!=null)
				Toast.makeText(a, this.successMsg, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_LONG).show();
			if(scb!=null)
			{
				scb.doAfterSuccess();
			}
		}
		else
		{//其他错误
			Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_LONG).show();
			if(scb!=null)
			{
				scb.doAfterFail();
			}
			return;				
		}
	}

	public interface SubmitCallBack{
		public void doAfterSuccess();
		public void doAfterFail();
	}
}

