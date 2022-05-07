package org.tron.core.services.http;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.core.db.export.AccountExportAspect;

@Component
@Slf4j
public class ExportAccount0301Servlet extends HttpServlet {

  @Autowired
  private AccountExportAspect accountExportAspect;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      String token = request.getParameter("token");
      if (token == null || token.equals("")) {
        response.getWriter().println("pls set the token to export");
      }

      accountExportAspect.exportAccount0301(token);
      response.getWriter().println("waiting for export data...");
    } catch (Exception e) {
      logger.debug("Exception: {}", e.getMessage());
      try {
        response.getWriter().println(Util.printErrorMsg(e));
      } catch (IOException ioe) {
        logger.debug("IOException: {}", ioe.getMessage());
      }
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
  }
}