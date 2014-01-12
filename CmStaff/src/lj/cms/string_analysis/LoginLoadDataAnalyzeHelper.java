package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import lj.cms.model.UserInfo;

import com.google.gson.stream.JsonReader;


public class LoginLoadDataAnalyzeHelper extends AnalyzeHelper {

	public int recode=0;
	public String remsg="�ɹ�";
//	public long userId=0;
//	public String userName="";
//	public String passWord="";
	public UserInfo userInfo;
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
		/*��ʼ����*/
		System.out.println("dataStr:"+dataStr);	
		JsonReader jrd=new JsonReader(new StringReader(dataStr));
		try {
			jrd.beginObject();
			while(jrd.hasNext())
			{				
				String tagName=jrd.nextName();
				System.out.println(tagName+":");
				if(tagName.equals("recode"))
				{//���صĽ����
					jrd.beginObject();
					while(jrd.hasNext())
					{
						tagName=jrd.nextName();
						System.out.print(tagName+":");
						if(tagName.equals("code")){
							try{
								int i_rd=jrd.nextInt();
								System.out.println(i_rd);
								this.recode=i_rd;
							}
							catch(IllegalStateException e)
							{
								e.printStackTrace();
								jrd.nextNull();
							}
						}
						else if(tagName.equals("label")){
							try{
								String str=jrd.nextString();
								System.out.println(str);
								this.remsg=str;
							}
							catch(IllegalStateException e)
							{
								e.printStackTrace();
								jrd.nextNull();
							}
						}
						else{
							jrd.skipValue();
						}
					}
					jrd.endObject();
				}
				else if(tagName.equals("staffInfo"))
				{//���ص��û���Ϣ
					if(this.recode==0)
					{
						this.userInfo=new UserInfo();
						jrd.beginObject();
						while(jrd.hasNext())
						{
							tagName=jrd.nextName();
							System.out.print(tagName+":");
							if(tagName.equals("id"))
							{//������Ʒ�б�
								try{
									long id=jrd.nextLong();
									System.out.println(id);
									this.userInfo.userId=id;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("loginName"))
							{//�����û���
								try{
									String str=jrd.nextString();
									System.out.println(str);
									this.userInfo.username=str;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("restaurantId"))
							{//�����û���
								try{
									String str=jrd.nextString();
									System.out.println(str);
									this.userInfo.restaurantId=str;
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
					}
				}
				else if(tagName.equals("positionList")){
					try{
						this.userInfo.positionTypeList=new ArrayList<Integer>();
						jrd.beginArray();
						while(jrd.hasNext()){
							jrd.beginObject();
							while(jrd.hasNext()){
								tagName=jrd.nextName();
								System.out.print(tagName+":");
								if(tagName.equals("positionType"))
								{//������Ʒ�б�
									try{
										int i_i=jrd.nextInt();
										System.out.println(i_i);
										this.userInfo.positionTypeList.add(i_i);
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
						}
						jrd.endArray();
					}
					catch(IllegalStateException e){
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else if(tagName.equals("originalPassWord")){
					try{
						String str=jrd.nextString();
						System.out.println(str);
						this.userInfo.password=str;
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
