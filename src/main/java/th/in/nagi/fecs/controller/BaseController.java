package th.in.nagi.fecs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * Base controllers for all controller classes.
 *
 * @author Chonnipa Kittisiriprasert
 *
 */
public class BaseController {

    /**
     *
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Gets message source.
     *
     * @return message source
     */
    protected MessageSource getMessageSource() {
        return messageSource;
    }

}
