package lj.cmc.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import lj.cmc.filerw.FileReadWrite;


public class ObjectSerialize {
	/********************
	 * ���������л����ļ�
	 * @param obj,��Ҫ���л��Ķ���
	 * @param dirName,�ļ�Ŀ¼
	 * @param fileName,�ļ���
	 **********************/
	public static void writeToFile(Object obj,String dirName,String fileName)
	{
		FileReadWrite frw=new FileReadWrite();
		String filePath=frw.getSDCardRoot()+dirName+fileName;
		ObjectOutputStream oos=null;
		try {
			oos=new ObjectOutputStream(new FileOutputStream(filePath, false));
			oos.writeObject(obj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(oos!=null)
			{
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/*************************
	 *���ļ������л�һ������
	 * @param dirName,�ļ�Ŀ¼
	 * @param fileName,�ļ���
	 * @return �ɹ�ʱ�����ط����л����ɵĶ���ʧ��ʱ������null
	 ***************************/
	public static Object readFromFile(String dirName,String fileName)
	{
		Object obj=null;
		FileReadWrite frw=new FileReadWrite();
		String filePath=frw.getSDCardRoot()+dirName+fileName;
		ObjectInputStream ois=null;	
		try {
			ois=new ObjectInputStream(new FileInputStream(filePath));
			try {
				obj=ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ois!=null)
			{
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return obj;
	}
}
