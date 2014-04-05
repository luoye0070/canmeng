package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import com.google.gson.stream.JsonReader;

import lj.cms.model.TableInfo;

public class DataOfTablesAnalyzeHelper extends AnalyzeHelper {
	public int totalCount=0;
	public ArrayList<TableInfo> tableList=null;
	private int cnuCount=0; 
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
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
				else if(tagName.equals("tableList")){
//					if(this.recode==0)
//					{
						this.tableList=new ArrayList<TableInfo>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
							jrd.beginObject();
							boolean canUse=true;
							while(jrd.hasNext()){
								tagName=jrd.nextName();
								System.out.print(tagName+":");								
								if(tagName.equals("canUse"))
								{//
									try{
										boolean b_b=jrd.nextBoolean();
										System.out.println(b_b);
										canUse=b_b;
										if(!canUse){
											cnuCount++;
											this.totalCount-=this.cnuCount;
										}
									}
									catch(IllegalStateException e)
									{
										e.printStackTrace();
										jrd.nextNull();
									}
								}
								else if(canUse&&tagName.equals("tableInfo"))
								{//
								TableInfo tableInfo=new TableInfo();
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
											tableInfo.id=id;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("restaurantId"))
									{//
										try{
											long id=jrd.nextLong();
											System.out.println(id);
											tableInfo.restaurantId=id;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("name"))
									{//名称
										try{
											String str=jrd.nextString();
											System.out.println(str);
											tableInfo.name=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("minPeople"))
									{//
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											tableInfo.minPeople=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("maxPeople"))
									{//
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											tableInfo.maxPeople=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}

									else if(tagName.equals("canMultiOrder"))
									{//
										try{
											boolean b_b=jrd.nextBoolean();
											System.out.println(b_b);
											tableInfo.canMultiOrder=b_b;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("canReserve"))
									{//
										try{
											boolean b_b=jrd.nextBoolean();
											System.out.println(b_b);
											tableInfo.canReserve=b_b;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("description"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											tableInfo.description=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("enabled"))
									{//
										try{
											boolean b_b=jrd.nextBoolean();
											System.out.println(b_b);
											tableInfo.enabled=b_b;
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
								this.tableList.add(tableInfo);
								}
								else
								{
									jrd.skipValue();
								}
							}
							jrd.endObject();
						}
						jrd.endArray();
//					}					
				}
				else if(tagName.equals("totalCount")){
					try{
						int i_i=jrd.nextInt();
						System.out.println(i_i);
						this.totalCount=i_i;
						this.totalCount-=this.cnuCount;
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
