package cn.jeeweb.web.ebp.notice.base.websockt;

import java.util.UUID;
import com.alibaba.fastjson.JSONObject;


public class Message {

	private String id;

	public static final short TYPE_HANDSHAKE = 1;
	
	public static final short TYPE_HEARTBEAT = 2;
	
	public static final short TYPE_USER = 100;

	private short type;
	
	private Object data;

	public Message() {
	}
	
	public Message(String id, short type, Object data) {
		super();
		this.id = id;
		this.type = type;
		this.data = data;
	}
	
	public Message(short type, Object data) {
		super();
		this.id = UUID.randomUUID().toString();
		this.type = type;
		this.data = data;
	}

	public Message(Object data) {
		super();
		this.id = UUID.randomUUID().toString();
		this.type = TYPE_USER;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
