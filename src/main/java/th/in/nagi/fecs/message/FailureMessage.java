package th.in.nagi.fecs.message;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.BaseView;

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
	@JsonView(BaseView.Standardized.class)
	private String message;

	public FailureMessage(String status, String message) {
		super(status);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
