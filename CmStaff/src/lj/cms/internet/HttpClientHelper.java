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
	
	/*********����**********/
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
            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
            this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��		
        }
	}
	private HttpClient httpClient=null;
	private String urlStr=null;
	private int resultCode;//��������������,0�ɹ�,1�������2��������
	private ArrayList<HashMap<String, String>> paramList=null;
	private int outtime;
	/********************
	 *���캯��������һ���ͻ��� 
	 ***********************/
	public HttpClientHelper(){
		//this.httpClient=new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
		this.httpClient=new DefaultHttpClient();
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		this.outtime=5000;
		this.urlStr = null;
		this.paramList = null;
        this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
		this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��
	}
	
	/********************
	 *���캯��������һ���ͻ��� 
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
        this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
		this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��
	}
	/********************
	 *���캯��������һ���ͻ��� 
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
        this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
		this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��
	}

	/*************************
	 * �رտͻ��ˣ��ͷ���Դ
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
			          this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��		
		}
		try {
			HttpResponse response = null;
			if (this.paramList == null||this.paramList.size()<=0) {// get��ʽ
				HttpGet get = new HttpGet(urlStr);
				response = this.httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(urlStr);
				// ��Ӳ���
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					nvps.add(new BasicNameValuePair(paramHash
							.get(AppConstant.HttpParamRe.PARAM_NAME), paramHash
							.get(AppConstant.HttpParamRe.PARAM_VALUE)));
				}
				// nvps.add(new
				// BasicNameValuePair("j_password","liuzhaoguo12345"));
				// ��������ı����ʽ
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				response = this.httpClient.execute(post);
			}
			System.out.println("״̬1�ǣ�"
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
			if (statusCode != 200) {// �κδ��󶼶���Ϊ��������
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return sb.toString();
			}
			// �õ����ص�client�����ʵ�������Ϣ.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("���ݱ����ǣ�" + entity.getContentEncoding());
				System.out.println("���������ǣ�" + entity.getContentType());
				// �õ����ص���������.
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
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��		
		}
		try {
			HttpResponse response = null;
			if (this.paramList != null&&this.paramList.size()>0) {// get��ʽ
				// ��Ӳ���
				urlStr+="?";
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					urlStr+=paramHash.get(AppConstant.HttpParamRe.PARAM_NAME)+"="+paramHash.get(AppConstant.HttpParamRe.PARAM_VALUE)+"&";
				}				
			} 
			HttpGet get = new HttpGet(urlStr);
			response = this.httpClient.execute(get);
			
			System.out.println("״̬1�ǣ�"
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
			if (statusCode != 200) {// �κδ��󶼶���Ϊ��������
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return sb.toString();
			}
			// �õ����ص�client�����ʵ�������Ϣ.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("���ݱ����ǣ�" + entity.getContentEncoding());
				System.out.println("���������ǣ�" + entity.getContentType());
				// �õ����ص���������.
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
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��		
		}
		try {
			HttpResponse response = null;
			
			HttpPost post = new HttpPost(urlStr);
			// ��Ӳ���
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
			// ��������ı����ʽ
			post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			response = this.httpClient.execute(post);
			
			System.out.println("״̬1�ǣ�"
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
			if (statusCode != 200) {// �κδ��󶼶���Ϊ��������
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return sb.toString();
			}
			// �õ����ص�client�����ʵ�������Ϣ.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("���ݱ����ǣ�" + entity.getContentEncoding());
				System.out.println("���������ǣ�" + entity.getContentType());
				// �õ����ص���������.
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
	 * ����ͼƬ�ļ�
	 * @param fileName,�ļ���
	 * @param dirName,Ŀ¼��
	 * @return ���ش������
	 **************************************************************/
	public int downloadImg(String fileName,String dirName)
	{
		return downloadImg(fileName,dirName,false);
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
	
	/**************************************
	 *�ӷ�������ȡ��Ӧ�����ֽ���
	 *@return ���ط��������ص����� 
	 **********************************/
	public long getResponseSize()
	{
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		System.out.println(this.urlStr);
		long responseSize=0;
		if (this.httpClient == null) {
			//this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
			this.httpClient=new DefaultHttpClient();
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��		
		}
		try {
			HttpResponse response = null;
			if (this.paramList == null||this.paramList.size()<=0) {// get��ʽ
				HttpGet get = new HttpGet(urlStr);
				response = this.httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(urlStr);
				// ��Ӳ���
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					nvps.add(new BasicNameValuePair(paramHash
							.get(AppConstant.HttpParamRe.PARAM_NAME), paramHash
							.get(AppConstant.HttpParamRe.PARAM_VALUE)));
				}
				// nvps.add(new
				// BasicNameValuePair("j_password","liuzhaoguo12345"));
				// ��������ı����ʽ
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				response = this.httpClient.execute(post);
			}
			System.out.println("״̬1�ǣ�"
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
			if (statusCode != 200) {// �κδ��󶼶���Ϊ��������
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return responseSize;
			}
			// �õ����ص�client�����ʵ�������Ϣ.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("���ݱ����ǣ�" + entity.getContentEncoding());
				System.out.println("���������ǣ�" + entity.getContentType());
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
		this.resultCode=AppConstant.VisitServerResultCode.RESULT_CODE_OK;
		InputStream instream = null;
		if (this.httpClient == null) {
			//this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(null, null),null);
			this.httpClient=new DefaultHttpClient();
			            this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.outtime);//���ӳ�ʱʱ��
                        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.outtime*10);//���ݶ�ȡ��ʱʱ��		
		}
		try {
			HttpResponse response = null;
			if (this.paramList == null||this.paramList.size()<=0) {// get��ʽ
				HttpGet get = new HttpGet(urlStr);
				response = this.httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(urlStr);
				// ��Ӳ���
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < this.paramList.size(); i++) {
					HashMap<String, String> paramHash = this.paramList.get(i);
					nvps.add(new BasicNameValuePair(paramHash
							.get(AppConstant.HttpParamRe.PARAM_NAME), paramHash
							.get(AppConstant.HttpParamRe.PARAM_VALUE)));
				}
				// nvps.add(new
				// BasicNameValuePair("j_password","liuzhaoguo12345"));
				// ��������ı����ʽ
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				response = this.httpClient.execute(post);
			}
			System.out.println("״̬1�ǣ�"
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
			if (statusCode != 200) {// �κδ��󶼶���Ϊ��������
				this.resultCode = AppConstant.VisitServerResultCode.RESULT_CODE_OTHERERROR;
				return this.resultCode;
			}
			// �õ����ص�client�����ʵ�������Ϣ.
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("���ݱ����ǣ�" + entity.getContentEncoding());
				System.out.println("���������ǣ�" + entity.getContentType());
				// �õ����ص���������.
				instream = entity.getContent();
				//��������д���ļ�
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
	
	
	
	
	public void test() throws ClientProtocolException, IOException{
		
String url="http://user.xjmei.com/j_spring_security_check?j_username=luoye_lj&j_password=liuzhaoguo12345";
        
        // Ĭ�ϵ�client�ࡣ  
        HttpClient client = new DefaultHttpClient();  
        // ����Ϊgetȡ���ӵķ�ʽ.  
        HttpGet get = new HttpGet(url);  
        // �õ����ص�response.  
        HttpResponse response = client.execute(get);  
        response.getFirstHeader("").getName();
        // �õ����ص�client�����ʵ�������Ϣ.  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            System.out.println("���ݱ����ǣ�" + entity.getContentEncoding());  
            System.out.println("���������ǣ�" + entity.getContentType());  
            // �õ����ص���������.  
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
  
        // �ر�����.  
        client.getConnectionManager().shutdown();  
	}
}
