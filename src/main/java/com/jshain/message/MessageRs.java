package com.jshain.message;

import com.jshain.dda.product.message.Status;
import java.util.List;

/**
 * Base class for all response messages.
 * Contains common fields: rquid for tracking request identifier and status for operation results.
 */
public class MessageRs {

	private String rquid;
	private List<Status> status;

	public String getRquid() {
		return rquid;
	}

	public void setRquid(String rquid) {
		this.rquid = rquid;
	}

	public List<Status> getStatus() {
		return status;
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}

} // Class end
