package com.jshain.message;

import com.jshain.dda.product.message.Status;
import java.util.List;

/**
 * Base class for all response messages.
 * Contains the common status field for tracking operation results.
 */
public class MessageRs {

	private List<Status> status;

	public List<Status> getStatus() {
		return status;
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}

} // Class end
