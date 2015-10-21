package th.in.nagi.fecs.message;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.BaseView;
import th.in.nagi.fecs.view.FurnitureDescriptionView;

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
	@JsonView(BaseView.Standardized.class)
	private String message;

	public ErrorMessage(String status, String message) {
		super(status);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
