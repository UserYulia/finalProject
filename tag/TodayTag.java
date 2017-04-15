package by.galkina.game.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Class TodayTag ...
 *
 * @author Виталий
 * Created on 19.06.2016
 */
@SuppressWarnings("serial")
public class TodayTag extends TagSupport {
    /** Field dateFormat  */
    private String dateFormat;

    /**
     * Method setFormat sets the format of this TodayTag object.
     *
     *
     *
     * @param dateFormat the format of this TodayTag object.
     *
     */
    public void setFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Method doStartTag ...
     * @return int
     * @throws javax.servlet.jsp.JspException when
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            Date today = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
            out.print(dateFormatter.format(today));

        } catch (IOException e) {
            throw new JspException("Error: " + e.getMessage());
        }
        return SKIP_BODY;
    }

    /**
     * Method doEndTag ...
     * @return int
     * @throws JspException when
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}