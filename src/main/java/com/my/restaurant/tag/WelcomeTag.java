package com.my.restaurant.tag;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class WelcomeTag extends SimpleTagSupport {

    String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void doTag() throws IOException {
        getJspContext().getOut().write("<p style='font-size:30px'>*** " + name + " ***</p>");
    }
}
