package laptt.servlet;

import laptt.tblAccount.TblAccountDTO;
import laptt.tblBill.TblBillDAO;
import laptt.tblShoppingItem.TblShoppingItemDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
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
@WebServlet(name = "ViewHistoryServlet", urlPatterns = {"/ViewHistoryServlet"})
public class ViewHistoryServlet extends HttpServlet {

    public static String LOGIN_JSP_PAGE = "view/jsp/login.jsp";
    public static String NOT_FOUND_JSP_PAGE = "view/jsp/notFound.jsp";
    public static String USER_HISTORY_JSP_PAGE = "view/jsp/userShoppingHistory.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //default url
        String url = LOGIN_JSP_PAGE;

        try {
            TblAccountDTO tblAccountDTO = null;

            HttpSession httpSession = request.getSession(false);

            if (httpSession != null) {
                tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");
            }

            if (tblAccountDTO != null) {
                //check user is customer or not
                if (tblAccountDTO.getRoleId() == 2) {
                    //if user is customer
                    TblBillDAO tblBillDAO = new TblBillDAO();
                    tblBillDAO.setTblBillDTOList(tblAccountDTO.getUserId());

                    request.setAttribute("userShoppingHistoryList", tblBillDAO.getTblBillDTOList());

                    TblShoppingItemDAO tblShoppingItemDAO = new TblShoppingItemDAO();
                    tblShoppingItemDAO.setTblShoppingItemDTOList();

                    url = USER_HISTORY_JSP_PAGE;
                } else {
                    url = request.getContextPath();
                }
            }
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (NamingException namingException) {
            namingException.printStackTrace();
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
