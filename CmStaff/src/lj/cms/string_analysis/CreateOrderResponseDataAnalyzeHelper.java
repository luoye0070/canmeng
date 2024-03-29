package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;

public class CreateOrderResponseDataAnalyzeHelper extends AnalyzeHelper {

	public int recode=0;
	public String remsg="成功";
	public long orderId=0;
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
				else if(tagName.equals("orderInfo"))
				{//返回订单信息
//					if(this.recode==0)
//					{
						jrd.beginObject();
						while(jrd.hasNext())
						{
							tagName=jrd.nextName();
							System.out.print(tagName+":");
							if(tagName.equals("id"))
							{//订单ID
								try{
									long id=jrd.nextLong();
									System.out.println(id);
									this.orderId=id;
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
									String str=jrd.nextString();
									System.out.println(str);
									
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
//					}
				}
				else if(tagName.equals("errors")){
					
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
