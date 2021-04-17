package laptt.servlet;

import laptt.tblAccount.TblAccountDTO;
import laptt.tblShoppingItem.TblShoppingItemDAO;
import laptt.utilities.FileUtilities;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "CreateShoppingItemServlet", urlPatterns = {"/CreateShoppingItemServlet"})
public class CreateShoppingItemServlet extends HttpServlet {

    public static String LOGIN_JSP_PAGE = "view/jsp/login.jsp";
    public static String VIEW_CREATE_ITEM_PAGE_SERVLET = "ViewCrateItemPageServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //default url
        String url = VIEW_CREATE_ITEM_PAGE_SERVLET;

        HttpSession httpSession = request.getSession(false);
        
        try {
            if (httpSession != null) {
                TblAccountDTO tblAccountDTO = (TblAccountDTO) httpSession.getAttribute("authenticatedUser");
                if (tblAccountDTO != null) {
                    if (tblAccountDTO.getRoleId() == 1) {
                        String shoppingItemName = request.getParameter("txtShoppingItemName");
                        String shoppingItemCategory = request.getParameter("txtShoppingItemCategory");
                        String shoppingItemDescription = request.getParameter("txtShoppingItemDescription");

                        if (shoppingItemName != null && !shoppingItemName.isEmpty() && shoppingItemCategory != null && !shoppingItemCategory.isEmpty() && shoppingItemDescription != null && !shoppingItemDescription.isEmpty()) {
                            float shoppingItemPrice = Float.parseFloat(request.getParameter("txtShoppingItemPrice"));
                            int shoppingItemQuantity = Integer.parseInt(request.getParameter("txtShoppingItemQuantity"));

                            if (shoppingItemPrice <= 0 || shoppingItemQuantity < 0) {
                                throw new Exception();
                            }

                            String imagePath = "";
                            String saveDirectory = "assets/img/";

                            String appPath = request.getServletContext().getRealPath("");
                            appPath = appPath.replace("\\","/");

                            String fullSavePath = appPath.endsWith("/") ? appPath + saveDirectory : appPath + "/" + saveDirectory;

                            File fileSaveDir = new File(fullSavePath);
                            if (!fileSaveDir.exists()) {
                                fileSaveDir.mkdir();
                            }

                            Part part = request.getPart("uploadfile");
                            if (part != null) {
                                String fileName = FileUtilities.extractFileName(part);
                                if (fileName != null && fileName.length() > 0 && (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg"))) {
                                    String filePath = fullSavePath + File.separator + fileName;
                                    part.write(filePath);

                                    imagePath = saveDirectory + File.separator + fileName;
                                }
                            }

                            TblShoppingItemDAO tblShoppingItemDAO = new TblShoppingItemDAO();
                            tblShoppingItemDAO.createTblShoppingItemDTOInDatabase(shoppingItemName, imagePath, shoppingItemCategory, shoppingItemDescription, shoppingItemQuantity, shoppingItemPrice);

                            httpSession = request.getSession(true);
                            httpSession.setAttribute("notification", "Create shopping item successfully!");

                            url = request.getContextPath();
                        } else {
                            throw new Exception();
                        }
                    } else {
                        httpSession = request.getSession(true);
                        httpSession.setAttribute("notification", "Create shopping item failed! Access denied.");

                        url = request.getContextPath();
                    }
                } else {
                    url = LOGIN_JSP_PAGE;
                }
            } else {
                url = LOGIN_JSP_PAGE;
            }
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            httpSession = request.getSession(true);
            httpSession.setAttribute("notification", "Create shopping item failed! Invalid input.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            httpSession = request.getSession(true);
            httpSession.setAttribute("notification", "Create shopping item failed! Invalid input.");
        } catch (NamingException e) {
            e.printStackTrace();
            httpSession = request.getSession(true);
            httpSession.setAttribute("notification", "Create shopping item failed! Invalid input.");
        } catch (Exception e) {
            e.printStackTrace();
            httpSession = request.getSession(true);
            httpSession.setAttribute("notification", "Create shopping item failed! Invalid input.");
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
