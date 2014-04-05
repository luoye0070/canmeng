package lj.cms.internet;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.constant.AppConstant;
public class ParamCollect extends ParamCollectAbstract implements Cloneable {
	
	private HashMap<String, Integer> paramIndexs=null;
//	private ArrayList<HashMap<String, String>> paramList=null;
//	public ArrayList<HashMap<String, String>> getParamList() {
//		return paramList;
//	}
	
	public ParamCollect() {
		super();		
		paramIndexs=new HashMap<String, Integer>();
		paramList=new ArrayList<HashMap<String,String>>();
		paramIndexs.put(AppConstant.HttpRequestParamName.ANDROID_PHONE_FIELD_KEY, paramList.size());
		HashMap<String, String> paramHash=new HashMap<String, String>();
		paramHash.put(AppConstant.HttpParamRe.PARAM_NAME, AppConstant.HttpRequestParamName.ANDROID_PHONE_FIELD_KEY);
		paramHash.put(AppConstant.HttpParamRe.PARAM_VALUE, "true");
		paramList.add(paramHash);
	}
	
	/***********************************
	 *��ӻ��������ò����б�
	 *@param name,������
	 *@param value,����ֵ 
	 **************************************/
	public void addOrSetParam(String name,String value){
		if(paramList==null)
		{
			paramList=new ArrayList<HashMap<String,String>>();
			paramIndexs.put(AppConstant.HttpRequestParamName.ANDROID_PHONE_FIELD_KEY, paramList.size());
			HashMap<String, String> paramHash=new HashMap<String, String>();
			paramHash.put(AppConstant.HttpParamRe.PARAM_NAME, AppConstant.HttpRequestParamName.ANDROID_PHONE_FIELD_KEY);
			paramHash.put(AppConstant.HttpParamRe.PARAM_VALUE, "true");
			paramList.add(paramHash);			
		}
		if(paramIndexs.get(name)==null)
		{
			paramIndexs.put(name, paramList.size());
			HashMap<String, String> paramHash=new HashMap<String, String>();
			paramHash.put(AppConstant.HttpParamRe.PARAM_NAME, name);
			paramHash.put(AppConstant.HttpParamRe.PARAM_VALUE, value);
			paramList.add(paramHash);
		}
		else
		{
			int paramIdx=paramIndexs.get(name);
			paramList.get(paramIdx).put(AppConstant.HttpParamRe.PARAM_VALUE, value);
		}
	}
	/******************************
	 *�Ƴ�һ������,���ָ���Ĳ�����������ֱ�ӷ���
	 *@param name,��Ҫ�Ƴ��Ĳ��������� 
	 ********************************/
	public void removeParam(String name){
		if(paramList==null||paramList.size()==0)
		{
			return;
		}
		if(paramIndexs.get(name)==null)
		{
			return;
		}
		else
		{
			int paramIdx=paramIndexs.get(name);
			paramList.remove(paramIdx);
			//�ؽ�����
			paramIndexs=new HashMap<String, Integer>();
			for(int i=0;i<paramList.size();i++)
			{
				paramIndexs.put(paramList.get(i).get(AppConstant.HttpParamRe.PARAM_NAME), i);
			}
		}
		
	}
	/**************************
	 *��� 
	 **************************/
	public ParamCollect clone()
    {
		ParamCollect o=null;
       try
        {
        o=(ParamCollect)super.clone();//Object�е�clone()ʶ�����Ҫ���Ƶ�����һ������
        o.paramIndexs=(HashMap<String, Integer>) this.paramIndexs.clone();
        o.paramList=(ArrayList<HashMap<String, String>>) this.paramList.clone();
        }
       catch(CloneNotSupportedException e)
        {
            System.out.println(e.toString());
        }
       return o;
    }

}
