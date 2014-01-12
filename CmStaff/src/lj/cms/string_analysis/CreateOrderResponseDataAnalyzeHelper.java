package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;

public class CreateOrderResponseDataAnalyzeHelper extends AnalyzeHelper {

	public int recode=0;
	public String remsg="�ɹ�";
	public long orderId=0;
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
				else if(tagName.equals("orderInfo"))
				{//���ض�����Ϣ
//					if(this.recode==0)
//					{
						jrd.beginObject();
						while(jrd.hasNext())
						{
							tagName=jrd.nextName();
							System.out.print(tagName+":");
							if(tagName.equals("id"))
							{//����ID
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
							{//����ID
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
