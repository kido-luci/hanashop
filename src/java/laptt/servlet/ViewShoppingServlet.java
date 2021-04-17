package laptt.servlet;

import laptt.tblAccount.TblAccountDTO;
import laptt.tblShoppingItem.TblShoppingItemDAO;
import laptt.tblShoppingItem.TblShoppingItemDTO;
import laptt.tblShoppingItemCategory.TblShoppingItemCategoryDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "ViewShoppingServlet", urlPatterns = {"/ViewShoppingServlet"})
public class ViewShoppingServlet extends HttpServlet {

    public static String SHOPPING_JSP_PAGE = "view/jsp/shopping.jsp";
    public static String SHOPPING_ADMIN_JSP_PAGE = "view/jsp/shoppingAdmin.jsp";
    public static String NOT_FOUND_JSP_PAGE = "view/jsp/notFound.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //default url
        String url = SHOPPING_JSP_PAGE;

        try {
            int shoppingPage = 1;
            int quantityOfShoppingPage = 1;
            float minPrice = 0;
            float maxPrice = 0;

            try {
                shoppingPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException | NullPointerException e) {
                shoppingPage = 1;
            }

            try {
                minPrice = Float.parseFloat(request.getParameter("minPriceInput"));
                maxPrice = Float.parseFloat(request.getParameter("maxPriceInput"));
            } catch (NumberFormatException | NullPointerException e) {
                minPrice = 0;
                maxPrice = 0;
            }

            String searchText = request.getParameter("txtSearchValue");
            searchText = searchText == null ? "" : searchText;

            String shoppingItemCategorySearch = request.getParameter("txtShoppingItemCategorySearch");
            shoppingItemCategorySearch = shoppingItemCategorySearch == null || shoppingItemCategorySearch.isEmpty() ? "All" : shoppingItemCategorySearch;

            TblAccountDTO tblAccountDTO = null;

            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");
            }

            TblShoppingItemDAO tblShoppingItemDAO = new TblShoppingItemDAO();

            if (tblAccountDTO != null && tblAccountDTO.getRoleId() == 1) {
                tblShoppingItemDAO.setTblShoppingItemDTOList(searchText, shoppingItemCategorySearch, minPrice, maxPrice, shoppingPage);

                url = SHOPPING_ADMIN_JSP_PAGE;
            } else {
                tblShoppingItemDAO.setTblShoppingItemDTOListUserRole(searchText, shoppingItemCategorySearch, minPrice, maxPrice, shoppingPage);
            }

            List<TblShoppingItemDTO> tblShoppingItemDTOList  = tblShoppingItemDAO.getTblShoppingItemDTOList();

            if (tblShoppingItemDTOList == null || tblShoppingItemDTOList.size() == 0) {
                request.setAttribute("information", "The favorites food you looking for is not found.");

                url = NOT_FOUND_JSP_PAGE;
            } else {
                TblShoppingItemCategoryDAO tblShoppingItemCategoryDAO = new TblShoppingItemCategoryDAO();
                List<String> shoppingItemCategoryList = tblShoppingItemCategoryDAO.getShoppingItemCategoryList();

                quantityOfShoppingPage = (tblShoppingItemDAO.getQuantityOfItem() % 6 > 0) ? ((tblShoppingItemDAO.getQuantityOfItem() / 6) + 1) : (tblShoppingItemDAO.getQuantityOfItem() / 6);

                request.setAttribute("ShoppingPage", shoppingPage);
                request.setAttribute("QuantityOfShoppingPage", quantityOfShoppingPage);
                request.setAttribute("ShoppingItemList", tblShoppingItemDTOList);
                request.setAttribute("ShoppingItemCategoryList", shoppingItemCategoryList);
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
