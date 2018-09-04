package com.agilean.lessons.timer.form;
public class FamilyAirAppointForm extends BasePojo
{
    private Long sceneId;
    private Long userId;
    private Long roomId;
    private String roomName;
    private Integer roomType;
    private Long  applianceId;
    private String applianceName;
    private Integer applianceType;
    private Integer switcher;//1 关闭定时任务, 2 开启定时任务
    private String time;
    private int[] week;
    private Integer timeZone;
    private Integer status;//预约状态，1关闭，2开启，默认2
    private Integer repeat;//1不重复，2重复。
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getRoomType() {
		return roomType;
	}
	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}
	public Long getApplianceId() {
		return applianceId;
	}
	public void setApplianceId(Long applianceId) {
		this.applianceId = applianceId;
	}
	public String getApplianceName() {
		return applianceName;
	}
	public void setApplianceName(String applianceName) {
		this.applianceName = applianceName;
	}
	public Integer getApplianceType() {
		return applianceType;
	}
	public void setApplianceType(Integer applianceType) {
		this.applianceType = applianceType;
	}
	public Integer getSwitcher() {
		return switcher;
	}
	public void setSwitcher(Integer switcher) {
		this.switcher = switcher;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int[] getWeek() {
		return week;
	}
	public void setWeek(int[] week) {
		this.week = week;
	}
	public Integer getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(Integer timeZone) {
		this.timeZone = timeZone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRepeat() {
		return repeat;
	}
	public void setRepeat(Integer repeat) {
		this.repeat = repeat;
	}
    
    
	
	
    
    
  
	
}
