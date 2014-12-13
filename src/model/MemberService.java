package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.dao.MemberDAO_JDBC;

public class MemberService {
	private MemberDAO dao = new MemberDAO_JDBC();

	public List<MemberBean> select(MemberBean bean) {
		List<MemberBean> result = null;
		if (bean != null && bean.getUsername() != null) {
			MemberBean temp = dao.findByPrimeKey(bean.getUsername());
			if (temp != null) {
				result = new ArrayList<MemberBean>();
				result.add(temp);
			}
		} else {
			result = dao.getAll();
		}
		return result;
	}

	public MemberBean register(MemberBean bean, InputStream is,
			long sizeInBytes, String filename) {
		MemberBean result = null;
		if (bean != null && is != null && sizeInBytes != 0 && filename != null) {
			result = dao.insert(bean, is, sizeInBytes, filename);
		} else if (bean != null) {
			result = dao.insert(bean, null, 0, null);
		}
		return result;
	}

	public MemberBean update(MemberBean bean, InputStream is, long sizeInBytes,
			String filename) {
		MemberBean result = null;
		if (bean != null && is != null && sizeInBytes != 0 && filename != null) {
			result = dao.insert(bean, is, sizeInBytes, filename);
		} else if (bean != null) {
			result = dao.insert(bean, null, 0, null);
		}
		return result;
	}

	public static void main(String[] args) {
		MemberService service = new MemberService();

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
		// service.register(bean1, is, size, filename1);
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
		// service.register(bean2, is1, size1, filename2);

		// Select All
		List<MemberBean> beans = service.select(null);
		System.out.println("beans=" + beans);
	}
}
