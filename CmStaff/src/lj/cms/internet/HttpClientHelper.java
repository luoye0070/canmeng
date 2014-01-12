package lj.cms.internet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import lj.cms.constant.AppConstant;
import lj.cms.filerw.FileReadWrite;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;


public class HttpClientHelper {
	
	/*********单例**********/
	private static HttpClientHelper httpClientHelper=null;
	public static HttpClientHelper getInstance(){
		if(httpClientHelper==null){
			httpClientHelper=new HttpClientHelper();
		}
		return httpClientHelper;
	}
	
	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
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

	public int getOuttime() {
		return outtime;
	}

	public void setOuttime(int outtime) {
		this.outtime = outtime;
        if(this.httpClient!=null){
            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
            this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间		
        }
	}
	private HttpClient httpClient=null;
	private String urlStr=null;
	private int resultCode;//访问网络结果代码,0成功,1网络错误，2其他错误
	private ArrayList<HashMap<String, String>> paramList=null;
	private int outtime;
	/********************
	 *构造函数，创建一个客户端 
	 ***********************/
	public HttpClientHelper(){
		//this.httpClient=new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
		this.httpClient=new DefaultHttpClient();
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		this.outtime=5000;
		this.urlStr = null;
		this.paramList = null;
        this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
		this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间
	}
	
	/********************
	 *构造函数，创建一个客户端 
	 ***********************/
	public HttpClientHelper(String urlStr,
			ArrayList<HashMap<String, String>> paramList) {
		super();
		//this.httpClient=new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
		this.httpClient=new DefaultHttpClient();
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		this.outtime=5000;
		this.urlStr = urlStr;
		this.paramList = paramList;
        this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
		this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间
	}
	/********************
	 *构造函数，创建一个客户端 
	 ***********************/
	public HttpClientHelper(String urlStr,
			ArrayList<HashMap<String, String>> paramList, int outtime) {
		super();
		//this.httpClient=new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
		this.httpClient=new DefaultHttpClient();
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		this.urlStr = urlStr;
		this.paramList = paramList;
		this.outtime = outtime;
        this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
		this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间
	}

	/*************************
	 * 关闭客户端，释放资源
	 * ************************/
	public void shutdownClient(){
		if(this.httpClient!=null){
			this.httpClient.getConnectionManager().shutdown();
		}
	}
	
