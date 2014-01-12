package lj.cmc.string_analysis;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;

public class SubmitResponseDataAnalyzeHelper extends AnalyzeHelper {
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
		/* ��ʼ���� */
		System.out.println("dataStr:" + dataStr);
		JsonReader jrd = new JsonReader(new StringReader(dataStr));
		try {
			jrd.beginObject();
			while (jrd.hasNext()) {
				String tagName = jrd.nextName();
				System.out.println(tagName + ":");
				if (tagName.equals("recode")) {// ���صĽ����
					jrd.beginObject();
					while (jrd.hasNext()) {
						tagName = jrd.nextName();
						System.out.print(tagName + ":");
						if (tagName.equals("code")) {// ���صĽ����
							try {
								int i_rd = jrd.nextInt();
								System.out.println(i_rd);
								this.recode = i_rd;
							} catch (IllegalStateException e) {
								e.printStackTrace();
								jrd.nextNull();
							}
						} else if (tagName.equals("label")) {// ���صĽ��
							try {
								String str = jrd.nextString();
								System.out.println(str);
								this.remsg = str;
							} catch (IllegalStateException e) {
								e.printStackTrace();
								jrd.nextNull();
							}
						} else {
							jrd.skipValue();
						}
					}
					jrd.endObject();
				} else {
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
