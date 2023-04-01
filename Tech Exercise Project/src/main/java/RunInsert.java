
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RunInsert")
public class RunInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public RunInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String username = request.getParameter("username");
      String date = request.getParameter("date");
      String daytime = request.getParameter("daytime");
      String distance = request.getParameter("distance");
      String runtime = request.getParameter("runtime");
      String route = request.getParameter("route");

      Connection connection = null;
      String insertSql = " INSERT INTO runsTable (USERNAME,DATE,DAYTIME,DISTANCE,RUNTIME,ROUTE) values (?,?,?,?,?,?)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, username);
         preparedStmt.setString(2, date);
         preparedStmt.setString(3, daytime);
         preparedStmt.setString(4, distance);
         preparedStmt.setString(5, runtime);
         preparedStmt.setString(6, route);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Your Run Entry Has Been Submitted";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body>\n" + //
            "<header><h2 align=\"center\">" + title + "</h2></header>\n");

      out.println("<style>\r\n"
            + "        body {\r\n"
            + "            background-color: #EEEEEE;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        td {\r\n"
            + "            text-align: right;\r\n"
            + "            padding-top: 1px;\r\n"
            + "            padding-bottom: 1px;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        .left {\r\n"
            + "            text-align: left;\r\n"
            + "            padding-top: 1px;\r\n"
            + "            padding-bottom: 1px;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        header {\r\n"
            + "            background-color: #CCCCCC;\r\n"
            + "            color: black;\r\n"
            + "            width: 100%;\r\n"
            + "            text-align: center;\r\n"
            + "            padding-top: 10px;\r\n"
            + "            padding-bottom: 10px;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        section {\r\n"
            + "            width: 100%;\r\n"
            + "            text-align: center;\r\n"
            + "            padding-top: 10px;\r\n"
            + "            padding-bottom: 10px;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        table {\r\n"
            + "            width: auto;\r\n"
            + "            margin-left: auto;\r\n"
            + "            margin-right: auto;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        footer {\r\n"
            + "            background-color: #CCCCCC;\r\n"
            + "            color: black;\r\n"
            + "            width: 100%;\r\n"
            + "            text-align: center;\r\n"
            + "            padding-top: 10px;\r\n"
            + "            padding-bottom: 10px;\r\n"
            + "        }\r\n"
            + "    </style>");

      out.println(docType + //
            "<section><table>\n" + //
            "  <tr><td><b>Runner Name</b>:</td> <td class=\"left\">" + username + "</td></tr>\n" + //
            "  <tr><td><b>Date</b>:</td> <td class=\"left\">" + date + "</td></tr>\n" + //
            "  <tr><td><b>Time of Day</b>:</td> <td class=\"left\">" + daytime + "</td></tr>\n" + //
            "  <tr><td><b>Distance (MI)</b>:</td> <td class=\"left\">" + distance + "</td></tr>\n" + //
            "  <tr><td><b>Run Duration</b>:</td> <td class=\"left\">" + runtime + "</td></tr>\n" + //
            "  <tr><td><b>Route</b>:</td> <td class=\"left\">" + route + "</td></tr>\n" + //
            "</table></section>\n");

      out.println("<footer><a href=/personalProject/runSearch.html>Search Run Entries</a> <br>");
      out.println("<a href=/personalProject/runInsert.html>Insert Run Entry</a> <br></footer>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   }

}
