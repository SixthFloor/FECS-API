package th.in.nagi.fecs.message;

/**
 * Use for success message only.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public class SuccessMessage extends Message {

	/**
	 * Information to send to client.
	 */
	private Object data;

	public SuccessMessage(String status, Object data) {
		super(status);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
