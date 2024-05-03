package sevlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import util.DBUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;

import beans.Product;
import connect.ConnectionUtils;
import connect.*;

/**
 * Servlet implementation class ProductListServlet
 */
@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con=null;
		String errorString=null;
		try {
		 con=(Connection) ConnectionUtils.getConnection();
		}
		catch(SQLException e) {
			errorString=e.getMessage();
		}
		catch(ClassNotFoundException e1) {
			errorString=e1.getMessage();
		}
		
		List<Product> list=null;
		try {
			list=DBUtils.queryProduct(con);
			System.out.println("list ok");
		}catch(SQLException e) {
			e.printStackTrace();
			errorString=e.getMessage();
		}
		request.setAttribute("errorString",errorString);
		request.setAttribute("productList",list);
		if(errorString==null) {
		RequestDispatcher dispatcher=request.getServletContext().getRequestDispatcher("/WEB-INF/views/productListView.jsp");
		dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
