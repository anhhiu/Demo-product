package sevlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DBUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import beans.Product;
import connect.ConnectionUtils;

/**
 * Servlet implementation class EditProductServlet
 */
@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public EditProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		String errorString = null;
		try {
			con = ConnectionUtils.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			errorString = e.getMessage();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		
		String code = request.getParameter("code");
		Product product = null;
		
		try {
			product = DBUtils.findProduct(con, code);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		if(errorString != null && product ==null) {
			response.sendRedirect(request.getServletPath()+"/productList");
			return;
		}
		
		request.setAttribute("erroString", errorString);
		request.setAttribute("product", product);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		String errorString = null;
		try {
			con = ConnectionUtils.getConnection();
		} catch (SQLException e) {
			errorString = e.getMessage();
		} catch (ClassNotFoundException e1) {
			errorString = e1.getMessage();
		}
		String code = (String) request.getParameter("code");
		Product product = null;
		Product product1=null;

		try {
			product = DBUtils.findProduct(con, code);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		if(product!=null) {			
			String name=(String)request.getParameter("name");
			String priceStr=(String)request.getParameter("price");
			
			float price=0;
			try { price=Float.parseFloat(priceStr);
			
		}catch(NumberFormatException e) {
			e.printStackTrace();
			errorString=e.getMessage();
			
		}
		 product1=new Product(code,name,price);
		}
		
		try {
			DBUtils.updateProduct(con, product1);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		// luu thong tin vao attribute truoc khi forward Views
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);
		// neu co loi forward lai trang edit
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/editproductView.jsp");
			dispatcher.forward(request, response);
		} else {
			// neu khong co loi Redirect sang trang danh sach san pham
			response.sendRedirect(request.getContextPath() + "/productList");
		}

	}

}
