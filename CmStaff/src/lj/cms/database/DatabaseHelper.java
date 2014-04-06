package lj.cms.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lj.cms.constant.MessageStatus;
import lj.cms.model.MessageInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int VERSION=1;
	private static final String msgInfoTable="msg_info";
	
	/*****************��Ϣ��Ϣ���ֶ���*****************/
	public static class MessageInfoProperty{
		//id
		public static final String id="id";
		//����ID
	    public static final String orderId="order_id";
	    //����
	    public static final String type="type";
	    //״̬
	    public static final String status="status";
	    //���ܷ�ID
	    public static final String receiveId="receive_id";
	    //���ͷ�ID
	    public static final String sendId="send_id";
	    //����
	    public static final String content="content";
	    //Ԥ������ʱ��
	    public static final String recTime="rec_time";
	    //���ͷ���
	    public static final String sendType="send_type";
	    //����ʱ��
	    public static final String sendTime="send_time";
	    //����ID
	    public static final String restaurantId="restaurant_id";
	}
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHelper(Context context, String name,int version)
	{
		this(context, name, null, version);
	}
	public DatabaseHelper(Context context, String name)
	{
		this(context, name, VERSION);
	}
	public DatabaseHelper(Context context)
	{
		this(context, "cm_s.db", VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create table "+msgInfoTable);
		//execSQL��������ִ��SQL���
		StringBuffer sb=new StringBuffer("create table "+msgInfoTable+" (");
		//id
		sb.append(MessageInfoProperty.id+" LONG,");
		//����ID
	    sb.append(MessageInfoProperty.orderId+" LONG,");
	    //����
	    sb.append(MessageInfoProperty.type+" INTEGER,");
	    //״̬
	    sb.append(MessageInfoProperty.status+" INTEGER,");
	    //���ܷ�ID
	    sb.append(MessageInfoProperty.receiveId+" LONG,");
	    //���ͷ�ID
	    sb.append(MessageInfoProperty.sendId+" LONG,");
	    //����
	    sb.append(MessageInfoProperty.content+" TEXT,");
	    //Ԥ������ʱ��
	    sb.append(MessageInfoProperty.recTime+" TEXT,");
	    //���ͷ���
	    sb.append(MessageInfoProperty.sendType+" INTEGER,");
	    //����ʱ��
	    sb.append(MessageInfoProperty.sendTime+" TEXT,");
	    //����ID
		sb.append(MessageInfoProperty.restaurantId+" LONG)");
		db.execSQL(sb.toString());				
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("update table test");
	}
	
	/********************************
	 * ��һ����Ϣ��Ϣд�����,�ɹ�����һ������true�����򷵻�false
	 * @param omi,������Ϣ����
	 * *****************************/
	public boolean insert(MessageInfo msgi){
		//��ѯ��Ϣ��Ŷ��ڵļ�¼�Ƿ����
		SQLiteDatabase dbr=getReadableDatabase();
		Cursor c=dbr.query(msgInfoTable, null, MessageInfoProperty.id+"=?", new String[]{String.valueOf(msgi.id)}, null, null, null);
		if(c.moveToFirst()){
			dbr.close();
			return false;
		}
		dbr.close();
		SQLiteDatabase dbw=getWritableDatabase();
		//д������
		ContentValues values=new ContentValues();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//id
		values.put(MessageInfoProperty.id,msgi.id);
		//����ID
	    values.put(MessageInfoProperty.orderId,msgi.orderId);
	    //����
	    values.put(MessageInfoProperty.type,msgi.type);
	    //״̬
	    values.put(MessageInfoProperty.status,msgi.status);
	    //���ܷ�ID
	    values.put(MessageInfoProperty.receiveId,msgi.receiveId);
	    //���ͷ�ID
	    values.put(MessageInfoProperty.sendId,msgi.sendId);
	    //����
	    values.put(MessageInfoProperty.content,msgi.content);
	    //Ԥ������ʱ��
	    values.put(MessageInfoProperty.recTime,sdf.format(msgi.recTime));
	    //���ͷ���
	    values.put(MessageInfoProperty.sendType,msgi.sendType);
	    //����ʱ��
	    values.put(MessageInfoProperty.sendTime,sdf.format(msgi.sendTime));
	    //����ID
		values.put(MessageInfoProperty.restaurantId,msgi.restaurantId);
		
		dbw.insert(msgInfoTable, null, values);
		dbw.close();
		return true;
	}
	
	/****************
	 * ������Ϣ״̬Ϊ�Ѷ�
	 * @param id, ��Ϣ���
	 * *************/
	public void updateStatus(long id){
		SQLiteDatabase dbw=getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(MessageInfoProperty.status, MessageStatus.READED_STATUS.code);
		dbw.update(msgInfoTable, values, MessageInfoProperty.id+"=?", new String[]{String.valueOf(id)});
		System.out.println("���ݿ��и�����״̬-->"+id);
		dbw.close();
	}
	
	/********************
	 * ��ѯ��ȡ����δ����Ϣ,������
	 * ********************/
	public ArrayList<MessageInfo> getMsgNoReadList(long receiveId){
		ArrayList<MessageInfo> msgiList=null;		
		SQLiteDatabase dbr=getReadableDatabase();
		Cursor c=dbr.query(msgInfoTable, null, MessageInfoProperty.status+"=? and "+MessageInfoProperty.receiveId+"=?", 
				new String[]{String.valueOf(MessageStatus.ORIGINAL_STATUS.code),String.valueOf(receiveId)}, null, null, "id desc");
		if(c.moveToFirst()){
			msgiList=new ArrayList<MessageInfo>();
			for(int i=0;i<c.getCount();i++){
				MessageInfo msgi=new MessageInfo();
				c.moveToPosition(i);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//id
				msgi.id=c.getLong(0);
				//����ID
			    msgi.orderId=c.getLong(1);
			    //����
			    msgi.type=c.getInt(2);
			    //״̬
			    msgi.status=c.getInt(3);
			    //���ܷ�ID
			    msgi.receiveId=c.getLong(4);
			    //���ͷ�ID
			    msgi.sendId=c.getLong(5);
			    //����
			    msgi.content=c.getString(6);
			    //Ԥ������ʱ��
			    try{msgi.recTime=sdf.parse(c.getString(7));}catch(Exception ex){}
			    //���ͷ���
			    msgi.sendType=c.getInt(8);
			    //����ʱ��
			    try{msgi.sendTime=sdf.parse(c.getString(9));}catch(Exception ex){}
			    //����ID
				msgi.restaurantId=c.getLong(10);
				msgiList.add(msgi);
			}
		}
		dbr.close();
		return msgiList;
	}
	
}
