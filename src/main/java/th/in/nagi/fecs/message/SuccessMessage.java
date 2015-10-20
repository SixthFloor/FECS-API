package th.in.nagi.fecs.message;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.BaseView;

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
	@JsonView(BaseView.Standardized.class)
	private Object data;

	public SuccessMessage(String status, Object data) {
		super(status);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
