package th.in.nagi.fecs.message;

/**
 * Use for fail message only.
 * 
 * @author Nara Surawit
 *
 */
public class FailureMessage extends Message {

	/**
	 * Information to send to client.
	 */
	private String message;

	public FailureMessage(String status, String message) {
		super(status);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
