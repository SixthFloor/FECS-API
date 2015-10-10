package th.in.nagi.fecs.message;

public class ErrorMessage extends Message{

	private String message;
	public ErrorMessage(String status, String message) {
		super(status);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
	
}
