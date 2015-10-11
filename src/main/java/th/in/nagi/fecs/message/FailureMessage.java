package th.in.nagi.fecs.message;

public class FailureMessage extends Message{
	
	private String message;
	public FailureMessage(String status, String message) {
		super(status);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
