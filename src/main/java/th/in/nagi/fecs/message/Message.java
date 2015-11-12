package th.in.nagi.fecs.message;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.BaseView;

/**
 * Use for return message to client
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public class Message {

	public static String SUCCESS = "success";
	public static String FAIL = "fail";
	public static String ERROR = "error";

	/**
	 * Have 3 type. success, fail, error
	 */
	@JsonView(BaseView.Standardized.class)
	private String description;

	public Message(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
