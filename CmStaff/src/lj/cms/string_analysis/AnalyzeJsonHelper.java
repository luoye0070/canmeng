package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.stream.JsonReader;

/******************************************
 * json数据解析辅助类
 *****************************************/
public class AnalyzeJsonHelper {
	
	/****************************************************
	 * 将json字符串数组解析为字符串数组
	 * @param jsonData,json字符串
	 * @return 解析得到的字符串数组，如果没有解析成功返回null
	 ***************************************************/
	public String[] getStrArrayFromJson(String jsonData)
	{
		String []strArray=null;
		ArrayList<String> strTempList=new ArrayList<String>();
		JsonReader jrd=new JsonReader(new StringReader(jsonData));
		try {
			jrd.beginArray();
			while(jrd.hasNext())
			{
				String str=jrd.nextString();
				strTempList.add(str);
				System.out.println(str);
			}
			jrd.endArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!strTempList.isEmpty())
		{
			strArray=new String[strTempList.size()];
			int i=0;
			for (Iterator<String> iterator = strTempList.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				strArray[i]=str;
				i++;
			}
		}
		return strArray;
	}
	
	/****************************************************
	 * 将json字符串数组解析为图片地址列表
	 * @param jsonData,json字符串
	 * @return 解析得到的字符串ArrayList，如果没有解析成功返回null
	 ***************************************************/
	public ArrayList<String> getImgUrlListFromJson(String jsonData)
	{
		ArrayList<String> imgList=null;
		JsonReader jrd=new JsonReader(new StringReader(jsonData));
		try {
			jrd.beginObject();
			while(jrd.hasNext())
			{
				String tagName=jrd.nextName();
				System.out.print(tagName+":");
				if(tagName.equals("imgageslist"))
				{
					imgList=new ArrayList<String>();
					jrd.beginArray();
					while(jrd.hasNext())
					{
						String str=jrd.nextString();
						imgList.add(str);
						System.out.println(str);
					}
					jrd.endArray();
				}
				else
				{
					jrd.skipValue();
				}
			}
			jrd.endObject();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgList;
	}
	
}
