package lj.cmc.string_analysis;

import lj.cmc.model.ApplicationVersionInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class AnalyzeXmlHelper extends DefaultHandler {
	private ApplicationVersionInfo appVInfo=null;
	private String tagName=null;
	
	public ApplicationVersionInfo getAppVInfo() {
		return appVInfo;
	}
	public void setAppVInfo(ApplicationVersionInfo appVInfo) {
		this.appVInfo = appVInfo;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		
		if(this.tagName.equals("appName"))
		{
			this.appVInfo.setAppName(new String(ch,start,length));
		}
		else if(this.tagName.equals("apkName"))
		{
			this.appVInfo.setApkName(new String(ch,start,length));
		}
		else if(this.tagName.equals("apkSize"))
		{
			this.appVInfo.setApkSize(new String(ch,start,length));
		}
		else if(this.tagName.equals("verName"))
		{
			this.appVInfo.setVerName(new String(ch,start,length));
		}
		else if(this.tagName.equals("verCode"))
		{
			this.appVInfo.setVerCode(new String(ch,start,length));
		}
		else if(this.tagName.equals("apkUrl"))
		{
			this.appVInfo.setApkUrl(new String(ch,start,length));
		}
		else if(this.tagName.equals("updateInfo"))
		{
			this.appVInfo.setUpdateInfo(new String(ch,start,length));
		}
	}
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		this.tagName="";
	}
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		this.tagName=localName;
		if(this.tagName.equals("versionInfo"))
		{
			this.appVInfo=new ApplicationVersionInfo();
		}
	}
	
	
	
	
}
