package lj.cms.common;

import java.util.HashMap;

public class NotifyId {
	private long orderId;
	private int msgType;
	private int nId;
	
	public NotifyId(long orderId, int msgType, int nId) {
		super();
		this.orderId = orderId;
		this.msgType = msgType;
		this.nId = nId;
	}
	private static int maxId=10;//预留10个数给其他通知用
	private static HashMap<Long,HashMap<Integer,NotifyId>> notifyIdOrderIdMap=null;
	public static int getNId(long orderId,int msgType){
		if(notifyIdOrderIdMap==null){
			notifyIdOrderIdMap=new HashMap<Long,HashMap<Integer,NotifyId>>();
		}
		HashMap<Integer,NotifyId> notifyIdTypeMap=notifyIdOrderIdMap.get(orderId);
		if(notifyIdTypeMap==null){
			maxId++;
			notifyIdTypeMap=new HashMap<Integer, NotifyId>();
			notifyIdTypeMap.put(msgType, new NotifyId(orderId,msgType,maxId));
			notifyIdOrderIdMap.put(orderId, notifyIdTypeMap);
		}
		else{
			NotifyId nIdObj=notifyIdTypeMap.get(msgType);
			if(nIdObj==null){
				maxId++;
				notifyIdTypeMap.put(msgType, new NotifyId(orderId,msgType,maxId));
			}
			else{
				return nIdObj.nId;
			}
		}
		return maxId;
	}
}
