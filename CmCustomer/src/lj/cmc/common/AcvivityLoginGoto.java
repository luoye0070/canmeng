package lj.cmc.common;

public class AcvivityLoginGoto {	
	
	static ActivityCallBackInterface acbi=null;
	
	public static void setAcbi(ActivityCallBackInterface acbiParam)
	{
		acbi=acbiParam;
	}
	
	public static ActivityCallBackInterface getAcbi() {
		return acbi;
	}
}
