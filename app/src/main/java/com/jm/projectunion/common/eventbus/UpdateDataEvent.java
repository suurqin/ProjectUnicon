package com.jm.projectunion.common.eventbus;

public class UpdateDataEvent {
	private int status;
	private String msg;
	private Object tag;
	private String className;

	public UpdateDataEvent(int status, String msg, Object tag, String className) {
		super();
		this.status = status;
		this.msg = msg;
		this.tag = tag;
		this.className = className;
	}

	public UpdateDataEvent(int status, String msg,String className) {
		super();
		this.status = status;
		this.msg = msg;
		this.className = className;
	}
	public UpdateDataEvent() {
		super();
	}
	public UpdateDataEvent(int status, Object tag, String className) {
		super();
		this.status = status;
		this.tag = tag;
		this.className = className;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

}
