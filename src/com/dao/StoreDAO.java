package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.Store;

public class StoreDAO {
	private static final String INSERT_QUERY = " INSERT INTO store VALUES (?,?,?,?,?)";
	private static final String UPDATE_QUERY = " UPDATE store SET name = ?, address = ?,latitude=?,longitude=? WHERE id = ?";
	private static final String DELETE_QUERY = " DELETE FROM store WHERE id = ?";
	private static final String STORE_LOCATOR_QUERY = "SELECT id, name,address,latitude,longitude,( 3959 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) + sin( radians(?) ) * sin( radians( latitude ) ) ) ) AS distance FROM store HAVING distance < ?";
	private static final String GET_ALL_STORES = "Select * from Store limit 20";
	
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
	
    public static List<Store> locateStore(double lat,double lng,int dist) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		List<Store> storeList = new ArrayList<Store>();
		try {
			pst = con.prepareStatement(STORE_LOCATOR_QUERY);
		    pst.setDouble(1, lat);
		    pst.setDouble(2, lng);
			pst.setDouble(3, lat);
			pst.setDouble(4, dist);
			ResultSet resultSet = pst.executeQuery();
		    while (resultSet.next()) {
              Store store = new Store();
              store.setId(resultSet.getInt("id"));
		      store.setName(resultSet.getString("name"));
		      store.setAddress(resultSet.getString("address"));
		      store.setLatitude(resultSet.getDouble("latitude"));
              store.setLongitude(resultSet.getDouble("longitude"));
              storeList.add(store);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.cleanUp(con,pst);
		}
		return storeList;
	}
    
    public static List<Store> getAllStores() {
		Connection con = DatabaseUtil.getConnection();
		Statement st = null;
		List<Store> storeList = new ArrayList<Store>();
		try {
			st = con.createStatement();
			ResultSet resultSet = st.executeQuery(GET_ALL_STORES);
		    while (resultSet.next()) {
              Store store = new Store();
              store.setId(resultSet.getInt("id"));
		      store.setName(resultSet.getString("name"));
		      store.setAddress(resultSet.getString("address"));
		      store.setLatitude(resultSet.getDouble("latitude"));
              store.setLongitude(resultSet.getDouble("longitude"));
              storeList.add(store);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.cleanUp(con,st);
		}
		return storeList;
	}
}
