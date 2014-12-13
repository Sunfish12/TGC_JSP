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

import model.MemberBean;
import model.MemberDAO;

public class MemberDAO_JDBC implements MemberDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BoardGames";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "sa123456";

	private static final String SELECT_BY_USERNAME = "select * from member where username = ?";

	@Override
	public MemberBean findByPrimeKey(String username) {
		MemberBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_BY_USERNAME);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new MemberBean();
				result.setMemberId(rs.getInt("memberid"));
				result.setUsername(rs.getString("username"));
				result.setPswd(rs.getBytes("pswd"));
				result.setEmail(rs.getString("email"));
				result.setLastname(rs.getString("lastname"));
				result.setFirstname(rs.getString("firstname"));
				result.setGender(rs.getString("gender"));
				result.setNickname(rs.getNString("nickname"));
				result.setBirthday(rs.getDate("birthday"));
				result.setIdCard(rs.getString("idcard"));
				result.setJoinDate(rs.getDate("joindate"));
				result.setPhone(rs.getString("phone"));
				result.setMemberAddress(rs.getString("memberaddress"));
				result.setImgFileName(rs.getString("imgfilename"));

				// 圖片另存
				File f = new File("WebContent/imagesDB/image_member.jpg");

				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(f));
					byte[] b = rs.getBytes("memberimage");
					if (b != null) {
						bos.write(b, 0, (int) b.length);
						bos.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				result.setIsGroupBan(rs.getBoolean("isgroupban"));
				result.setIsCommentBan(rs.getBoolean("iscommentban"));
				result.setNotBanTime(rs.getDate("notbantime"));
				result.setBanTime(rs.getDate("bantime"));
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

	private static final String SELECT_ALL = "select * from member order by memberid";

	@Override
	public List<MemberBean> getAll() {

		List<MemberBean> result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();

			result = new ArrayList<MemberBean>();
			while (rs.next()) {
				MemberBean bean = new MemberBean();
				bean.setMemberId(rs.getInt("memberid"));
				bean.setUsername(rs.getString("username"));
				bean.setPswd(rs.getBytes("pswd"));
				bean.setEmail(rs.getString("email"));
				bean.setLastname(rs.getString("lastname"));
				bean.setFirstname(rs.getString("firstname"));
				bean.setGender(rs.getString("gender"));
				bean.setNickname(rs.getNString("nickname"));
				bean.setBirthday(rs.getDate("birthday"));
				bean.setIdCard(rs.getString("idcard"));
				bean.setJoinDate(rs.getDate("joindate"));
				bean.setPhone(rs.getString("phone"));
				bean.setMemberAddress(rs.getString("memberaddress"));
				bean.setImgFileName(rs.getString("imgfilename"));

				// 圖片另存
				File f = new File("WebContent/imagesDB/image_member.jpg");
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(f));
					byte[] b = rs.getBytes("memberimage");
					if (b != null) {
						bos.write(b, 0, (int) b.length);
						bos.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				bean.setIsGroupBan(rs.getBoolean("isgroupban"));
				bean.setIsCommentBan(rs.getBoolean("iscommentban"));
				bean.setNotBanTime(rs.getDate("notbantime"));
				bean.setBanTime(rs.getDate("bantime"));
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

	private static final String INSERT = "insert into Member (username, pswd, email, lastname,"
			+ " firstname, gender, nickname, birthday, idCard, joinDate, phone, memberAddress,"
			+ "imgFileName, memberImage, isGroupBan, isCommentBan, notBanTime, banTime)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null, null)";

	@Override
	public MemberBean insert(MemberBean bean, InputStream is, long size,
			String filename) {
		MemberBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, bean.getUsername());
			pstmt.setBytes(2, bean.getPswd());
			pstmt.setString(3, bean.getEmail());
			pstmt.setString(4, bean.getLastname());
			pstmt.setString(5, bean.getFirstname());
			pstmt.setString(6, bean.getGender());
			pstmt.setNString(7, bean.getNickname());

			// 接收birthday字串轉java.util.Date
			java.util.Date birthday = bean.getBirthday();
			if (birthday != null) {
				long temp = birthday.getTime();
				pstmt.setDate(8, new java.sql.Date(temp));
			} else {
				pstmt.setDate(8, null);
			}

			pstmt.setString(9, bean.getIdCard());

			// 接收joinDate字串轉java.util.Date
			java.util.Date joinDate = bean.getJoinDate();
			if (joinDate != null) {
				long temp = joinDate.getTime();
				pstmt.setDate(10, new java.sql.Date(temp));
			} else {
				pstmt.setDate(10, null);
			}

			pstmt.setString(11, bean.getPhone());
			pstmt.setString(12, bean.getMemberAddress());
			if (filename != null) {
				pstmt.setString(13, filename);
			} else {
				pstmt.setString(13, null);
			}

			// 準備存圖片
			if (is != null && size != 0) {
				pstmt.setBinaryStream(14, is, size);
			} else {
				pstmt.setBinaryStream(14, null, 0);
			}

			pstmt.setBoolean(15, false);
			pstmt.setBoolean(16, false);
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

	private static final String UPDATE = "update Member set pswd=?, email=?"
			+ ",lastname=?, firstname=?, gender=?, nickname=?, birthday=?, idCard=?, joinDate=?"
			+ ",phone=?, memberAddress=?, imgFileName=?, memberImage=?, isGroupBan=?, isCommentBan=?"
			+ ",notBanTime=?, banTime=? where username=?";

	@Override
	public MemberBean update(MemberBean bean, InputStream is, long size,
			String filename) {
		MemberBean result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setBytes(1, bean.getPswd());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getLastname());
			pstmt.setString(4, bean.getFirstname());
			pstmt.setString(5, bean.getGender());
			pstmt.setNString(6, bean.getNickname());

			// 接收birthday字串轉java.util.Date
			java.util.Date birthday = bean.getBirthday();
			if (birthday != null) {
				long temp = birthday.getTime();
				pstmt.setDate(7, new java.sql.Date(temp));
			} else {
				pstmt.setDate(7, null);
			}

			pstmt.setString(8, bean.getIdCard());

			// 接收joinDate字串轉java.util.Date
			java.util.Date joinDate = bean.getJoinDate();
			if (joinDate != null) {
				long temp = joinDate.getTime();
				pstmt.setDate(9, new java.sql.Date(temp));
			} else {
				pstmt.setDate(9, null);
			}

			pstmt.setString(10, bean.getPhone());
			pstmt.setString(11, bean.getMemberAddress());
			if (filename != null) {
				pstmt.setString(12, filename);
			} else {
				pstmt.setString(12, null);
			}

			// 準備改圖片
			if (is != null && size != 0) {
				pstmt.setBinaryStream(13, is, size);
			} else {
				pstmt.setBinaryStream(13, null, 0);
			}

			// 修改getIsGroupBan
			if (bean.getIsGroupBan() != null) {
				pstmt.setBoolean(14, bean.getIsGroupBan());
			} else {
				pstmt.setBoolean(14, false);
			}

			// 修改getIsCommentBan
			if (bean.getIsCommentBan() != null) {
				pstmt.setBoolean(15, bean.getIsCommentBan());
			} else {
				pstmt.setBoolean(15, false);
			}

			// 接收notBanTime字串轉java.util.Date
			java.util.Date notBanTime = bean.getNotBanTime();
			if (notBanTime != null) {
				long temp = notBanTime.getTime();
				pstmt.setDate(16, new java.sql.Date(temp));
			} else {
				pstmt.setDate(16, null);
			}

			// 接收BanTime字串轉java.util.Date
			java.util.Date BanTime = bean.getBanTime();
			if (BanTime != null) {
				long temp = BanTime.getTime();
				pstmt.setDate(17, new java.sql.Date(temp));
			} else {
				pstmt.setDate(17, null);
			}
			pstmt.setString(18, bean.getUsername());

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

	private static final String DELETE = "delete from member where username=?";

	@Override
	public boolean delete(String username) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setString(1, username);
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
		MemberDAO dao = new MemberDAO_JDBC();

		// Insert
		// MemberBean bean1 = new MemberBean();
		// bean1.setUsername("sunfisher");
		// bean1.setPswd("Aa@123".getBytes());
		// bean1.setEmail("sunfisher@gmail.com");
		// bean1.setLastname("Freeman");
		// bean1.setFirstname("Gold");
		// bean1.setGender("male");
		// bean1.setNickname("戰士");
		// bean1.setBirthday(MemberBean.convertDate("1990-10-10"));
		// bean1.setIdCard("A1234567890");
		// bean1.setJoinDate(MemberBean.convertDate("2014-10-10"));
		// bean1.setPhone("0911222333");
		// bean1.setMemberAddress("新北市三重區集美街219號3樓");
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
		// dao.insert(bean1, is, size, filename1);
		//
		// MemberBean bean2 = new MemberBean();
		// bean2.setUsername("leemike");
		// bean2.setPswd("Bb&456".getBytes());
		// bean2.setEmail("superman@yahoo.com");
		// bean2.setLastname("李");
		// bean2.setFirstname("麥克");
		// bean2.setGender("male");
		// bean2.setNickname("超人");
		// bean2.setBirthday(MemberBean.convertDate("1911-01-01"));
		// bean2.setIdCard("Z0987654321");
		// bean2.setJoinDate(MemberBean.convertDate("2001-12-12"));
		// bean2.setPhone("0988777666");
		// bean2.setMemberAddress("臺北市大安區復興南路一段390號3樓");
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
		// dao.insert(bean2, is1, size1, filename2);

		// Update
		// MemberBean bean3 = new MemberBean();
		// bean3.setUsername("");
		// bean3.setPswd("Cc#789".getBytes());
		// bean3.setEmail("hi@hello.com");
		// bean3.setLastname("鹹蛋");
		// bean3.setFirstname("超人");
		// bean3.setGender("female");
		// bean3.setNickname("完美女超人");
		// bean3.setBirthday(MemberBean.convertDate("1984-01-01"));
		// bean3.setIdCard("Z0987654321");
		// bean3.setJoinDate(MemberBean.convertDate("2011-02-21"));
		// bean3.setPhone("0900455357");
		// bean3.setMemberAddress("高雄市");
		// bean3.setIsGroupBan(true);
		// bean3.setIsCommentBan(true);
		// bean3 = dao.update(bean3);

		// Delete By Username
		// boolean b = dao.delete("sunfisher");
		// System.out.println(b);

		// Select By Username
		// MemberBean bean1 = dao.findByPrimeKey("sunfisher");
		// System.out.println(bean1);

		// MemberBean bean2 = dao.findByPrimeKey("leemike");
		// System.out.println(bean2);

		// Select All
		List<MemberBean> beans = dao.getAll();
		System.out.println(beans);
	}
}
