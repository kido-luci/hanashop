package laptt.servlet;

import laptt.tblShoppingItem.TblShoppingItemDAO;
import laptt.tblShoppingItem.TblShoppingItemDTO;

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

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "ViewSingleItemServlet", urlPatterns = {"/ViewSingleItemServlet"})
public class ViewSingleItemServlet extends HttpServlet {

    public static String SINGLE_ITEM_JSP_PAGE = "view/jsp/singleItem.jsp";
    public static String NOT_FOUND_JSP_PAGE = "view/jsp/notFound.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //default page
        String url = SINGLE_ITEM_JSP_PAGE;

        try {
            String shoppingItemId = request.getParameter("shoppingItemId");
            if (shoppingItemId != null && shoppingItemId.length() > 0) {
                TblShoppingItemDAO tblShoppingItemDAO = new TblShoppingItemDAO();
                TblShoppingItemDTO tblShoppingItemDTO = tblShoppingItemDAO.getTblShoppingItemDTO(shoppingItemId);
                if (tblShoppingItemDTO != null) {
                    request.setAttribute("singleItem", tblShoppingItemDTO);
                } else {
                    request.setAttribute("information", "The favorites food you looking for is not found.");

                    url = NOT_FOUND_JSP_PAGE;
                }
            } else {
                request.setAttribute("information", "The favorites food you looking for is not found.");

                url = NOT_FOUND_JSP_PAGE;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            request.setAttribute("information", "The favorites food you looking for is not found.");

            url = NOT_FOUND_JSP_PAGE;
        } catch (NamingException e) {
            e.printStackTrace();
            request.setAttribute("information", "The favorites food you looking for is not found.");

            url = NOT_FOUND_JSP_PAGE;
        } finally {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
            requestDispatcher.forward(request, response);

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
