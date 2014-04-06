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
	
	/*****************消息信息表字段名*****************/
	public static class MessageInfoProperty{
		//id
		public static final String id="id";
		//订单ID
	    public static final String orderId="order_id";
	    //类型
	    public static final String type="type";
	    //状态
	    public static final String status="status";
	    //接受方ID
	    public static final String receiveId="receive_id";
	    //发送方ID
	    public static final String sendId="send_id";
	    //内容
	    public static final String content="content";
	    //预定接收时间
	    public static final String recTime="rec_time";
	    //发送方向
	    public static final String sendType="send_type";
	    //发送时间
	    public static final String sendTime="send_time";
	    //饭店ID
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
		//execSQL函数用于执行SQL语句
		StringBuffer sb=new StringBuffer("create table "+msgInfoTable+" (");
		//id
		sb.append(MessageInfoProperty.id+" LONG,");
		//订单ID
	    sb.append(MessageInfoProperty.orderId+" LONG,");
	    //类型
	    sb.append(MessageInfoProperty.type+" INTEGER,");
	    //状态
	    sb.append(MessageInfoProperty.status+" INTEGER,");
	    //接受方ID
	    sb.append(MessageInfoProperty.receiveId+" LONG,");
	    //发送方ID
	    sb.append(MessageInfoProperty.sendId+" LONG,");
	    //内容
	    sb.append(MessageInfoProperty.content+" TEXT,");
	    //预定接收时间
	    sb.append(MessageInfoProperty.recTime+" TEXT,");
	    //发送方向
	    sb.append(MessageInfoProperty.sendType+" INTEGER,");
	    //发送时间
	    sb.append(MessageInfoProperty.sendTime+" TEXT,");
	    //饭店ID
		sb.append(MessageInfoProperty.restaurantId+" LONG)");
		db.execSQL(sb.toString());				
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("update table test");
	}
	
	/********************************
	 * 将一条消息信息写入表中,成功插入一条返回true，否则返回false
	 * @param omi,订单信息对象
	 * *****************************/
	public boolean insert(MessageInfo msgi){
		//查询消息编号对于的记录是否存在
		SQLiteDatabase dbr=getReadableDatabase();
		Cursor c=dbr.query(msgInfoTable, null, MessageInfoProperty.id+"=?", new String[]{String.valueOf(msgi.id)}, null, null, null);
		if(c.moveToFirst()){
			dbr.close();
			return false;
		}
		dbr.close();
		SQLiteDatabase dbw=getWritableDatabase();
		//写入数据
		ContentValues values=new ContentValues();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//id
		values.put(MessageInfoProperty.id,msgi.id);
		//订单ID
	    values.put(MessageInfoProperty.orderId,msgi.orderId);
	    //类型
	    values.put(MessageInfoProperty.type,msgi.type);
	    //状态
	    values.put(MessageInfoProperty.status,msgi.status);
	    //接受方ID
	    values.put(MessageInfoProperty.receiveId,msgi.receiveId);
	    //发送方ID
	    values.put(MessageInfoProperty.sendId,msgi.sendId);
	    //内容
	    values.put(MessageInfoProperty.content,msgi.content);
	    //预定接收时间
	    values.put(MessageInfoProperty.recTime,sdf.format(msgi.recTime));
	    //发送方向
	    values.put(MessageInfoProperty.sendType,msgi.sendType);
	    //发送时间
	    values.put(MessageInfoProperty.sendTime,sdf.format(msgi.sendTime));
	    //饭店ID
		values.put(MessageInfoProperty.restaurantId,msgi.restaurantId);
		
		dbw.insert(msgInfoTable, null, values);
		dbw.close();
		return true;
	}
	
	/****************
	 * 更新消息状态为已读
	 * @param id, 消息编号
	 * *************/
	public void updateStatus(long id){
		SQLiteDatabase dbw=getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(MessageInfoProperty.status, MessageStatus.READED_STATUS.code);
		dbw.update(msgInfoTable, values, MessageInfoProperty.id+"=?", new String[]{String.valueOf(id)});
		System.out.println("数据库中更新了状态-->"+id);
		dbw.close();
	}
	
	/********************
	 * 查询获取所有未读消息,倒排序
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
				//订单ID
			    msgi.orderId=c.getLong(1);
			    //类型
			    msgi.type=c.getInt(2);
			    //状态
			    msgi.status=c.getInt(3);
			    //接受方ID
			    msgi.receiveId=c.getLong(4);
			    //发送方ID
			    msgi.sendId=c.getLong(5);
			    //内容
			    msgi.content=c.getString(6);
			    //预定接收时间
			    try{msgi.recTime=sdf.parse(c.getString(7));}catch(Exception ex){}
			    //发送方向
			    msgi.sendType=c.getInt(8);
			    //发送时间
			    try{msgi.sendTime=sdf.parse(c.getString(9));}catch(Exception ex){}
			    //饭店ID
				msgi.restaurantId=c.getLong(10);
				msgiList.add(msgi);
			}
		}
		dbr.close();
		return msgiList;
	}
	
}
