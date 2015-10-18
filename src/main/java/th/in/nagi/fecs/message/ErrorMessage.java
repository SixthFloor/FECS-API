package th.in.nagi.fecs.message;

/**
 * Use for error message only.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public class ErrorMessage extends Message {

	/**
	 * Information to send to client.
	 */
	private String message;

	public ErrorMessage(String status, String message) {
		super(status);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
