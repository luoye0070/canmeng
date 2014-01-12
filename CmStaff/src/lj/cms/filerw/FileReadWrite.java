package lj.cms.filerw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileReadWrite {
	String SDCardRoot=null;//sd��·��
	public FileReadWrite()
	{
		getSDPath();
	}
	private void getSDPath()
	{
		SDCardRoot=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
	}
	
	public String getSDCardRoot() {
		return SDCardRoot;
	}
	/*************************************************************
	 *����ļ��Ƿ����
	 *************************************************************/
	public boolean isFileExist(String qfileName)
	{
		File file=new File(SDCardRoot+qfileName);
		return file.exists();
	}
	
	private File createDir(String dirName)
	{
		File file=new File(SDCardRoot+dirName);
		file.mkdirs();
		return file;
	}
	
	private File createFile(String dirName,String fileName)
	{
		File file=new File(SDCardRoot+dirName+fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(SDCardRoot+dirName+fileName);
		return file;
	}
	
	/*****************************************************
	 * ��һ������������д���ļ�
	 ******************************************************/
	public int writeFile(InputStream input,String fileName,String dirName)
	{
		int recode=0;
		File file=null;
		OutputStream output=null;
		System.out.println(dirName+fileName);
		if(!isFileExist(dirName))
		{
			createDir(dirName);
		}
		if(!isFileExist(dirName+fileName))
		{
			file=createFile(dirName, fileName);
		}
		else
		{
			return 1;//�ļ��Ѿ�����
		}
		if(file.canWrite())
		{
			try {
				output=new FileOutputStream(file);
				byte []buff=new byte[4*1024];
				int count=-1;
				while((count=input.read(buff))!=-1)
				{
					//output.write(buff);
					output.write(buff, 0, count);
					output.flush();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				recode=-2;//IO�쳣
			}
			finally
			{
				try {
					if(output!=null)
						output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			recode=-1;//�ļ�����д
		}
		return recode;//д�ļ��ɹ�
	}
	
	/***********************************************
	 * ��ȡ�ļ�������
	 **********************************************/
	public InputStream getFileInputStream(String dirName,String fileName)
	{
		InputStream input=null;
		try{
			File file=new File(SDCardRoot+dirName+fileName);
			//file.setReadable(true);
			input=new FileInputStream(file);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			
		}
		return input;
	}
	
	/****************************************************
	*��һ���ַ���д���ļ�
	****************************************************/
	public int writeFile(String data,String fileName,String dirName)
	{
		int reCode=0;
		File file=null;
		OutputStream output=null;
		BufferedWriter bw=null;
		System.out.println(dirName+fileName);
		if(!isFileExist(dirName))
		{
			createDir(dirName);
		}
		if(!isFileExist(dirName+fileName))
		{
			file=createFile(dirName, fileName);
		}
		else
		{
			file=new File(SDCardRoot+dirName+fileName);
		}
		if(file.canWrite())
		{
			try {
				output=new FileOutputStream(file,true);
				bw=new BufferedWriter(new OutputStreamWriter(output));
				bw.write(data+"\r\n");
				bw.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reCode=-1;
			}
			finally{
				if(bw!=null)
				{
					try {
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						reCode=-1;
					}
				}
			}
		}
		else
		{
			reCode=-1;
		}
		return reCode;
	}
	
	/***************************************************
	 * ���ļ��ж�ȡ���ݵ��ַ��б��У����з�
	 ***************************************************/
	public ArrayList<String> getStringArrayFromFile(String dirName,String fileName)
	{
		ArrayList<String> strList=null;
		BufferedReader br=null;
		try{
			File file=new File(SDCardRoot+dirName+fileName);
			if(file.exists())
			{
				//file.setReadable(true);
				br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				strList=new ArrayList<String>();
				String line=null;
				while((line=br.readLine())!=null)
				{
					strList.add(line);
				}
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			if(br!=null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return strList;
	}
	
	/***************************************************************
	 * ����ַ����Ƿ����ļ���
	 ***************************************************************/
	public boolean stringIsInFile(String data,String fileName,String dirName)
	{
		boolean isInFile=false;
		BufferedReader br=null;
		try{
			File file=new File(SDCardRoot+dirName+fileName);
			if(file.exists())
			{
				//file.setReadable(true);
				br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				
				String line=null;
				while((line=br.readLine())!=null)
				{
					if(line.equals(data))
					{
						isInFile=true;
						break;
					}
				}
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			if(br!=null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return isInFile;
	}
	
	/***************************************************************
	 * ��SD����ȡͼƬ
	 ***************************************************************/
	public Bitmap getImg(String path)
	{
		if(path==null||path.equals(""))
		{
			return null;
		}
		Bitmap bitmap=null;
		InputStream input=null;
		try{
			File file=new File(SDCardRoot+path);
//			if(file.length()>1024*1024)
//			{
//				return null;
//			}
			//file.setReadable(true);
			input=new FileInputStream(file);
			bitmap=BitmapFactory.decodeStream(input);
		}
		catch(IOException ex){
			ex.printStackTrace();
			bitmap=null;//����IO�쳣ʱ����һ����ͼƬ����
		}
		finally{
			
		}
		return bitmap;
	}
	
	/*****************************************************
	 * ��һ������������д���ļ�
	 ******************************************************/
	public int writeFile(InputStream input,String fileName,String dirName,ProgressDialog pd)
	{
		int recode=0;
		File file=null;
		OutputStream output=null;
		System.out.println(dirName+fileName);
		if(!isFileExist(dirName))
		{
			createDir(dirName);
		}
		if(!isFileExist(dirName+fileName))
		{
			file=createFile(dirName, fileName);
		}
		else
		{
			file=new File(SDCardRoot+dirName+fileName);//�ļ��Ѿ�����,����д�ļ�
		}
		if(file.canWrite())
		{
			try {
				output=new FileOutputStream(file,false);
				byte []buff=new byte[4*1024];
				int count=-1;
				int total=0;
				while((count=input.read(buff))!=-1)
				{
					//output.write(buff);
					output.write(buff, 0, count);
					output.flush();
					total+=count;
					pd.setProgress(total);
					System.out.println(total);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				recode=-2;//IO�쳣
			}
			finally
			{
				try {
					if(output!=null)
						output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			recode=-1;//�ļ�����д
		}
		return recode;//д�ļ��ɹ�
	}
	
	
	/*************************************************************
	 *��ȡ�ļ���С���ֽ���
	 *************************************************************/
	public long getFileSize(String qfileName)
	{
		File file=new File(SDCardRoot+qfileName);
		return file.length();
	}
	
	
	/*****************************************************
	 * ��һ������������д���ļ�
	 ******************************************************/
	public int writeFile(InputStream input,String fileName,String dirName,boolean newwrite)
	{
		int recode=0;
		File file=null;
		OutputStream output=null;
		System.out.println(dirName+fileName);
		if(!isFileExist(dirName))
		{
			createDir(dirName);
		}
		if(!isFileExist(dirName+fileName))
		{
			file=createFile(dirName, fileName);
		}
		else
		{ 
			if(newwrite)
			{
				file=new File(SDCardRoot+dirName+fileName);//�ļ��Ѿ�����,����д�ļ�	
			}
			else
			{
				return 1;//�ļ��Ѿ�����
			}
		}
		if(file.canWrite())
		{
			try {
				output=new FileOutputStream(file,false);
				byte []buff=new byte[4*1024];
				int count=-1;
				while((count=input.read(buff))!=-1)
				{
					//output.write(buff);
					output.write(buff, 0, count);
					output.flush();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				recode=-2;//IO�쳣
			}
			finally
			{
				try {
					if(output!=null)
						output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			recode=-1;//�ļ�����д
			System.out.println("ͼƬ����д");
		}
		return recode;//д�ļ��ɹ�
	}
}
