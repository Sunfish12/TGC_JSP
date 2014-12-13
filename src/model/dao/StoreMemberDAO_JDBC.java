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

import model.StoreMemberBean;
import model.StoreMemberDAO;

public class StoreMemberDAO_JDBC implements StoreMemberDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BoardGames";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "sa123456";

	private static final String SELECT_BY_STOREUSERNAME = "select * from storemember where storeusername = ?";

	@Override
	public StoreMemberBean findByPrimeKey(String storeUsername) {
		StoreMemberBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_BY_STOREUSERNAME);
			pstmt.setString(1, storeUsername);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new StoreMemberBean();
				result.setStoreMemberId(rs.getInt("storememberid"));
				result.setStoreUsername(rs.getString("storeusername"));
				result.setStorePswd(rs.getBytes("storepswd"));
				result.setStoreJoinDate(rs.getDate("storejoindate"));
				result.setStorePhone(rs.getString("storephone"));
				result.setImgFileName(rs.getString("imgfilename"));

				// 圖片另存
				File f = new File("WebContent/imagesDB/image_storeMember.jpg");
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(f));
					byte[] b = rs.getBytes("storeImage");
					if (b != null) {
						bos.write(b, 0, (int) b.length);
						bos.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				result.setStoreEmail(rs.getString("storeemail"));
				result.setStoreWebsite(rs.getString("storewebsite"));
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

	private static final String SELECT_ALL = "select * from storemember order by storememberid";

	@Override
	public List<StoreMemberBean> getAll() {
		List<StoreMemberBean> result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();

			result = new ArrayList<StoreMemberBean>();
			while (rs.next()) {
				StoreMemberBean bean = new StoreMemberBean();
				bean.setStoreMemberId(rs.getInt("storememberid"));
				bean.setStoreUsername(rs.getString("storeusername"));
				bean.setStorePswd(rs.getBytes("storepswd"));
				bean.setStoreJoinDate(rs.getDate("storejoindate"));
				bean.setStorePhone(rs.getString("storephone"));
				bean.setImgFileName(rs.getString("imgfilename"));

				// 圖片另存
				File f = new File("WebContent/imagesDB/image_storeMember.jpg");
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(f));
					byte[] b = rs.getBytes("storeImage");
					if (b != null) {
						bos.write(b, 0, (int) b.length);
						bos.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				bean.setStoreEmail(rs.getString("storeemail"));
				bean.setStoreWebsite(rs.getString("storewebsite"));
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

	private static final String INSERT = "insert into StoreMember (storeUsername, storePswd,"
			+ " storeJoinDate, storePhone, imgFileName, storeImage, storeEmail,"
			+ " storeWebsite) values (?, ?, ?, ?, ?, ?, ?, ?)";

	@Override
	public StoreMemberBean insert(StoreMemberBean bean, InputStream is,
			long size, String filename) {
		StoreMemberBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, bean.getStoreUsername());
			pstmt.setBytes(2, bean.getStorePswd());

			// 接收storeJoinDate字串轉java.util.Date
			java.util.Date storeJoinDate = bean.getStoreJoinDate();
			if (storeJoinDate != null) {
				long temp = storeJoinDate.getTime();
				pstmt.setDate(3, new java.sql.Date(temp));
			} else {
				pstmt.setDate(3, null);
			}

			pstmt.setString(4, bean.getStorePhone());
			if (filename != null) {
				pstmt.setString(5, filename);
			} else {
				pstmt.setString(5, null);
			}

			// 準備存圖片
			if (is != null && size != 0) {
				pstmt.setBinaryStream(6, is, size);
			} else {
				pstmt.setBinaryStream(6, null, 0);
			}

			pstmt.setString(7, bean.getStoreEmail());
			pstmt.setString(8, bean.getStoreWebsite());

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

	private static final String UPDATE = "update StoreMember set storePswd=?,"
			+ " storeJoinDate=?, storePhone=?, imgFileName=?, storeImage=?, storeEmail=?,"
			+ " storeWebsite=? where storeUsername=?";

	@Override
	public StoreMemberBean update(StoreMemberBean bean, InputStream is,
			long size, String filename) {
		StoreMemberBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setBytes(1, bean.getStorePswd());

			// 接收storeJoinDate字串轉java.util.Date
			java.util.Date storeJoinDate = bean.getStoreJoinDate();
			if (storeJoinDate != null) {
				long temp = storeJoinDate.getTime();
				pstmt.setDate(2, new java.sql.Date(temp));
			} else {
				pstmt.setDate(2, null);
			}

			pstmt.setString(3, bean.getStorePhone());
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

			pstmt.setString(6, bean.getStoreEmail());
			pstmt.setString(7, bean.getStoreWebsite());
			pstmt.setString(8, bean.getStoreUsername());

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

	private static final String DELETE = "delete from storemember where storeusername=?";

	@Override
	public boolean delete(String storeUsername) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setString(1, storeUsername);
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
		StoreMemberDAO_JDBC dao = new StoreMemberDAO_JDBC();

		// Insert
		// StoreMemberBean bean1 = new StoreMemberBean();
		// bean1.setStoreUsername("sunfisher");
		// bean1.setStorePswd("Aa@123".getBytes());
		// bean1.setStoreJoinDate(StoreMemberBean.convertDate("2014-10-10"));
		// bean1.setStorePhone("0911222333");
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
		// bean1.setStoreEmail("sunfisher@gmail.com");
		// bean1.setStoreWebsite("http://www.boardgamesclub.com.tw");
		// dao.insert(bean1, is, size, filename1);
		//
		// StoreMemberBean bean2 = new StoreMemberBean();
		// bean2.setStoreUsername("leemike");
		// bean2.setStorePswd("Bb&456".getBytes());
		// bean2.setStoreJoinDate(StoreMemberBean.convertDate("2001-12-12"));
		// bean2.setStorePhone("0988777666");
		// String filename2 = "boardgames.jpg";
		// bean2.setImgFileName(filename2);
		// File f1 = new File("WebContent/res/" + bean2.getImgFileName());
		// long size1 = 0;
		// InputStream is1 = null;
		// try {
		// size1 = f1.length();
		// is1 = new FileInputStream(f1);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		// bean2.setStoreEmail("superman@yahoo.com");
		// bean2.setStoreWebsite("http://www.boardgamesclub.com.tw");
		// dao.insert(bean2, is1, size1, filename2);

		// Update
		// StoreMemberBean bean3 = new StoreMemberBean();
		// bean3.setStoreUsername("sunfisher");
		// bean3.setStorePswd("Cc#789".getBytes());
		// bean3.setStoreEmail("hi@hello.com");
		// bean3.setStoreJoinDate(StoreMemberBean.convertDate("2011-02-21"));
		// bean3.setStorePhone("0900455357");
		// long size3 = 0;
		// InputStream is3 = null;
		// String filename3 = null;
		// bean3 = dao.update(bean3, is3, size3, filename3);

		// Delete By StoreUsername
		// boolean b = dao.delete("sunfisher");
		// System.out.println(b);

		// Select By StoreUsername
		// StoreMemberBean bean1 = dao.findByPrimeKey("sunfisher");
		// System.out.println(bean1);
		// StoreMemberBean bean2 = dao.findByPrimeKey("leemike");
		// System.out.println(bean2);

		// Select All
		List<StoreMemberBean> beans = dao.getAll();
		System.out.println(beans);
	}
}
