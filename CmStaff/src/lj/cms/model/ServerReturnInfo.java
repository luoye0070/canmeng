package lj.cms.model;

import java.io.Serializable;

public class ServerReturnInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int returnCode;
	private String returnMsg=null;
	
	public ServerReturnInfo() {
		super();
		this.returnCode = -1;
		this.returnMsg = "";
	}

	public ServerReturnInfo(int returnCode, String returnMsg) {
		super();
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Override
	public String toString() {
		return "ServerReturnInfo [returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + "]";
	}
	
}
