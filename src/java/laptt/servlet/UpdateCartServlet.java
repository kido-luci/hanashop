package laptt.servlet;


import laptt.shoppingCart.ShoppingCart;
import laptt.tblAccount.TblAccountDTO;

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

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {

    //jsp
    public static String LOGIN_JSP_PAGE = "view/jsp/login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = request.getContextPath();
        try {
            TblAccountDTO tblAccountDTO = null;

            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");
            }

            if (tblAccountDTO != null) {
                if (tblAccountDTO.getRoleId() == 2) {
                    String updateCartItemMethod = request.getParameter("updateCartItemMethod");
                    if (updateCartItemMethod != null) {
                        if (updateCartItemMethod.equals("updateCartItemQuantity")) {
                            String cartItemId = request.getParameter("cartItemId");
                            int cartItemQuantity = Integer.parseInt(request.getParameter("cartItemQuantity"));

                            ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("shoppingCart");
                            //update quantity
                            int isUpdateCartItemQuantity = shoppingCart.updateItemQuantity(cartItemId, cartItemQuantity);
                            if (isUpdateCartItemQuantity == -1) {
                                httpSession.setAttribute("shoppingCart", shoppingCart);
                                httpSession = request.getSession(true);
                                httpSession.setAttribute("notification", "Update cart item successfully!");
                            } else {
                                httpSession = request.getSession(true);
                                httpSession.setAttribute("notification", "Update cart item failed! The quantity of the item in the stock: " + isUpdateCartItemQuantity + ".");
                            }

                            url = "DispatchServlet?btAction=viewCart";
                        } else if (updateCartItemMethod.equals("removeItemFromCart")) {
                            String cartItemId = request.getParameter("cartItemId");
                            ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("shoppingCart");
                            if (shoppingCart.removeItemFromCart(cartItemId)) {
                                httpSession.setAttribute("shoppingCart", shoppingCart);
                                httpSession = request.getSession(true);
                                httpSession.setAttribute("notification", "Removed item form cart successfully!");
                            }

                            url = "DispatchServlet?btAction=viewCart";
                        }
                    }
                } else {
                    httpSession = request.getSession(true);
                    httpSession.setAttribute("notification", "Update cart failed. You are not allowed to access this function!");
                }
            } else {
                url = LOGIN_JSP_PAGE;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
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
