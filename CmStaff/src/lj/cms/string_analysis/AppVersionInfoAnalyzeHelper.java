package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import lj.cms.model.ApplicationVersionInfo;

public class AppVersionInfoAnalyzeHelper extends AnalyzeHelper {

	public ApplicationVersionInfo appVInfo=null;
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
//		try {
//			Gson gson=new Gson();
//			this.appVInfo=gson.fromJson(dataStr, new TypeToken<ApplicationVersionInfo>(){}.getType());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		/*��ʼ����*/
		System.out.println("dataStr:"+dataStr);
		JsonReader jrd=new JsonReader(new StringReader(dataStr));
		try {
			this.appVInfo=new ApplicationVersionInfo();
			jrd.beginObject();
			while(jrd.hasNext())
			{				
				String tagName=jrd.nextName();
				System.out.print(tagName+":");
				if(tagName.equals("appName"))
				{//Ӧ������
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.appVInfo.setAppName(str);
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else if(tagName.equals("apkName"))
				{//apk����
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.appVInfo.setApkName(str);
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else if(tagName.equals("apkSize"))
				{//apk��С
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.appVInfo.setApkSize(str);
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else if(tagName.equals("verName"))
				{//�汾��
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.appVInfo.setVerName(str);
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else if(tagName.equals("verCode"))
				{//�汾��
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.appVInfo.setVerCode(str);
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else if(tagName.equals("apkUrl"))
				{//apkURL
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.appVInfo.setApkUrl(str);
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else if(tagName.equals("updateInfo"))
				{//��������
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.appVInfo.setUpdateInfo(str);
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
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
	}

}
