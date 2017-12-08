package com.common;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CountryTag extends TagSupport {

    private List<CountryDTO> countryList;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if (countryList != null) {
                for (CountryDTO c : countryList) {
                    out.println("<option value='" + c.getId() + "'>" + c.getName() + "</option>");
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return EVAL_PAGE;
    }

    /**
     * @return the countryList
     */
    public List<CountryDTO> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<CountryDTO> countryList) {
        this.countryList = countryList;
    }

}
