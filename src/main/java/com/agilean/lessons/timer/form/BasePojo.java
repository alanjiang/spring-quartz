package com.agilean.lessons.timer.form;
public class BasePojo {
	
	/**
	 * 错误码
	 */
	private String errCode;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 家庭ID
	 */
	private Long familyId;
    
	/**
	 * @see #familyId
	 */
	public Long getFamilyId() {
		return familyId;
	}

	/**
	 * @see #familyId 
	 */
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode the errCode to set
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
  
	
	
	
	

}
