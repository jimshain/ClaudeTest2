package com.jshain.dda.product.message;

public class Status {
	// Severity level constants
	public static final String SEVERITY_INFO = "INFO";
	public static final String SEVERITY_WARN = "WARN";
	public static final String SEVERITY_ERROR = "ERROR";
	public static final String SEVERITY_FATAL = "FATAL";

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
	 * Code: 100, Severity: INFO, Message: Success
	 *
	 * @return Status object with success values
	 */
	public static Status getSuccess() {
		Status status = new Status();
		status.setCode("100");
		status.setSeverity(SEVERITY_INFO);
		status.setMessage("Success");
		return status;
	}

	/**
	 * Returns a Status object representing a fatal error.
	 * Code: 999, Severity: FATAL, Message: Process failed! Contact support.
	 *
	 * @return Status object with fatal error values
	 */
	public static Status getFatalError() {
		Status status = new Status();
		status.setCode("999");
		status.setSeverity(SEVERITY_FATAL);
		status.setMessage("Process failed! Contact support.");
		return status;
	}

} // Class end