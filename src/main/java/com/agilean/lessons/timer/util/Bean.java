package com.agilean.lessons.timer.util;

public class Bean {
 private String name;
 private String no;
 
 private String address;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNo() {
	return no;
}
public void setNo(String no) {
	this.no = no;
}
public Bean(String name, String no) {
	super();
	this.name = name;
	this.no = no;
}
public Bean() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "Bean [name=" + name + ", no=" + no + ", address=" + address + "]";
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
 
}
