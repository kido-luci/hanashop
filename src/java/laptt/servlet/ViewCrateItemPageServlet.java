package laptt.servlet;

import laptt.tblAccount.TblAccountDTO;
import laptt.tblShoppingItemCategory.TblShoppingItemCategoryDAO;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "ViewCrateItemPageServlet", urlPatterns = {"/ViewCrateItemPageServlet"})
public class ViewCrateItemPageServlet extends HttpServlet {

    public static String CREATE_ITEM_JSP_PAGE = "view/jsp/createItem.jsp";
    public static String LOGIN_JSP_PAGE = "view/jsp/login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = LOGIN_JSP_PAGE;

        try {
            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                TblAccountDTO tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");
                if (tblAccountDTO != null) {
                    if (tblAccountDTO.getRoleId() == 1) {
                        TblShoppingItemCategoryDAO tblShoppingItemCategoryDAO = new TblShoppingItemCategoryDAO();
                        List<String> shoppingItemCategoryList = tblShoppingItemCategoryDAO.getShoppingItemCategoryList();
                        request.setAttribute("ShoppingItemCategoryList", shoppingItemCategoryList);

                        url = CREATE_ITEM_JSP_PAGE;
                    } else {
                        url = request.getContextPath();
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
