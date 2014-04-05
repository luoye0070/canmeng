package lj.cms.internet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lj.cms.constant.AppConstant;
import lj.cms.filerw.FileReadWrite;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HttpConnectionHelper {
	private String urlStr;
	private int resultCode;//��������������,0�ɹ�,1�������2��������
	private ArrayList<HashMap<String, String>> paramList;
	private int outtime;
	
	public HttpConnectionHelper(){resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;outtime=120000;}
	public HttpConnectionHelper(String urlStr,
			ArrayList<HashMap<String, String>> paramList) {
		super();
		this.urlStr = urlStr;
		this.paramList = paramList;
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		this.outtime=120000;
	}
	
	public int getOuttime() {
		return outtime;
	}
	public void setOuttime(int outtime) {
		this.outtime = outtime;
	}
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public ArrayList<HashMap<String, String>> getParamList() {
		return paramList;
	}
	public void setParamList(ArrayList<HashMap<String, String>> paramList) {
		this.paramList = paramList;
	}
	/**************************************
	 *�ӷ�������ȡ��Ӧ����
	 *@return ���ط��������ص����� 
	 **********************************/
	public String getResponseStr()
	{
		System.out.println(this.urlStr);
		StringBuilder sb=new StringBuilder("");
		HttpURLConnection httpConn=null;
		BufferedReader br=null;
		try {
			URL url=new URL(urlStr);
			httpConn=(HttpURLConnection) url.openConnection();
			//this.outtime=10000;
			httpConn.setReadTimeout(this.outtime);//���ó�ʱʱ��Ϊ5��
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//��������
				String paramsStr="";
				for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
					HashMap<String, String> paramHash = (HashMap<String, String>) iterator.next();
					paramsStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}
				OutputStream out=httpConn.getOutputStream();
				out.write(paramsStr.getBytes());
				out.flush();
				out.close();
			}
			
			//���շ�������
			br=new BufferedReader(new InputStreamReader(httpConn.getInputStream()),1024*512);
			
			//��ȡ��Ӧ��
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//�κδ��󶼶���Ϊ��������
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}
			
			//��ȡ����
			String line=null;
			while((line=br.readLine())!=null)
			{
				System.out.println(line);
				sb.append(line);
			}
			
		}
		catch(FileNotFoundException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_FILENOTFOUND;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch(IOException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		finally
		{
			if(httpConn!=null)
			{
				httpConn.disconnect();
			}
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
		
		return sb.toString();
	}
	/**************************************
	 *�ӷ�������ȡ��Ӧ����
	 *@param urlStr,����ĵ�ַ
	 *@param paramList,�����б�
	 *@return ���ط��������ص����� 
	 **********************************/
	public String getResponseStr(String urlStr,
			ArrayList<HashMap<String, String>> paramList) {
		this.urlStr = urlStr;
		this.paramList = paramList;
		return getResponseStr();
	}
	
	/*************************************************************
	 * ����ͼƬ�ļ�
	 * @param fileName,�ļ���
	 * @param dirName,Ŀ¼��
	 * @return ���ش������
	 **************************************************************/
	public int downloadImg(String fileName,String dirName)
	{
		System.out.println(this.urlStr);
		//StringBuilder sb=new StringBuilder("");
		InputStream inputStream=null;
		HttpURLConnection httpConn=null;

		try {
			URL url=new URL(urlStr);
			httpConn=(HttpURLConnection) url.openConnection();
			httpConn.setReadTimeout(this.outtime);//���ó�ʱʱ��Ϊ5��
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//��������
				String paramsStr="";
				for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
					HashMap<String, String> paramHash = (HashMap<String, String>) iterator.next();
					paramsStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}
				OutputStream out=httpConn.getOutputStream();
				out.write(paramsStr.getBytes());
				out.flush();
				out.close();
			}
			
			//���շ�������
			inputStream=httpConn.getInputStream();
			//��������д���ļ�
			FileReadWrite frw=new FileReadWrite();
			this.resultCode=frw.writeFile(inputStream, fileName, dirName);
			
			//��ȡ��Ӧ��
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//�κδ��󶼶���Ϊ��������
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}
			
		}
		catch(FileNotFoundException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_FILENOTFOUND;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch(IOException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		finally
		{
			try {
				if(inputStream!=null)
				{
					inputStream.close();	
				}
				if(httpConn!=null)
				{
					httpConn.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this.resultCode;
	}
	
	/*************************************************************
	 * ����ͼƬ�ļ�
	 * @param urlStr,����ĵ�ַ
	 * @param paramList,�����б�
	 * @param fileName,�ļ���
	 * @param dirName,Ŀ¼��
	 * @return ���ش������
	 **************************************************************/
	public int downloadImg(String urlStr,
			ArrayList<HashMap<String, String>> paramList,String fileName,String dirName)
	{
		this.urlStr = urlStr;
		this.paramList = paramList;
		return downloadImg(fileName,dirName);
	}
	/*************************************************************
	 * �����ļ�
	 * @param fileName,�ļ���
	 * @param dirName,Ŀ¼��
	 * @param pd,���ȶԻ���
	 * @return ���ش������
	 **************************************************************/
	public int downloadUpdateFile(String fileName,String dirName,ProgressDialog pd)
	{
		System.out.println(this.urlStr);
		//StringBuilder sb=new StringBuilder("");
		InputStream inputStream=null;
		HttpURLConnection httpConn=null;

		try {
			URL url=new URL(urlStr);
			httpConn=(HttpURLConnection) url.openConnection();
			httpConn.setReadTimeout(this.outtime);//���ó�ʱʱ��Ϊ5��
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//��������
				String paramsStr="";
				for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
					HashMap<String, String> paramHash = (HashMap<String, String>) iterator.next();
					paramsStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}
				OutputStream out=httpConn.getOutputStream();
				out.write(paramsStr.getBytes());
				out.flush();
				out.close();
			}
			
			//���շ�������
			inputStream=httpConn.getInputStream();
			
			int fileSize=httpConn.getContentLength();
			pd.setMax(fileSize);
			System.out.println(fileSize);
			//��������д���ļ�
			FileReadWrite frw=new FileReadWrite();
			this.resultCode=frw.writeFile(inputStream, fileName, dirName,pd);
			
			//��ȡ��Ӧ��
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//�κδ��󶼶���Ϊ��������
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}
			
		}
		catch(FileNotFoundException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_FILENOTFOUND;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch(IOException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		finally
		{
			try {
				if(inputStream!=null)
				{
					inputStream.close();	
				}
				if(httpConn!=null)
				{
					httpConn.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this.resultCode;
	}
	/*************************************************************
	 * ����ͼƬ�ļ�
	 * @param urlStr,����ĵ�ַ
	 * @param paramList,�����б�
	 * @param fileName,�ļ���
	 * @param dirName,Ŀ¼��
	 * @param pd,���ȶԻ���
	 * @return ���ش������
	 **************************************************************/
	public int downloadUpdateFile(String urlStr,
			ArrayList<HashMap<String, String>> paramList,String fileName,String dirName,ProgressDialog pd)
	{
		this.urlStr = urlStr;
		this.paramList = paramList;
		return downloadUpdateFile(fileName,dirName,pd);
	}
	
	/**************************************
	 *�ӷ�������ȡ��Ӧ����
	 *@param a,���������Activity
	 *@return ���ط��������ص����� 
	 **********************************/
	public String getResponseStrAndCookie(Activity a)
	{
		System.out.println(this.urlStr);
		StringBuilder sb=new StringBuilder("");
		HttpURLConnection httpConn=null;
		BufferedReader br=null;
		try {
			URL url=new URL(urlStr);
			httpConn=(HttpURLConnection) url.openConnection();
			//this.outtime=10000;
			httpConn.setReadTimeout(this.outtime);//���ó�ʱʱ��Ϊ5��
			//HttpURLConnection.setFollowRedirects(true);
			// ��ȡ SharedPreferences 
			SharedPreferences preferences = a.getSharedPreferences("cookies", Activity.MODE_PRIVATE);  
			String cookieStr=preferences.getString("cookie_str", "");
			if(!cookieStr.isEmpty())
			{//����Cookie
				httpConn.setRequestProperty("Cookie", cookieStr);
			}
			
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//��������
				String paramsStr="";
				for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
					HashMap<String, String> paramHash = (HashMap<String, String>) iterator.next();
					paramsStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}
				OutputStream out=httpConn.getOutputStream();
				out.write(paramsStr.getBytes());
				out.flush();
				out.close();
			}
			
			//���շ�������
			br=new BufferedReader(new InputStreamReader(httpConn.getInputStream()),1024*512);
			
			//��ȡ��Ӧ��
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//�κδ��󶼶���Ϊ��������
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}
			
			//��ȡcookie
			String reCookieStr=httpConn.getHeaderField("set-cookie");
			if(reCookieStr!=null&&reCookieStr.length()>0)
			{
				//����cookie
				Editor editor=preferences.edit();
				editor.putString("cookie_str", reCookieStr);
				editor.commit();
			}
			
			//��ȡ����
			String line=null;
			while((line=br.readLine())!=null)
			{
				System.out.println(line);
				sb.append(line);
			}
			
		}
		catch(FileNotFoundException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_FILENOTFOUND;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch(IOException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		finally
		{
			if(httpConn!=null)
			{
				httpConn.disconnect();
			}
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
		
		return sb.toString();
	}
	/**************************************
	 *�ӷ�������ȡ��Ӧ����
	 *@param urlStr,����ĵ�ַ
	 *@param paramList,�����б�
	 *@param a,���������Activity
	 *@return ���ط��������ص����� 
	 **********************************/
	public String getResponseStrAndCookie(String urlStr,
			ArrayList<HashMap<String, String>> paramList,Activity a) {
		this.urlStr = urlStr;
		this.paramList = paramList;
		return getResponseStrAndCookie(a);
	}
	
	
	
	/**************************************
	 *�ӷ�������ȡ��Ӧ�����ֽ���
	 *@return ���ط��������ص����� 
	 **********************************/
	public long getResponseSize()
	{
		System.out.println(this.urlStr);
		long responseSize=0;
		HttpURLConnection httpConn=null;
		try {
			URL url=new URL(urlStr);
			httpConn=(HttpURLConnection) url.openConnection();
			//this.outtime=10000;
			httpConn.setReadTimeout(this.outtime);//���ó�ʱʱ��Ϊ5��
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//��������
				String paramsStr="";
				for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
					HashMap<String, String> paramHash = (HashMap<String, String>) iterator.next();
					paramsStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}
				OutputStream out=httpConn.getOutputStream();
				out.write(paramsStr.getBytes());
				out.flush();
				out.close();
			}
			
			//���շ���������
			responseSize=httpConn.getContentLength();
			
			//��ȡ��Ӧ��
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//�κδ��󶼶���Ϊ��������
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}		
		}
		catch(FileNotFoundException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_FILENOTFOUND;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch(IOException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		finally
		{
			if(httpConn!=null)
			{
				httpConn.disconnect();
			}
		}
		
		return responseSize;
	}
	/**************************************
	 *�ӷ�������ȡ��Ӧ�����ֽ���
	 *@param urlStr,����ĵ�ַ
	 *@param paramList,�����б�
	 *@return ���ط��������ص����� �ֽ���
	 **********************************/
	public long getResponseSize(String urlStr,
			ArrayList<HashMap<String, String>> paramList) {
		this.urlStr = urlStr;
		this.paramList = paramList;
		return getResponseSize();
	}
		
	/*************************************************************
	 * ����ͼƬ�ļ�
	 * @param fileName,�ļ���
	 * @param dirName,Ŀ¼��
	 * @param newWrite,ͼƬ�����Ƿ���������
	 * @return ���ش������
	 **************************************************************/
	public int downloadImg(String fileName,String dirName,boolean newWrite)
	{
		System.out.println(this.urlStr);
		//StringBuilder sb=new StringBuilder("");
		InputStream inputStream=null;
		HttpURLConnection httpConn=null;

		try {
			URL url=new URL(urlStr);
			httpConn=(HttpURLConnection) url.openConnection();
			httpConn.setReadTimeout(this.outtime);//���ó�ʱʱ��Ϊ5��
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//��������
				String paramsStr="";
				for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
					HashMap<String, String> paramHash = (HashMap<String, String>) iterator.next();
					paramsStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}
				OutputStream out=httpConn.getOutputStream();
				out.write(paramsStr.getBytes());
				out.flush();
				out.close();
			}
			
			//���շ�������
			inputStream=httpConn.getInputStream();
			//��������д���ļ�
			FileReadWrite frw=new FileReadWrite();
			//this.resultCode=frw.writeFile(inputStream, fileName, dirName);
			this.resultCode=frw.writeFile(inputStream, fileName, dirName,newWrite);
			
			//��ȡ��Ӧ��
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//�κδ��󶼶���Ϊ��������
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}
			
		}
		catch(FileNotFoundException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_FILENOTFOUND;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch(IOException e)
		{
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			//this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e.printStackTrace();
		}
		finally
		{
			try {
				if(inputStream!=null)
				{
					inputStream.close();	
				}
				if(httpConn!=null)
				{
					httpConn.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this.resultCode;
	}
	
	/*************************************************************
	 * ����ͼƬ�ļ�
	 * @param urlStr,����ĵ�ַ
	 * @param paramList,�����б�
	 * @param fileName,�ļ���
	 * @param dirName,Ŀ¼��
	 * @param newWrite,ͼƬ�����Ƿ���������
	 * @return ���ش������
	 **************************************************************/
	public int downloadImg(String urlStr,
			ArrayList<HashMap<String, String>> paramList,String fileName,String dirName,boolean newWrite)
	{
		this.urlStr = urlStr;
		this.paramList = paramList;
		return downloadImg(fileName,dirName,newWrite);
	}
}
