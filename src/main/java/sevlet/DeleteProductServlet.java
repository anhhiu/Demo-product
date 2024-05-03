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

import connect.ConnectionUtils;

/**
 * Servlet implementation class DeleteProductServlet
 */
@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProductServlet() {
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
		 con=ConnectionUtils.getConnection();
		}
		catch(SQLException e) {
			errorString=e.getMessage();
		}
		catch(ClassNotFoundException e1) {
			errorString=e1.getMessage();
		}
		String code=request.getParameter("code");
		
		try {
			DBUtils.deleteProduct(con, code);
		}catch(SQLException e) {
			e.printStackTrace();
			errorString=e.getMessage();
		}
		//neu co loi  forward quay lai trang productView
		if(errorString!=null) {
			request.setAttribute("errorString",errorString);
			RequestDispatcher dispatcher=request.getServletContext().getRequestDispatcher("/WEB-INF/views/deleteProductErrorView.jsp");
			dispatcher.forward(request, response);
			
		}
		else {
			//neu khong co loi redirec lai trang ProduceListView.jsp
			response.sendRedirect(request.getContextPath()+"/productList");
			
		}	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
