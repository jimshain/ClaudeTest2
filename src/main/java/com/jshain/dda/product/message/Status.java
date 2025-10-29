package com.jshain.dda.product.message;

public class Status {
	private String code;
	private String severity;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns a Status object representing a successful operation.
	 * Code: 100, Severity: 0, Message: Success
	 *
	 * @return Status object with success values
	 */
	public static Status getSuccess() {
		Status status = new Status();
		status.setCode("100");
		status.setSeverity("0");
		status.setMessage("Success");
		return status;
	}

	/**
	 * Returns a Status object representing a successful operation.
	 * Code: 100, Severity: 0, Message: Success
	 *
	 * @return Status object with success values
	 */
	public static Status getFatalError() {
		Status status = new Status();
		status.setCode("999");
		status.setSeverity("999");
		status.setMessage("Process failed! Contact support.");
		return status;
	}

} // Class end