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
	 * 将对象序列化到文件
	 * @param obj,需要序列化的对象
	 * @param dirName,文件目录
	 * @param fileName,文件名
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
	 *从文件反序列化一个对象
	 * @param dirName,文件目录
	 * @param fileName,文件名
	 * @return 成功时，返回反序列化生成的对象；失败时，返回null
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
