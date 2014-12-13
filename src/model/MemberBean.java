package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MemberBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer memberId;
	private String username;
	private byte[] pswd;
	private String email;
	private String lastname;
	private String firstname;
	private String gender;
	private String nickname;
	private java.util.Date birthday;
	private String idCard;
	private java.util.Date joinDate;
	private String phone;
	private String memberAddress;
	private String imgFileName;
	private byte[] memberImage;
	private Boolean isGroupBan;
	private Boolean isCommentBan;
	private java.util.Date notBanTime;
	private java.util.Date banTime;

	public MemberBean() {
	}

	@Override
	public String toString() {
		return "[" + memberId + "," + username + "," + email + "," + lastname
				+ "," + firstname + "," + gender + "," + nickname + ","
				+ birthday + "," + idCard + "," + joinDate + "," + phone + ","
				+ memberAddress + "," + imgFileName + "," + isGroupBan + ","
				+ isCommentBan + "," + notBanTime + "," + banTime + "]";
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

	public static double convertDouble(String data) {
		double result = 0;
		try {
			result = Double.parseDouble(data);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = -1000;
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

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPswd() {
		return pswd;
	}

	public void setPswd(byte[] pswd) {
		this.pswd = pswd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public java.util.Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(java.util.Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public byte[] getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(byte[] memberImage) {
		this.memberImage = memberImage;
	}

	public Boolean getIsGroupBan() {
		return isGroupBan;
	}

	public void setIsGroupBan(Boolean isGroupBan) {
		this.isGroupBan = isGroupBan;
	}

	public Boolean getIsCommentBan() {
		return isCommentBan;
	}

	public void setIsCommentBan(Boolean isCommentBan) {
		this.isCommentBan = isCommentBan;
	}

	public java.util.Date getNotBanTime() {
		return notBanTime;
	}

	public void setNotBanTime(java.util.Date notBanTime) {
		this.notBanTime = notBanTime;
	}

	public java.util.Date getBanTime() {
		return banTime;
	}

	public void setBanTime(java.util.Date banTime) {
		this.banTime = banTime;
	}
}
