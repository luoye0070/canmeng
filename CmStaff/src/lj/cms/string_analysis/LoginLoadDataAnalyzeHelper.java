package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import lj.cms.model.UserInfo;

import com.google.gson.stream.JsonReader;


public class LoginLoadDataAnalyzeHelper extends AnalyzeHelper {

	public int recode=0;
	public String remsg="成功";
//	public long userId=0;
//	public String userName="";
//	public String passWord="";
	public UserInfo userInfo;
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
		/*开始解析*/
		System.out.println("dataStr:"+dataStr);	
		JsonReader jrd=new JsonReader(new StringReader(dataStr));
		try {
			jrd.beginObject();
			while(jrd.hasNext())
			{				
				String tagName=jrd.nextName();
				System.out.println(tagName+":");
				if(tagName.equals("recode"))
				{//返回的结果码
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
				{//返回的用户信息
					if(this.recode==0)
					{
						this.userInfo=new UserInfo();
						jrd.beginObject();
						while(jrd.hasNext())
						{
							tagName=jrd.nextName();
							System.out.print(tagName+":");
							if(tagName.equals("id"))
							{//解析商品列表
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
							{//解析用户名
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
							{//解析用户名
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
								{//解析商品列表
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
