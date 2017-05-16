package by.galkina.game.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("serial")
public class TodayTag extends TagSupport {
    private String dateFormat;

    public void setFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

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

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}