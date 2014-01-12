package lj.cms.internet;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.constant.AppConstant;
/********���Դ��ڶ��ͬ�������Ĳ�������******/
public class ParamsCollect extends ParamCollectAbstract implements Cloneable {
	
	private HashMap<String, int[]> paramIndexs=null;
//	private ArrayList<HashMap<String, String>> paramList=null;
//	public ArrayList<HashMap<String, String>> getParamList() {
//		return paramList;
//	}
	
	public ParamsCollect() {
		super();
		paramIndexs=new HashMap<String, int[]>();
		paramList=new ArrayList<HashMap<String,String>>();
		paramIndexs.put(AppConstant.HttpRequestParamName.ANDROID_PHONE_FIELD_KEY, new int[]{paramList.size()});
		HashMap<String, String> paramHash=new HashMap<String, String>();
		paramHash.put(AppConstant.HttpParamRe.PARAM_NAME, AppConstant.HttpRequestParamName.ANDROID_PHONE_FIELD_KEY);
		paramHash.put(AppConstant.HttpParamRe.PARAM_VALUE, "true");
		paramList.add(paramHash);
	}
	
	/***********************************
	 *�������ò����б�
	 *@param name,������
	 *@param value,����ֵ 
	 *@param idx,ͬ�������е�����,���idxС��0�������������е�
	 **************************************/
	public void setParam(String name,String value,int idx){
		if(paramIndexs.get(name)!=null)
		{
			int[] paramIdxs=paramIndexs.get(name);
			if(idx<0){
				for(int i=0;i<paramIdxs.length;i++){
					paramList.get(paramIdxs[i]).put(AppConstant.HttpParamRe.PARAM_VALUE, value);
				}
			}
			else if(paramIdxs.length>idx){
				paramList.get(paramIdxs[idx]).put(AppConstant.HttpParamRe.PARAM_VALUE, value);
			}
		}		
	}
	/***********************************
	 *��Ӳ����б�
	 *@param name,������
	 *@param value,����ֵ 
	 **************************************/
	public void addParam(String name,String value){
		int[] paramIdxs=paramIndexs.get(name);
		if(paramIdxs==null){
			paramIndexs.put(name, new int[]{paramList.size()});				
		}else{
			int[] paramIdxs1=new int[paramIdxs.length+1];
			for(int i=0;i<paramIdxs.length;i++){
				paramIdxs1[i]=paramIdxs[i];
			}
			paramIdxs1[paramIdxs.length]=paramList.size();
			paramIndexs.put(name, paramIdxs1);
		}
		HashMap<String, String> paramHash=new HashMap<String, String>();
		paramHash.put(AppConstant.HttpParamRe.PARAM_NAME, name);
		paramHash.put(AppConstant.HttpParamRe.PARAM_VALUE, value);
		paramList.add(paramHash);		
	}
	/******************************
	 *�Ƴ�һ������,���ָ���Ĳ�����������ֱ�ӷ���
	 *@param name,��Ҫ�Ƴ��Ĳ��������� 
	 *@param idx,ͬ�������е�����,���idxС��0���Ƴ����е�
	 ********************************/
	public void removeParam(String name,int idx){
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
			int[] paramIdxs=paramIndexs.get(name);
			if(idx<0){
				for(int i=0;i<paramIdxs.length;i++){
					paramList.remove(paramIdxs[i]);
				}
			}
			else if(paramIdxs.length>idx){
				paramList.remove(paramIdxs[idx]);
			}
			else{//�������������Χ��ֱ�ӷ���
				return;
			}
			//�ؽ�����
			paramIndexs=new HashMap<String, int[]>();
			for(int i=0;i<paramList.size();i++)
			{
				String nameTemp=paramList.get(i).get(AppConstant.HttpParamRe.PARAM_NAME);
				int[] paramIdxsTemp=paramIndexs.get(nameTemp);
				if(paramIdxsTemp==null){
					paramIndexs.put(nameTemp, new int[]{i});				
				}else{
					int[] paramIdxsTemp1=new int[paramIdxsTemp.length+1];
					for(int j=0;j<paramIdxsTemp.length;j++){
						paramIdxsTemp1[j]=paramIdxsTemp[j];
					}
					paramIdxsTemp1[paramIdxsTemp.length]=i;
					paramIndexs.put(nameTemp, paramIdxsTemp1);
				}
			}
		}
		
	}
	/**************************
	 *��� 
	 **************************/
	public ParamsCollect clone()
    {
		ParamsCollect o=null;
       try
        {
        o=(ParamsCollect)super.clone();//Object�е�clone()ʶ�����Ҫ���Ƶ�����һ������
        o.paramIndexs=(HashMap<String, int[]>) this.paramIndexs.clone();
        o.paramList=(ArrayList<HashMap<String, String>>) this.paramList.clone();
        }
       catch(CloneNotSupportedException e)
        {
            System.out.println(e.toString());
        }
       return o;
    }

}
