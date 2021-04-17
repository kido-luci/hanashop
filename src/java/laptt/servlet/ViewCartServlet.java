package laptt.servlet;

import laptt.shoppingCart.ShoppingCart;
import laptt.tblAccount.TblAccountDTO;
import laptt.tblShoppingItem.TblShoppingItemDAO;
import laptt.tblShoppingItem.TblShoppingItemDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
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
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    public static String VIEW_CART_JSP_PAGE = "view/jsp/cart.jsp";
    public static String LOGIN_JSP_PAGE = "view/jsp/login.jsp";
    public static String NOT_FOUND_JSP_PAGE = "view/jsp/notFound.jsp";

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
                    ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("shoppingCart");
                    if (shoppingCart != null && shoppingCart.size() > 0) {
                        TblShoppingItemDAO tblShoppingItemDAO = new TblShoppingItemDAO();
                        tblShoppingItemDAO.setTblShoppingItemDTOList();

                        HashMap<String, TblShoppingItemDTO> shoppingItemHashMap = new HashMap<>();

                        Set<String> shoppingItemIdSet = shoppingCart.keySet();
                        for (TblShoppingItemDTO tblShoppingItemDTO : tblShoppingItemDAO.getTblShoppingItemDTOList()) {
                            String tmpShoppingIemId = tblShoppingItemDTO.getShoppingItemId();
                            for (String shoppingItemID : shoppingItemIdSet) {
                                if (tmpShoppingIemId.equals(shoppingItemID)) {
                                    shoppingItemHashMap.put(tmpShoppingIemId, tblShoppingItemDTO);
                                    break;
                                }
                            }
                        }
                        request.setAttribute("shoppingItemHashMap", shoppingItemHashMap);

                        url = VIEW_CART_JSP_PAGE;
                    } else {
                        request.setAttribute("information", "Your shopping cart have no item.");

                        url = NOT_FOUND_JSP_PAGE;
                    }
                } else {
                    httpSession = request.getSession(true);
                    httpSession.setAttribute("notification", "Access denied! You are not allowed to use this function.");

                    url = request.getContextPath();
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
