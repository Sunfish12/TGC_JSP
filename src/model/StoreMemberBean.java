package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StoreMemberBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer storeMemberId;
	private String storeUsername;
	private byte[] storePswd;
	private java.util.Date storeJoinDate;
	private String storePhone;
	private String imgFileName;
	private byte[] storeImage;
	private String storeEmail;
	private String storeWebsite;

	public StoreMemberBean() {
	}

	@Override
	public String toString() {
		return "[" + storeMemberId + "," + storeUsername + "," + storeJoinDate
				+ "," + storePhone + "," + imgFileName + "," + storeEmail + ","
				+ storeWebsite + "]";
	}

	private static SimpleDateFormat sdFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static java.util.Date convertDate(String data) {
		java.util.Date result = null;
		try {
			result = sdFormat.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			result = new java.util.Date(0);
		}
		return result;
	}

	public static int convertInt(String data) {
		int result = 0;
		try {
			result = Integer.parseInt(data);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = -1000;
		}
		return result;
	}

	public Integer getStoreMemberId() {
		return storeMemberId;
	}

	public void setStoreMemberId(Integer storeMemberId) {
		this.storeMemberId = storeMemberId;
	}

	public String getStoreUsername() {
		return storeUsername;
	}

	public void setStoreUsername(String storeUsername) {
		this.storeUsername = storeUsername;
	}

	public byte[] getStorePswd() {
		return storePswd;
	}

	public void setStorePswd(byte[] storePswd) {
		this.storePswd = storePswd;
	}

	public java.util.Date getStoreJoinDate() {
		return storeJoinDate;
	}

	public void setStoreJoinDate(java.util.Date storeJoinDate) {
		this.storeJoinDate = storeJoinDate;
	}

	public String getStorePhone() {
		return storePhone;
	}

	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public byte[] getStoreImage() {
		return storeImage;
	}

	public void setStoreImage(byte[] storeImage) {
		this.storeImage = storeImage;
	}

	public String getStoreEmail() {
		return storeEmail;
	}

	public void setStoreEmail(String storeEmail) {
		this.storeEmail = storeEmail;
	}

	public String getStoreWebsite() {
		return storeWebsite;
	}

	public void setStoreWebsite(String storeWebsite) {
		this.storeWebsite = storeWebsite;
	}
}
