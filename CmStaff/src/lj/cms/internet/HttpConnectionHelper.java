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
	private int resultCode;//访问网络结果代码,0成功,1网络错误，2其他错误
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
	 *从服务器获取响应数据
	 *@return 返回服务器返回的数据 
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
			httpConn.setReadTimeout(this.outtime);//设置超时时间为5秒
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//发送数据
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
			
			//接收返回数据
			br=new BufferedReader(new InputStreamReader(httpConn.getInputStream()),1024*512);
			
			//获取响应码
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//任何错误都定义为其他错误
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}
			
			//读取数据
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
	 *从服务器获取响应数据
	 *@param urlStr,请求的地址
	 *@param paramList,参数列表
	 *@return 返回服务器返回的数据 
	 **********************************/
	public String getResponseStr(String urlStr,
			ArrayList<HashMap<String, String>> paramList) {
		this.urlStr = urlStr;
		this.paramList = paramList;
		return getResponseStr();
	}
	
	/*************************************************************
	 * 下载图片文件
	 * @param fileName,文件名
	 * @param dirName,目录名
	 * @return 返回错误代码
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
			httpConn.setReadTimeout(this.outtime);//设置超时时间为5秒
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//发送数据
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
			
			//接收返回数据
			inputStream=httpConn.getInputStream();
			//将输入流写入文件
			FileReadWrite frw=new FileReadWrite();
			this.resultCode=frw.writeFile(inputStream, fileName, dirName);
			
			//获取响应码
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//任何错误都定义为其他错误
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
	 * 下载图片文件
	 * @param urlStr,请求的地址
	 * @param paramList,参数列表
	 * @param fileName,文件名
	 * @param dirName,目录名
	 * @return 返回错误代码
	 **************************************************************/
	public int downloadImg(String urlStr,
			ArrayList<HashMap<String, String>> paramList,String fileName,String dirName)
	{
		this.urlStr = urlStr;
		this.paramList = paramList;
		return downloadImg(fileName,dirName);
	}
	/*************************************************************
	 * 下载文件
	 * @param fileName,文件名
	 * @param dirName,目录名
	 * @param pd,进度对话框
	 * @return 返回错误代码
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
			httpConn.setReadTimeout(this.outtime);//设置超时时间为5秒
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//发送数据
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
			
			//接收返回数据
			inputStream=httpConn.getInputStream();
			
			int fileSize=httpConn.getContentLength();
			pd.setMax(fileSize);
			System.out.println(fileSize);
			//将输入流写入文件
			FileReadWrite frw=new FileReadWrite();
			this.resultCode=frw.writeFile(inputStream, fileName, dirName,pd);
			
			//获取响应码
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//任何错误都定义为其他错误
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
	 * 下载图片文件
	 * @param urlStr,请求的地址
	 * @param paramList,参数列表
	 * @param fileName,文件名
	 * @param dirName,目录名
	 * @param pd,进度对话框
	 * @return 返回错误代码
	 **************************************************************/
	public int downloadUpdateFile(String urlStr,
			ArrayList<HashMap<String, String>> paramList,String fileName,String dirName,ProgressDialog pd)
	{
		this.urlStr = urlStr;
		this.paramList = paramList;
		return downloadUpdateFile(fileName,dirName,pd);
	}
	
	/**************************************
	 *从服务器获取响应数据
	 *@param a,发起请求的Activity
	 *@return 返回服务器返回的数据 
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
			httpConn.setReadTimeout(this.outtime);//设置超时时间为5秒
			//HttpURLConnection.setFollowRedirects(true);
			// 读取 SharedPreferences 
			SharedPreferences preferences = a.getSharedPreferences("cookies", Activity.MODE_PRIVATE);  
			String cookieStr=preferences.getString("cookie_str", "");
			if(!cookieStr.isEmpty())
			{//设置Cookie
				httpConn.setRequestProperty("Cookie", cookieStr);
			}
			
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//发送数据
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
			
			//接收返回数据
			br=new BufferedReader(new InputStreamReader(httpConn.getInputStream()),1024*512);
			
			//获取响应码
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//任何错误都定义为其他错误
				this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			}
			
			//获取cookie
			String reCookieStr=httpConn.getHeaderField("set-cookie");
			if(reCookieStr!=null&&reCookieStr.length()>0)
			{
				//保存cookie
				Editor editor=preferences.edit();
				editor.putString("cookie_str", reCookieStr);
				editor.commit();
			}
			
			//读取数据
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
	 *从服务器获取响应数据
	 *@param urlStr,请求的地址
	 *@param paramList,参数列表
	 *@param a,发起请求的Activity
	 *@return 返回服务器返回的数据 
	 **********************************/
	public String getResponseStrAndCookie(String urlStr,
			ArrayList<HashMap<String, String>> paramList,Activity a) {
		this.urlStr = urlStr;
		this.paramList = paramList;
		return getResponseStrAndCookie(a);
	}
	
	
	
	/**************************************
	 *从服务器获取响应数据字节数
	 *@return 返回服务器返回的数据 
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
			httpConn.setReadTimeout(this.outtime);//设置超时时间为5秒
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//发送数据
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
			
			//接收返回数据量
			responseSize=httpConn.getContentLength();
			
			//获取响应码
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//任何错误都定义为其他错误
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
	 *从服务器获取响应数据字节数
	 *@param urlStr,请求的地址
	 *@param paramList,参数列表
	 *@return 返回服务器返回的数据 字节数
	 **********************************/
	public long getResponseSize(String urlStr,
			ArrayList<HashMap<String, String>> paramList) {
		this.urlStr = urlStr;
		this.paramList = paramList;
		return getResponseSize();
	}
		
	/*************************************************************
	 * 下载图片文件
	 * @param fileName,文件名
	 * @param dirName,目录名
	 * @param newWrite,图片存在是否重新下载
	 * @return 返回错误代码
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
			httpConn.setReadTimeout(this.outtime);//设置超时时间为5秒
			//HttpURLConnection.setFollowRedirects(true);
			if(paramList!=null&&paramList.size()>0)
			{
				httpConn.setDoOutput(true);
				httpConn.setRequestMethod("POST");

				//发送数据
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
			
			//接收返回数据
			inputStream=httpConn.getInputStream();
			//将输入流写入文件
			FileReadWrite frw=new FileReadWrite();
			//this.resultCode=frw.writeFile(inputStream, fileName, dirName);
			this.resultCode=frw.writeFile(inputStream, fileName, dirName,newWrite);
			
			//获取响应码
			int reCode=httpConn.getResponseCode();
			System.out.println("responseCode:"+reCode);
			if(reCode!=200)
			{//任何错误都定义为其他错误
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
	 * 下载图片文件
	 * @param urlStr,请求的地址
	 * @param paramList,参数列表
	 * @param fileName,文件名
	 * @param dirName,目录名
	 * @param newWrite,图片存在是否重新下载
	 * @return 返回错误代码
	 **************************************************************/
	public int downloadImg(String urlStr,
			ArrayList<HashMap<String, String>> paramList,String fileName,String dirName,boolean newWrite)
	{
		this.urlStr = urlStr;
		this.paramList = paramList;
		return downloadImg(fileName,dirName,newWrite);
	}
}
