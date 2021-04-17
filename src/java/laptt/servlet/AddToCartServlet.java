package laptt.servlet;

import laptt.shoppingCart.ShoppingCart;
import laptt.tblAccount.TblAccountDTO;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    //servlet
    public static String VIEW_SHOPPING_SERVLET = "ViewShoppingServlet";
    public static String LOGIN_SERVLET = "LoginServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = LOGIN_SERVLET;

        TblAccountDTO tblAccountDTO = null;

        HttpSession httpSession = request.getSession(false);

        if (httpSession != null) {
            tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");

            Object object = httpSession.getAttribute("lastSearchResource");
            String lastSearch = object == null ? "" : object.toString();
            url = !lastSearch.isEmpty() ? lastSearch : request.getContextPath();
        }

        if (tblAccountDTO != null) {
            //check user is customer or not
            if (tblAccountDTO.getRoleId() == 2) {
                String shoppingItemId = request.getParameter("shoppingItemId");
                if (shoppingItemId != null && !shoppingItemId.isEmpty()) {
                    int quantity;
                    try {
                        quantity = Integer.parseInt(request.getParameter("quantity"));
                    } catch (NullPointerException | NumberFormatException e) {
                        quantity = 1;
                    }

                    //Check cart
                    ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("shoppingCart");
                    if (shoppingCart == null) {
                        shoppingCart = new ShoppingCart();
                    }

                    shoppingCart.addItemToCart(shoppingItemId, quantity);
                    httpSession = request.getSession(true);
                    httpSession.setAttribute("shoppingCart", shoppingCart);
                    httpSession.setAttribute("notification", "Item added to cart successfully!");
                }
            } else {
                httpSession = request.getSession(true);
                httpSession.setAttribute("notification", "Item added to cart failed. You are not allowed to access this function!");
            }
        } else {
            url = LOGIN_SERVLET;
        }

        if (url.equals(request.getContextPath())) {
            response.sendRedirect(url);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
        }

        out.close();
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