package com.jshain.dda.product.message;

import java.util.List;

public class DdaProductDelRs {

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
