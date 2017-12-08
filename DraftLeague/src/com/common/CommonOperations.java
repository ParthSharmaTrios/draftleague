package com.common;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonOperations {

    public static void dispach(HttpServletRequest req, HttpServletResponse resp, String viewPage, String title) throws ServletException, IOException {
        req.setAttribute("viewPage", viewPage);
        req.setAttribute("title", title);
        req.setAttribute("desc", title);
        req.setAttribute("keywords", title);
        ResourceBundle bundle = ResourceBundle.getBundle("meta");
        if (bundle.containsKey(title.toLowerCase() + ".title")) {
            title = title.toLowerCase();
            req.setAttribute("title", bundle.getString(title + ".title"));
            req.setAttribute("desc", bundle.getString(title + ".desc"));
            req.setAttribute("keywords", bundle.getString(title + ".keywords"));
        }
        req.getRequestDispatcher("./WEB-INF/jsps/main.jsp").forward(req, resp);
    }
}
