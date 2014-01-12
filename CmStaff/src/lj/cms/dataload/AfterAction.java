package lj.cms.dataload;

import lj.cms.constant.AppConstant;
import lj.cms.model.ServerReturnInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.R;
import android.app.Activity;
import android.widget.Toast;

public abstract class AfterAction {
	
	protected ServerReturnInfo reInfo=null;
	public AnalyzeHelper analyzeHelper=null;
	public Activity a=null;
	public ServerReturnInfo getReInfo() {
		return reInfo;
	}
	public void setReInfo(ServerReturnInfo reInfo) {
		this.reInfo = reInfo;
	}
//	public AfterAction() {
//		super();
//		this.reInfo = new ServerReturnInfo();
//	}
	public AnalyzeHelper getAnalyzeHelper() {
		return analyzeHelper;
	}
	public void setAnalyzeHelper(AnalyzeHelper analyzeHelper) {
		this.analyzeHelper = analyzeHelper;
	}

	public AfterAction(AnalyzeHelper analyzeHelper,Activity a) {
		super();
		this.reInfo = new ServerReturnInfo();
		this.analyzeHelper = analyzeHelper;
		this.a=a;
	}
	/**************************************************
	 *设置访问网络结果 
	 *@param reCode,返回码
	 *@param reMsg,返回信息
	 *************************************************/
	public void setReInfo(int reCode,String reMsg){
		this.reInfo.setReturnCode(reCode);
		this.reInfo.setReturnMsg(reMsg);
	}
	
	protected boolean isServerReturnOK()
	{
		switch(reInfo.getReturnCode())
		{
		case AppConstant.VisitServerResultCode.RESULT_CODE_OK:
			return true;
		case AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR:
			Toast.makeText(a, a.getString(R.string.urlErr_networkErrTxt), Toast.LENGTH_SHORT).show();
			break;
		case AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR:
			Toast.makeText(a, a.getString(R.string.urlErr_otherErrTxt), Toast.LENGTH_SHORT).show();
			break;
		case AppConstant.VisitServerResultCode.RESULT_CODE_STOPBYUSER:
			Toast.makeText(a, a.getString(R.string.urlErr_stopByUser), Toast.LENGTH_SHORT).show();	
			break;
		case AppConstant.VisitServerResultCode.RESULT_CODE_SERVERERRROR:
			Toast.makeText(a, a.getString(R.string.urlErr_serverErrTxt), Toast.LENGTH_SHORT).show();		
			break;
		case AppConstant.VisitServerResultCode.RESULT_CODE_FILENOTFOUND:
			Toast.makeText(a, a.getString(R.string.urlErr_fileNotFoundTxt), Toast.LENGTH_SHORT).show();		
			break;
		}
		return false;
	}
	/*************************************
	 *从网络获取数据后进行的动作 
	 ***********************************/
	public abstract void doSome();
	
//	/*************************************
//	 *从网络获取数据成功后进行的动作 
//	 ***********************************/
//	public abstract void okDoSome();
//	
//	/*************************************
//	 *从网络获取数据失败后进行的动作 
//	 ***********************************/
//	public abstract void flDoSome();
}
