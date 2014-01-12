package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;

import lj.cms.model.FoodInfo;
import lj.cms.model.MessageInfo;
import lj.cms.model.OrderInfo;

public class DataOfMessageAnalyzeHelper extends AnalyzeHelper {
	public MessageInfo messageInfo;
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
				else if(tagName.equals("messageInfo"))
				{//返回订单信息
//					if(this.recode==0)
//					{
						this.messageInfo=new MessageInfo();
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
									this.messageInfo.id=id;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("orderId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.messageInfo.orderId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("type"))
							{//
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.messageInfo.type=i_i;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("status"))
							{//
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.messageInfo.status=i_i;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("receiveId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.messageInfo.receiveId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("sendId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.messageInfo.sendId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("content"))
							{//
								try{
									String str=jrd.nextString();
									System.out.println(str);
									this.messageInfo.content=str;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}	
							else if(tagName.equals("recTime"))
							{//
								try{
									String str=jrd.nextString();
									System.out.println(str);
									SimpleDateFormat sdf=new SimpleDateFormat();
									this.messageInfo.recTime=sdf.parse(str);
								}
								catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("sendType"))
							{//
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.messageInfo.sendType=i_i;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("sendTime"))
							{//
								try{
									String str=jrd.nextString();
									System.out.println(str);
									SimpleDateFormat sdf=new SimpleDateFormat();
									this.messageInfo.sendTime=sdf.parse(str);
								}
								catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
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
