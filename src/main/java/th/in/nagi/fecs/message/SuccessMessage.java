package th.in.nagi.fecs.message;

public class SuccessMessage extends Message{

	private Object data;
	
	public SuccessMessage(String status, Object data) {
		super(status);
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
}
