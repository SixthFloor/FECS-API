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
	private Object data;
	private String httpCode;
	
	public ErrorMessage(String status, Object data, String httpCode) {
		super(status);
		this.data = data;
		this.httpCode = httpCode;
	}

	public Object getData() {
		return data;
	}

}
