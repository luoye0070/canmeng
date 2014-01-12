package lj.cmc.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import lj.cmc.model.FailedDish;

import com.google.gson.stream.JsonReader;

public class AddDishesReDataAnalyzeHelper extends AnalyzeHelper {
	public ArrayList<FailedDish> failedList=null;
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
				else if(tagName.equals("failedList"))
				{//����ʧ���б�
//					if(this.recode==0)
//					{
						this.failedList=new ArrayList<FailedDish>();
						jrd.beginArray();
						while(jrd.hasNext()){
							FailedDish failedDish=new FailedDish();
							jrd.beginObject();							
							while(jrd.hasNext())
							{
								tagName=jrd.nextName();
								System.out.print(tagName+":");
								if(tagName.equals("foodId"))
								{//��ƷID
									try{
										long l_l=jrd.nextLong();
										System.out.println(l_l);
										failedDish.foodId=l_l;
									}
									catch(IllegalStateException e)
									{
										e.printStackTrace();
										jrd.nextNull();
									}
								}
								else if(tagName.equals("msg"))
								{//ʧ��ԭ��
									try{
										String str=jrd.nextString();
										System.out.println(str);
										failedDish.msg=str;
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
							this.failedList.add(failedDish);
						}
						jrd.endArray();
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
