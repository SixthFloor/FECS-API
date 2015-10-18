package th.in.nagi.fecs.message;

/**
 * Use for return message to client
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public abstract class Message {

	public static String SUCCESS = "success";
	public static String FAIL = "fail";
	public static String ERROR = "error";

	/**
	 * Have 3 type. success, fail, error
	 */
	private String status;

	public Message(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
