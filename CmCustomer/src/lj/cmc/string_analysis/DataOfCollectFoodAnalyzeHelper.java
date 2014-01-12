package lj.cmc.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lj.cmc.model.FoodCollectInfo;
import com.google.gson.stream.JsonReader;

public class DataOfCollectFoodAnalyzeHelper extends AnalyzeHelper {

	public int totalCount=0;
	public ArrayList<FoodCollectInfo> foodCollectInfoList=null;
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
				else if(tagName.equals("collectList")){
//					if(this.recode==0)
//					{
						this.foodCollectInfoList=new ArrayList<FoodCollectInfo>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
//							if(this.recode==0)
//							{
							FoodCollectInfo foodCollectInfo=new FoodCollectInfo();
								jrd.beginObject();
								while(jrd.hasNext())
								{
									tagName=jrd.nextName();
									System.out.print(tagName+":");
									if(tagName.equals("id"))
									{//ID
										try{
											long id=jrd.nextLong();
											System.out.println(id);
											foodCollectInfo.id=id;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("foodId"))
									{//菜谱ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											foodCollectInfo.foodId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("userId"))
									{//用户ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											foodCollectInfo.userId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("collectTime"))
									{//收藏时间
										try{
											Date d_d=null;
											try {
												d_d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jrd.nextString());
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											System.out.println(d_d);
											foodCollectInfo.collectTime=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}																		
									else if(tagName.equals("name"))
									{//饭店名
										try{
											String str=jrd.nextString();
											System.out.println(str);
											foodCollectInfo.name=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									
									else if(tagName.equals("image"))
									{//图片
										try{
											String str=jrd.nextString();
											System.out.println(str);
											foodCollectInfo.image=str;
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
								this.foodCollectInfoList.add(foodCollectInfo);
//							}
						}
						jrd.endArray();
//					}					
				}
				else if(tagName.equals("totalCount")){
					try{
						int i_i=jrd.nextInt();
						System.out.println(i_i);
						this.totalCount=i_i;
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
