package model;

public class StoreInformationBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String storeUsername;
	private Integer storeId;
	private String storeName;
	private String storeAddress;
	private String imgFileName;
	private byte[] storeImage;
	private String storeTel;
	private Double rentAreaCost;
	private Integer groupUpperLimit;

	public StoreInformationBean() {
	}

	@Override
	public String toString() {
		return "[" + storeUsername + "," + storeId + "," + storeName + ","
				+ storeAddress + "," + imgFileName + "," + storeTel + ","
				+ rentAreaCost + "," + groupUpperLimit + "]";
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

	public String getStoreUsername() {
		return storeUsername;
	}

	public void setStoreUsername(String storeUsername) {
		this.storeUsername = storeUsername;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
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

	public String getStoreTel() {
		return storeTel;
	}

	public void setStoreTel(String storeTel) {
		this.storeTel = storeTel;
	}

	public Double getRentAreaCost() {
		return rentAreaCost;
	}

	public void setRentAreaCost(Double rentAreaCost) {
		this.rentAreaCost = rentAreaCost;
	}

	public Integer getGroupUpperLimit() {
		return groupUpperLimit;
	}

	public void setGroupUpperLimit(Integer groupUpperLimit) {
		this.groupUpperLimit = groupUpperLimit;
	}
}
