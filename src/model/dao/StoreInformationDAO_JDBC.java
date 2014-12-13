package model.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.StoreInformationBean;
import model.StoreInformationDAO;

public class StoreInformationDAO_JDBC implements StoreInformationDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BoardGames";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "sa123456";

	private static final String SELECT_BY_ID = "select * from storeinformation where storeid = ?";

	@Override
	public StoreInformationBean findByPrimeKey(String storeId) {
		StoreInformationBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_BY_ID);
			pstmt.setString(1, storeId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new StoreInformationBean();
				result.setStoreUsername(rs.getString("storeusername"));
				result.setStoreId(rs.getInt("storeid"));
				result.setStoreName(rs.getString("storename"));
				result.setStoreAddress(rs.getString("storeaddress"));
				result.setImgFileName(rs.getString("imgfilename"));

				// 圖片另存
				File f = new File("WebContent/imagesDB/image_store.jpg");
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(f));
					byte[] b = rs.getBytes("storeimage");
					if (b != null) {
						bos.write(b, 0, (int) b.length);
						bos.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				result.setStoreTel(rs.getString("storetel"));
				result.setRentAreaCost(rs.getDouble("rentareacost"));
				result.setGroupUpperLimit(rs.getInt("groupupperlimit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static final String SELECT_ALL = "select * from storeinformation order by storeid";

	@Override
	public List<StoreInformationBean> getAll() {
		List<StoreInformationBean> result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();

			result = new ArrayList<StoreInformationBean>();
			while (rs.next()) {
				StoreInformationBean bean = new StoreInformationBean();
				bean.setStoreUsername(rs.getString("storeusername"));
				bean.setStoreId(rs.getInt("storeid"));
				bean.setStoreName(rs.getString("storename"));
				bean.setStoreAddress(rs.getString("storeaddress"));
				bean.setImgFileName(rs.getString("imgfilename"));

				// 圖片另存
				File f = new File("WebContent/imagesDB/image_store.jpg");
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(f));
					byte[] b = rs.getBytes("storeimage");
					if (b != null) {
						bos.write(b, 0, (int) b.length);
						bos.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				bean.setStoreTel(rs.getString("storetel"));
				bean.setRentAreaCost(rs.getDouble("rentareacost"));
				bean.setGroupUpperLimit(rs.getInt("groupupperlimit"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static final String INSERT = "insert into storeinformation (storeUsername,"
			+ " storeName, storeAddress, imgFileName, storeImage, storeTel, rentAreaCost,"
			+ " groupUpperLimit) values (?, ?, ?, ?, ?, ?, ?, ?)";

	@Override
	public StoreInformationBean insert(StoreInformationBean bean,
			InputStream is, long size, String filename) {
		StoreInformationBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, bean.getStoreUsername());
			pstmt.setString(2, bean.getStoreName());
			pstmt.setString(3, bean.getStoreAddress());
			if (filename != null) {
				pstmt.setString(4, filename);
			} else {
				pstmt.setString(4, null);
			}

			// 準備存圖片
			if (is != null && size != 0) {
				pstmt.setBinaryStream(5, is, size);
			} else {
				pstmt.setBinaryStream(5, null, 0);
			}

			pstmt.setString(6, bean.getStoreTel());
			pstmt.setDouble(7, bean.getRentAreaCost());
			pstmt.setInt(8, bean.getGroupUpperLimit());

			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = bean;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static final String UPDATE = "update storeinformation set storeName=?,"
			+ " storeAddress=?, imgFileName=?, storeImage=?, storeTel=?, rentAreaCost=?,"
			+ " groupUpperLimit=? where storeId=?";

	@Override
	public StoreInformationBean update(StoreInformationBean bean,
			InputStream is, long size, String filename) {
		StoreInformationBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, bean.getStoreName());
			pstmt.setString(2, bean.getStoreAddress());
			if (filename != null) {
				pstmt.setString(3, filename);
			} else {
				pstmt.setString(3, null);
			}

			// 準備存圖片
			if (is != null && size != 0) {
				pstmt.setBinaryStream(4, is, size);
			} else {
				pstmt.setBinaryStream(4, null, 0);
			}

			pstmt.setString(5, bean.getStoreTel());
			pstmt.setDouble(6, bean.getRentAreaCost());
			pstmt.setInt(7, bean.getGroupUpperLimit());
			pstmt.setInt(8, bean.getStoreId());

			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = bean;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static final String DELETE = "delete from storeinformation where storeid=?";

	@Override
	public boolean delete(String storeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setString(1, storeId);
			int i = pstmt.executeUpdate();
			if (i == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {

		StoreInformationDAO dao = new StoreInformationDAO_JDBC();

		// Insert
		// StoreInformationBean bean1 = new StoreInformationBean();
		// bean1.setStoreUsername("sunfisher");
		// bean1.setStoreName("瘋桌遊");
		// bean1.setStoreAddress("台北市松山區三民路102巷20號");
		// String filename1 = "boardgames.jpg";
		// bean1.setImgFileName(filename1);
		// File f = new File("WebContent/res/" + bean1.getImgFileName());
		// long size = 0;
		// InputStream is = null;
		// try {
		// size = f.length();
		// is = new FileInputStream(f);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		// bean1.setStoreTel("(02)2528-2765");
		// bean1.setRentAreaCost(120.0);
		// bean1.setGroupUpperLimit(50);
		// dao.insert(bean1, is, size, filename1);

		// Select All
		List<StoreInformationBean> beans = dao.getAll();
		System.out.println(beans);
	}
}
