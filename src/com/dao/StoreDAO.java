package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bean.Store;

public class StoreDAO {
	private static final String INSERT_QUERY = " INSERT INTO store VALUES (?,?,?,?,?)";
	private static final String UPDATE_QUERY = " UPDATE store SET name = ?, address = ?,latitude=?,longitude=? WHERE id = ?";
	private static final String DELETE_QUERY = " DELETE FROM store WHERE id = ?";
	
	public static int addStore(Store newStore) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		int result = 0;
		try {
			pst = con.prepareStatement(INSERT_QUERY);
			pst.setInt(1, newStore.getId());
			pst.setString(2, newStore.getName());
			pst.setString(3, newStore.getAddress());
			pst.setDouble(4, newStore.getLatitude());
			pst.setDouble(5, newStore.getLongitude());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.cleanUp(con,pst);
		}
		return result;
	}
	
	public static int updateStore(Store newStore) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		int result = 0;
		try {
			pst = con.prepareStatement(UPDATE_QUERY);
		    pst.setString(1, newStore.getName());
			pst.setString(2, newStore.getAddress());
			pst.setDouble(3, newStore.getLatitude());
			pst.setDouble(4, newStore.getLongitude());
			pst.setDouble(5, newStore.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.cleanUp(con,pst);
		}
		return result;
	}
	public static int deleteStore(int id) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		int result = 0;
		try {
			pst = con.prepareStatement(DELETE_QUERY);
		    pst.setInt(1, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.cleanUp(con,pst);
		}
		return result;
	}
}
