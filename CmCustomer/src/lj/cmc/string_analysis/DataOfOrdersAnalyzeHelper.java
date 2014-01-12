package lj.cmc.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.stream.JsonReader;

import lj.cmc.model.FoodInfo;
import lj.cmc.model.OrderInfo;

public class DataOfOrdersAnalyzeHelper extends AnalyzeHelper {

	public ArrayList<OrderInfo> orderList=null;
	public int totalCount=0;
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
				else if(tagName.equals("orderList")){
//					if(this.recode==0)
//					{
						this.orderList=new ArrayList<OrderInfo>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
//							if(this.recode==0)
//							{
								OrderInfo orderInfo=new OrderInfo();
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
											orderInfo.id=id;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("restaurantId"))
									{//饭店ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											orderInfo.restaurantId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("tableId"))
									{//桌位ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											orderInfo.tableId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("date"))
									{//用餐日期
										try{
											Date d_d=null;
											try {
												d_d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jrd.nextString());
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											System.out.println(d_d);
											orderInfo.date=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("time"))
									{//用餐时间
										try{
											Date d_d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jrd.nextString());
											System.out.println(d_d);
											orderInfo.time=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}																		
									else if(tagName.equals("status"))
									{//状态
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											orderInfo.status=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									
									else if(tagName.equals("valid"))
									{//有效性
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											orderInfo.valid=i_i;
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
								this.orderList.add(orderInfo);
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
