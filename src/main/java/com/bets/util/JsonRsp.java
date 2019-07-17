package com.bets.util;

/**
 * 
 * @author 响应到用户前端
 *
 */
public class JsonRsp implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public boolean success; //标识
	public String msg; //消息
	public Object obj; //对象
	public String url; //访问url

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
