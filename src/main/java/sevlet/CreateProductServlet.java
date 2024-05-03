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

@WebServlet("/createProduct")
public class CreateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public CreateProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispastcher=request.getServletContext().getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
		dispastcher.forward(request, response);	
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con=null;
		String errorString=null;
		try {
		 con=ConnectionUtils.getConnection();
		}
		catch(SQLException e) {
			errorString=e.getMessage();
		}
		catch(ClassNotFoundException e1) {
			errorString=e1.getMessage();
		}
		String code=(String)request.getParameter("code");
		String name=(String)request.getParameter("name");
		String priceStr=(String)request.getParameter("price");		
		float price=0;
		try {
			price=Float.parseFloat(priceStr);
		}catch(NumberFormatException e) {
			e.printStackTrace();
			errorString=e.getMessage();
		}
		Product product=new Product(code,name,price);
		String regex="\\w";
		if(code==null||code.matches(regex)) {
			errorString="Product Code invalid";
		}
		if(errorString==null) {
			try {
				DBUtils.insertProduct(con, product);
			}catch(SQLException e) {
				e.printStackTrace();
				errorString=e.getMessage();
			}
		}
		//luu thong tin vao requets truoc khi forward sang view
		request.setAttribute("errorString",errorString);
		request.setAttribute("product",product);
		//neu co loi forward sang trang "createProductView.jsp neu khong chuyen huong sang productList
		if(errorString!=null) {
			RequestDispatcher dispatcher=request.getServletContext().getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath()+"/productList");
		}
	}

}
