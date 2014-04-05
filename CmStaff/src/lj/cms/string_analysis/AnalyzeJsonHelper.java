package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.stream.JsonReader;

/******************************************
 * json���ݽ���������
 *****************************************/
public class AnalyzeJsonHelper {
	
	/****************************************************
	 * ��json�ַ����������Ϊ�ַ�������
	 * @param jsonData,json�ַ���
	 * @return �����õ����ַ������飬���û�н����ɹ�����null
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
	 * ��json�ַ����������ΪͼƬ��ַ�б�
	 * @param jsonData,json�ַ���
	 * @return �����õ����ַ���ArrayList�����û�н����ɹ�����null
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
