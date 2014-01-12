package lj.cmc.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;


import lj.cmc.model.ReserveRMsg;

public class DataOfReserveResponseAnalyzeHelper extends AnalyzeHelper {
	public ArrayList<ReserveRMsg> okList=null;
	public ArrayList<ReserveRMsg> errorList=null;
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
		System.out.println("开始解析数据："+dataStr);
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
				else if(tagName.equals("errorList")){
					try{
						this.errorList=new ArrayList<ReserveRMsg>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
							ReserveRMsg reserveRMsg=new ReserveRMsg();
							jrd.beginObject();
							while(jrd.hasNext())
							{
								tagName=jrd.nextName();
								System.out.print(tagName+":");
								if(tagName.equals("tableId"))
								{//tableID
									try{
										long l_l=jrd.nextLong();
										System.out.println(l_l);
										reserveRMsg.tableId=l_l;
									}
									catch(IllegalStateException e)
									{
										e.printStackTrace();
										jrd.nextNull();
									}
								}
								else if(tagName.equals("recode"))
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
												reserveRMsg.code=i_rd;
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
												reserveRMsg.label=str;
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
								else
								{
									jrd.skipValue();
								}
							}
							jrd.endObject();
							this.errorList.add(reserveRMsg);
						}
						jrd.endArray();
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
