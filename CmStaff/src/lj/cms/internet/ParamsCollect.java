package lj.cms.internet;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.constant.AppConstant;
/********可以存在多个同名参数的参数集合******/
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
	 *重新设置参数列表
	 *@param name,参数名
	 *@param value,参数值 
	 *@param idx,同名参数中的索引,如果idx小于0则重新设置所有的
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
	 *添加参数列表
	 *@param name,参数名
	 *@param value,参数值 
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
	 *移除一个参数,如果指定的参数不存在则直接返回
	 *@param name,需要移除的参数的名称 
	 *@param idx,同名参数中的索引,如果idx小于0则移除所有的
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
			else{//相对索引超出范围则直接返回
				return;
			}
			//重建索引
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
	 *深拷贝 
	 **************************/
	public ParamsCollect clone()
    {
		ParamsCollect o=null;
       try
        {
        o=(ParamsCollect)super.clone();//Object中的clone()识别出你要复制的是哪一个对象。
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
