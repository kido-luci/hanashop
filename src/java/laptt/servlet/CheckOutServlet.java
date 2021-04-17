package laptt.servlet;

import laptt.shoppingCart.ShoppingCart;
import laptt.tblAccount.TblAccountDTO;
import laptt.tblBill.TblBillDAO;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    //jsp
    public static String LOGIN_JSP_PAGE = "view/jsp/login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url  = request.getContextPath();

        try {
            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                TblAccountDTO tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");
                if (tblAccountDTO != null) {
                    if (tblAccountDTO.getRoleId() == 2) {
                        ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("shoppingCart");
                        if (shoppingCart != null && shoppingCart.size() > 0) {
                            TblBillDAO tblBillDAO = new TblBillDAO();
                            tblBillDAO.insertBillToDatabase(tblAccountDTO.getUserId(), shoppingCart, true);
                        } else {
                            httpSession = request.getSession(true);
                            httpSession.setAttribute("notification", "Check out failed. Your shopping cart have no item!");
                        }
                    } else {
                        httpSession = request.getSession(true);
                        httpSession.setAttribute("notification", "Check out failed. You are not allowed to access this function!");
                    }

                    httpSession.removeAttribute("shoppingCart");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect(request.getContextPath());

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
