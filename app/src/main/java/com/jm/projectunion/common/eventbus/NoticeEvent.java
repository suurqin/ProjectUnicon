package com.jm.projectunion.common.eventbus;

import android.content.Context;

public class NoticeEvent {
	private int status;
	private String msg;
	private Object tag;
	private Context context;

	public NoticeEvent(int status, String msg, Object tag, Context context) {
		super();
		this.status = status;
		this.msg = msg;
		this.tag = tag;
		this.context = context;
	}

	public NoticeEvent(int status, Object tag, Context context) {
		super();
		this.status = status;
		this.tag = tag;
		this.context = context;
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

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

}
