package util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import beans.*;
import connect.ConnectionUtils;

public class DBUtils {
	
	public static List<Product> queryProduct(Connection con)throws SQLException{
		String sql="select * from product";
		PreparedStatement pstm=con.prepareStatement(sql);
		ResultSet rs=pstm.executeQuery();
		List<Product> list=new ArrayList<Product>();
		while(rs.next()) {
			Product pd=new Product(rs.getString("code"),rs.getString("Name"),rs.getFloat("Price"));
			list.add(pd);
	
		}
		return list;
	}
	public static Product findProduct(Connection con, String code) throws SQLException {
		String sql="select * from product where code=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1,code);
		ResultSet rs=pstm.executeQuery();
		if(rs.next()) {
			Product pd=new Product(code,rs.getString("Name"),rs.getFloat("Price"));
			return pd;
		}
		return null;
	}
	public static void updateProduct(Connection con, Product prd) throws SQLException {
		String sql="update product set name=?, price=? where code=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setFloat(2,prd.getPrice());
		pstm.setString(3, prd.getCode());
		pstm.setString(1, prd.getName());
		pstm.executeUpdate();
		
	}
	public static void insertProduct(Connection con, Product pd) throws SQLException{
		String sql="insert into product set code=?, name=?, price=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1,pd.getCode());
		pstm.setString(2, pd.getName());
		pstm.setFloat(3, pd.getPrice());
		pstm.executeUpdate();
	}
	public static void deleteProduct(Connection con,String code) throws SQLException{
		String sql="delete from product where code=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, code);
		pstm.execute();
	}
	public static void PrintListProduct(Connection con) throws SQLException,ClassNotFoundException {
		
			List list=queryProduct(con);
			for(int i=0;i<list.size();i++) {
				Product p=(Product)list.get(i);
				p.printlnInfor();
			}
			

	}
	
	public static void main(String args[]) {
		
		try {

			Connection con=ConnectionUtils.getConnection();
			PrintListProduct(con) ;
			//Product pd=new Product("ggg","Quan dui",9000);
			//updateProduct(con,pd);
			//deleteProduct(con, "ggg");
			//insertProduct(con, pd);
			System.out.println("sau khi update: ");
			PrintListProduct(con) ;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

}
	}

