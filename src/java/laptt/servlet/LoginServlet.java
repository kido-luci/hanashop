package laptt.servlet;

import laptt.tblAccount.TblAccountDAO;
import laptt.tblAccount.TblAccountDTO;
import laptt.utilities.GooglePojo;
import laptt.utilities.GoogleUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    public static String LOGIN_JSP_PAGE = "view/jsp/login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //default login page
        String url = LOGIN_JSP_PAGE;

        try {
            TblAccountDTO tblAccountDTO = null;
            TblAccountDAO tblAccountDAO = new TblAccountDAO();

            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");
            }

            //check authenticated user is exist in httpsession or not
            if (tblAccountDTO != null) {
                url = request.getContextPath();
            } else {
                String loginType = request.getParameter("loginType");

                if (loginType != null && loginType.equals("loginWithAccount")) {
                    String userId = request.getParameter("txtUserId");
                    String password = request.getParameter("txtPassword");

                    //check validation for userId and password
                    if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
                        httpSession = request.getSession(true);
                        httpSession.setAttribute("notification", "Login failed! User id and password can't be blank.");

                        url = LOGIN_JSP_PAGE;
                    } else {
                        tblAccountDTO = tblAccountDAO.getTblAccountDTO(userId, password);

                        if (tblAccountDTO != null) {
                            httpSession = request.getSession(true);
                            httpSession.setAttribute("authenticatedUser", tblAccountDTO);
                            httpSession.setAttribute("notification", "Welcome " + tblAccountDTO.getFullName());
                            url = request.getContextPath();
                        } else {
                            httpSession = request.getSession(true);
                            httpSession.setAttribute("notification", "Login failed! Invalid user id or password.");

                            url = LOGIN_JSP_PAGE;
                        }
                    }
                } else {
                    String googleCode = request.getParameter("code");
                    if (googleCode != null && !googleCode.isEmpty()) {
                        String accessToken = GoogleUtils.getToken(googleCode);
                        GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

                        tblAccountDTO = tblAccountDAO.getTblAccountDTO(googlePojo.getEmail());

                        if (tblAccountDTO != null) {
                            httpSession = request.getSession(true);
                            httpSession.setAttribute("authenticatedUser", tblAccountDTO);
                            httpSession.setAttribute("notification", "Welcome " + tblAccountDTO.getFullName());

                            url = request.getContextPath();
                        } else {
                            httpSession = request.getSession(true);
                            httpSession.setAttribute("notification", "Login failed. Your google account is not allowed to log into the system");

                            url = LOGIN_JSP_PAGE;
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (url.equals(request.getContextPath())) {
                response.sendRedirect(url);
            } else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
                requestDispatcher.forward(request, response);
            }

            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
