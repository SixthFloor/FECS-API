package th.in.nagi.fecs.message;

public abstract class Message {
	
	public static String SUCCESS = "success";
	public static String FAIL = "fail";
	public static String ERROR = "error";
	
	private String status;
	
	public Message(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
