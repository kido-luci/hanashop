package laptt.servlet;

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
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

    //servlet
    public static String VIEW_SHOPPING_SERVLET = "ViewShoppingServlet";
    public static String VIEW_CART_SERVLET = "ViewCartServlet";
    public static String ADD_TO_CART_SERVLET = "AddToCartServlet";
    public static String UPDATE_CART_SERVLET = "UpdateCartServlet";
    public static String LOGIN_SERVLET = "LoginServlet";
    public static String LOGOUT_SERVLET = "LogoutServlet";
    public static String CHECK_OUT_SERVLET = "CheckOutServlet";
    public static String VIEW_SINGLE_ITEM_SERVLET = "ViewSingleItemServlet";
    public static String UPDATE_SHOPPING_ITEM_SERVLET = "UpdateShoppingItemServlet";
    public static String CREATE_SHOPPING_ITEM_SERVLET = "CreateShoppingItemServlet";
    public static String VIEW_CREATE_ITEM_PAGE_SERVLET = "ViewCrateItemPageServlet";
    public static String VIEW_HISTORY_SERVLET = "ViewHistoryServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //default url
        String url = VIEW_SHOPPING_SERVLET;

        HttpSession httpSession = null;

        String button = request.getParameter("btAction");
        if (button != null) {
            switch (button) {
                case "login" :
                    url = LOGIN_SERVLET;
                    break;
                case "logout" :
                    url = LOGOUT_SERVLET;
                    break;
                case "searchItem" :
                    String uri = request.getRequestURI();
                    String resource = uri.replaceFirst(request.getContextPath() + "/", "");
                    String lastSearchResource = resource + "?" + request.getQueryString();
                    httpSession = request.getSession(true);
                    httpSession.setAttribute("lastSearchResource", lastSearchResource);
                    url = VIEW_SHOPPING_SERVLET;
                    break;
                case "viewCart" :
                    url = VIEW_CART_SERVLET;
                    break;
                case "addToCart" :
                    url = ADD_TO_CART_SERVLET;
                    break;
                case "updateCartItem" :
                    url = UPDATE_CART_SERVLET;
                    break;
                case "checkOut" :
                    url = CHECK_OUT_SERVLET;
                    break;
                case "viewSingleItem" :
                    url = VIEW_SINGLE_ITEM_SERVLET;
                    break;
                case "updateShoppingItem" :
                    url = UPDATE_SHOPPING_ITEM_SERVLET;
                    break;
                case "toCreateItemPage" :
                    url = VIEW_CREATE_ITEM_PAGE_SERVLET;
                    break;
                case "createShoppingItem" :
                    url = CREATE_SHOPPING_ITEM_SERVLET;
                    break;
                case "history" :
                    url = VIEW_HISTORY_SERVLET;
                    break;
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);

        out.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request laptt.servlet request
     * @param response laptt.servlet response
     * @throws ServletException if a laptt.servlet-specific error occurs
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
     * @param request laptt.servlet request
     * @param response laptt.servlet response
     * @throws ServletException if a laptt.servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the laptt.servlet.
     *
     * @return a String containing laptt.servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
