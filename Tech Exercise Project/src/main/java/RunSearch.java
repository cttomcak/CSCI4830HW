import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RunSearch")
public class RunSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public RunSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String username = request.getParameter("username");
      String date = request.getParameter("date");
      String daytime = request.getParameter("daytime");
      String distance = request.getParameter("distance");
      String runtime = request.getParameter("runtime");
      String route = request.getParameter("route");

      if (username == null || username.isEmpty()) {
         username = "%";
      } else {
         username = "%" + username + "%";
      }
      if (date == null || date.isEmpty()) {
         date = "%";
      } else {
         date = "%" + date + "%";
      }
      if (daytime == null || daytime.isEmpty()) {
          daytime = "%";
       } else {
          daytime = "%" + daytime + "%";
       }
      if (distance == null || distance.isEmpty()) {
         distance = "%";
      } else {
         distance = "%" + distance + "%";
      }
      if (runtime == null || runtime.isEmpty()) {
          runtime = "%";
       } else {
          runtime = "%" + runtime + "%";
       }
      if (route == null || route.isEmpty()) {
         route = "%";
      } else {
         route = "%" + route + "%";
      }

      search(username, date, daytime, distance, runtime, route, response);
   }

   void search(String username, String date, String daytime, String distance, String runtime, String route, HttpServletResponse response)
         throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Run Entries";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body>\n" + //
            "<header><h1 align=\"center\">" + title + "</h1></header>\n");

      out.println("<style>\r\n"
            + "        td {\r\n"
            + "            text-align: center;\r\n"
            + "            padding-top: 10px;\r\n"
            + "            padding-bottom: 10px;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        tr:nth-child(odd) {\r\n"
            + "            background-color: #CCCCCC;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        tr:nth-child(even) {\r\n"
            + "            background-color: #DDDDDD;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        tr {\r\n"
            + "            text-align: center;\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        tr:hover {\r\n"
            + "            background-color: #BBBBBB\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        body {\r\n"
            + "            background-color: #EEEEEE;\r\n"
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
            + "            width: 100%;\r\n"
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

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;

         String selectSQL = "SELECT * FROM runsTable WHERE USERNAME LIKE ? AND DATE LIKE ? AND DAYTIME LIKE ? AND DISTANCE LIKE ? AND RUNTIME LIKE ? AND ROUTE LIKE ?";
         preparedStatement = connection.prepareStatement(selectSQL);
         preparedStatement.setString(1, username);
         preparedStatement.setString(2, date);
         preparedStatement.setString(3, daytime);
         preparedStatement.setString(4, distance);
         preparedStatement.setString(5, runtime);
         preparedStatement.setString(6, route);

         ResultSet rs = preparedStatement.executeQuery();

         out.println("<section><table>");

         out.println("<tr>");
         out.println("<td><b>" + "Runner Name" + "</b></td>");
         out.println("<td><b>" + "Date" + "</b></td>");
         out.println("<td><b>" + "Time of Day" + "</b></td>");
         out.println("<td><b>" + "Distance (MI)" + "</b></td>");
         out.println("<td><b>" + "Run Duration" + "</b></td>");
         out.println("<td><b>" + "Route" + "</b></td>");
         out.println("</tr>");

         while (rs.next()) {
            // int id = rs.getInt("id");
            String USERNAME = rs.getString("USERNAME").trim();
            String DATE = rs.getString("DATE").trim();
            String DAYTIME = rs.getString("DAYTIME").trim();
            String DISTANCE = rs.getString("DISTANCE").trim();
            String RUNTIME = rs.getString("RUNTIME").trim();
            String ROUTE = rs.getString("ROUTE").trim();

            out.println("<tr>");
            out.println("<td>" + USERNAME + "</td>");
            out.println("<td>" + DATE + "</td>");
            out.println("<td>" + DAYTIME + "</td>");
            out.println("<td>" + DISTANCE + "</td>");
            out.println("<td>" + RUNTIME + "</td>");
            out.println("<td>" + ROUTE + "</td>");
            out.println("</tr>");
         }

         out.println("</table></section>");

         out.println("<footer><a href=/personalProject/runSearch.html>Search Run Entries</a> <br>");
         out.println("<a href=/personalProject/runInsert.html>Insert Run Entry</a> <br></footer>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   }

}