	public String getResponseStr(){
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		StringBuffer sb = new StringBuffer();
		BufferedReader reader=null;
		if (this.httpClient == null) {
			//this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
			this.httpClient=new DefaultHttpClient();
			          this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间		
		}
		try {
			HttpResponse response = null;
			if (this.paramList == null||this.paramList.size()<=0) {// get方式
				HttpGet get = new HttpGet(urlStr);
				response = this.httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(urlStr);
				// 添加参数
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					nvps.add(new BasicNameValuePair(paramHash
							.get(AppConstant.HttpParamRe.PARAM_NAME), paramHash
							.get(AppConstant.HttpParamRe.PARAM_VALUE)));
				}
				// nvps.add(new
				// BasicNameValuePair("j_password","liuzhaoguo12345"));
				// 设置请求的编码格式
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				response = this.httpClient.execute(post);
			}
			System.out.println("状态1是："
					+ response.getStatusLine().getStatusCode());
			int statusCode = response.getStatusLine().getStatusCode();
			while (statusCode >= 300 && statusCode < 310) {
				String newUrl = "";
				Header locationHeader = response.getFirstHeader("Location");
				if (locationHeader != null) {
					newUrl = locationHeader.getValue();
				}
				HttpGet get = new HttpGet(newUrl);
				response = this.httpClient.execute(get);
				statusCode = response.getStatusLine().getStatusCode();
			}
			if (statusCode != 200) {// 任何错误都定义为其他错误
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return sb.toString();
			}
			// 得到返回的client里面的实体对象信息.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("内容编码是：" + entity.getContentEncoding());
				System.out.println("内容类型是：" + entity.getContentType());
				// 得到返回的主体内容.
				InputStream instream = entity.getContent();
				reader = new BufferedReader(
						new InputStreamReader(instream, "utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					sb.append(line);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			e.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e1.printStackTrace();
		} catch (Exception ex) {
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			ex.printStackTrace();
		}
		finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public String getResponseStr(String urlStr,
			ArrayList<HashMap<String, String>> paramList){
		this.urlStr=urlStr;
		this.paramList=paramList;		
		return getResponseStr();
	}
	
	
	public String doGet(){
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		StringBuffer sb = new StringBuffer();
		BufferedReader reader=null;
		if (this.httpClient == null) {
			//this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
			this.httpClient=new DefaultHttpClient();
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间		
		}
		try {
			HttpResponse response = null;
			if (this.paramList != null&&this.paramList.size()>0) {// get方式
				// 添加参数
				urlStr+="?";
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					urlStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}				
			} 
			HttpGet get = new HttpGet(urlStr);
			response = this.httpClient.execute(get);
			
			System.out.println("状态1是："
					+ response.getStatusLine().getStatusCode());
			int statusCode = response.getStatusLine().getStatusCode();
			while (statusCode >= 300 && statusCode < 310) {
				String newUrl = "";
				Header locationHeader = response.getFirstHeader("Location");
				if (locationHeader != null) {
					newUrl = locationHeader.getValue();
				}
				get = new HttpGet(newUrl);
				response = this.httpClient.execute(get);
				statusCode = response.getStatusLine().getStatusCode();
			}
			if (statusCode != 200) {// 任何错误都定义为其他错误
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return sb.toString();
			}
			// 得到返回的client里面的实体对象信息.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("内容编码是：" + entity.getContentEncoding());
				System.out.println("内容类型是：" + entity.getContentType());
				// 得到返回的主体内容.
				InputStream instream = entity.getContent();
				reader = new BufferedReader(
						new InputStreamReader(instream, "utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					sb.append(line);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			e.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e1.printStackTrace();
		} catch (Exception ex) {
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			ex.printStackTrace();
		}
		finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	public String doGet(String urlStr,
			ArrayList<HashMap<String, String>> paramList){
		this.urlStr=urlStr;
		this.paramList=paramList;		
		return doGet();
	}
	
	public String doPost(){
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		StringBuffer sb = new StringBuffer();
		BufferedReader reader=null;
		if (this.httpClient == null) {
			//this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
			this.httpClient=new DefaultHttpClient();
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间		
		}
		try {
			HttpResponse response = null;
			
			HttpPost post = new HttpPost(urlStr);
			// 添加参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if(this.paramList != null&&this.paramList.size()>0){
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					nvps.add(new BasicNameValuePair(paramHash
							.get(AppConstant.HttpParamRe.PARAM_NAME), paramHash
							.get(AppConstant.HttpParamRe.PARAM_VALUE)));
				}
			}
			// nvps.add(new
			// BasicNameValuePair("j_password","liuzhaoguo12345"));
			// 设置请求的编码格式
			post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			response = this.httpClient.execute(post);
			
			System.out.println("状态1是："
					+ response.getStatusLine().getStatusCode());
			int statusCode = response.getStatusLine().getStatusCode();
			while (statusCode >= 300 && statusCode < 310) {
				String newUrl = "";
				Header locationHeader = response.getFirstHeader("Location");
				if (locationHeader != null) {
					newUrl = locationHeader.getValue();
				}
				HttpGet get = new HttpGet(newUrl);
				response = this.httpClient.execute(get);
				statusCode = response.getStatusLine().getStatusCode();
			}
			if (statusCode != 200) {// 任何错误都定义为其他错误
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return sb.toString();
			}
			// 得到返回的client里面的实体对象信息.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("内容编码是：" + entity.getContentEncoding());
				System.out.println("内容类型是：" + entity.getContentType());
				// 得到返回的主体内容.
				InputStream instream = entity.getContent();
				reader = new BufferedReader(
						new InputStreamReader(instream, "utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					sb.append(line);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			e.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e1.printStackTrace();
		} catch (Exception ex) {
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			ex.printStackTrace();
		}
		finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	public String doPost(String urlStr,
			ArrayList<HashMap<String, String>> paramList){
		this.urlStr=urlStr;
		this.paramList=paramList;		
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
		return downloadImg(fileName,dirName,false);
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
	
	/**************************************
	 *从服务器获取响应数据字节数
	 *@return 返回服务器返回的数据 
	 **********************************/
	public long getResponseSize()
	{
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		System.out.println(this.urlStr);
		long responseSize=0;
		if (this.httpClient == null) {
			//this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
			this.httpClient=new DefaultHttpClient();
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间		
		}
		try {
			HttpResponse response = null;
			if (this.paramList == null||this.paramList.size()<=0) {// get方式
				HttpGet get = new HttpGet(urlStr);
				response = this.httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(urlStr);
				// 添加参数
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					nvps.add(new BasicNameValuePair(paramHash
							.get(AppConstant.HttpParamRe.PARAM_NAME), paramHash
							.get(AppConstant.HttpParamRe.PARAM_VALUE)));
				}
				// nvps.add(new
				// BasicNameValuePair("j_password","liuzhaoguo12345"));
				// 设置请求的编码格式
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				response = this.httpClient.execute(post);
			}
			System.out.println("状态1是："
					+ response.getStatusLine().getStatusCode());
			int statusCode = response.getStatusLine().getStatusCode();
			while (statusCode >= 300 && statusCode < 310) {
				String newUrl = "";
				Header locationHeader = response.getFirstHeader("Location");
				if (locationHeader != null) {
					newUrl = locationHeader.getValue();
				}
				HttpGet get = new HttpGet(newUrl);
				response = this.httpClient.execute(get);
				statusCode = response.getStatusLine().getStatusCode();
			}
			if (statusCode != 200) {// 任何错误都定义为其他错误
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return responseSize;
			}
			// 得到返回的client里面的实体对象信息.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("内容编码是：" + entity.getContentEncoding());
				System.out.println("内容类型是：" + entity.getContentType());
				responseSize=entity.getContentLength();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			e.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e1.printStackTrace();
		} catch (Exception ex) {
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			ex.printStackTrace();
		}
		finally{
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
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		InputStream instream = null;
		if (this.httpClient == null) {
			//this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
			this.httpClient=new DefaultHttpClient();
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//连接超时时间
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//数据读取超时时间		
		}
		try {
			HttpResponse response = null;
			if (this.paramList == null||this.paramList.size()<=0) {// get方式
				HttpGet get = new HttpGet(urlStr);
				response = this.httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(urlStr);
				// 添加参数
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					nvps.add(new BasicNameValuePair(paramHash
							.get(AppConstant.HttpParamRe.PARAM_NAME), paramHash
							.get(AppConstant.HttpParamRe.PARAM_VALUE)));
				}
				// nvps.add(new
				// BasicNameValuePair("j_password","liuzhaoguo12345"));
				// 设置请求的编码格式
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				response = this.httpClient.execute(post);
			}
			System.out.println("状态1是："
					+ response.getStatusLine().getStatusCode());
			int statusCode = response.getStatusLine().getStatusCode();
			while (statusCode >= 300 && statusCode < 310) {
				String newUrl = "";
				Header locationHeader = response.getFirstHeader("Location");
				if (locationHeader != null) {
					newUrl = locationHeader.getValue();
				}
				HttpGet get = new HttpGet(newUrl);
				response = this.httpClient.execute(get);
				statusCode = response.getStatusLine().getStatusCode();
			}
			if (statusCode != 200) {// 任何错误都定义为其他错误
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return this.resultCode;
			}
			// 得到返回的client里面的实体对象信息.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("内容编码是：" + entity.getContentEncoding());
				System.out.println("内容类型是：" + entity.getContentType());
				// 得到返回的主体内容.
				instream = entity.getContent();
				//将输入流写入文件
				FileReadWrite frw=new FileReadWrite();
				this.resultCode=frw.writeFile(instream, fileName, dirName,newWrite);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_NETWORKERROR;
			e.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			e1.printStackTrace();
		} catch (Exception ex) {
			this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
			ex.printStackTrace();
		}
		finally{
			if(instream!=null)
			{
				try {
					instream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
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
	
	
	
	
	public void test() throws ClientProtocolException, IOException{
		
String url="http://user.xjmei.com/j_spring_security_check?j_username=luoye_lj&j_password=liuzhaoguo12345";
        
        // 默认的client类。  
        HttpClient client = new DefaultHttpClient();  
        // 设置为get取连接的方式.  
        HttpGet get = new HttpGet(url);  
        // 得到返回的response.  
        HttpResponse response = client.execute(get);  
        response.getFirstHeader("").getName();
        // 得到返回的client里面的实体对象信息.  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            System.out.println("内容编码是：" + entity.getContentEncoding());  
            System.out.println("内容类型是：" + entity.getContentType());  
            // 得到返回的主体内容.  
            InputStream instream = entity.getContent();  
            try {  
                BufferedReader reader = new BufferedReader(  
                        new InputStreamReader(instream, "utf-8"));  
                System.out.println(reader.readLine());  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                instream.close();  
            }  
        }  
  
        // 关闭连接.  
        client.getConnectionManager().shutdown();  
	}
}
