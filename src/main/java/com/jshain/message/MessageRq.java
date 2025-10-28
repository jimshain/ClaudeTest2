package com.jshain.message;

/**
 * Base class for all request messages.
 * Contains the common rquid field which is a unique string identifier for each request message.
 */
public class MessageRq {

	private String rquid;

	public String getRquid() {
		return rquid;
	}

	public void setRquid(String rquid) {
		this.rquid = rquid;
	}

} // Class end
