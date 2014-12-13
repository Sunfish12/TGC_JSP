package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.GlobalService;

@WebServlet(urlPatterns = { "/abc" })
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024 * 1024 * 500 * 5)
public class RegisterServletMB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 準備存放錯誤訊息的Map物件
		Map<String, String> errorMsg = new HashMap<String, String>();
		// 準備存放註冊成功之訊息的Map物件
		Map<String, String> msgOK = new HashMap<String, String>();
		// 註冊成功後將用response.sendRedirect()導向新的畫面，所以需要
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		session.setAttribute("MsgOK", msgOK); // 顯示常訊息

		String username = "";
		String pswd1 = "";
		String pswd2 = "";
		String email = "";
		String lastname = "";
		String firstname = "";
		String gender = "";
		String nickname = "";
		String birthday = "";
		String idCard = "";
		String joinDate = "";
		String phone = "";
		String memberAddress = "";
		String fileName = "";
		long sizeInBytes = 0;
		InputStream is = null;

		Collection<Part> parts = request.getParts(); // 取出HTTP multipart
														// request內所有的parts
		GlobalService.exploreParts(parts, request);
		// 由parts != null來判斷此上傳資料是否為HTTP multipart request
		if (parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : parts) {
				String fldName = p.getName();
				String value = request.getParameter(fldName);
				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equals("username")) {
						username = value;
					} else if (fldName.equals("pswd1")) {
						pswd1 = value;
					} else if (fldName.equalsIgnoreCase("pswd2")) {
						pswd2 = value;
					} else if (fldName.equalsIgnoreCase("email")) {
						email = value;
					} else if (fldName.equalsIgnoreCase("lastname")) {
						lastname = value;
					} else if (fldName.equalsIgnoreCase("firstname")) {
						firstname = value;
					} else if (fldName.equalsIgnoreCase("gender")) {
						gender = value;
					} else if (fldName.equalsIgnoreCase("nickname")) {
						nickname = value;
					} else if (fldName.equalsIgnoreCase("birthday")) {
						birthday = value;
					} else if (fldName.equalsIgnoreCase("idCard")) {
						idCard = value;
					} else if (fldName.equalsIgnoreCase("joinDate")) {
						joinDate = value;
					} else if (fldName.equalsIgnoreCase("phone")) {
						phone = value;
					} else if (fldName.equalsIgnoreCase("memberAddress")) {
						memberAddress = value;
					}
				} else {
					fileName = GlobalService.getFileName(p); // 此為圖片檔的檔名
					if (fileName != null && fileName.trim().length() > 0) {
						sizeInBytes = p.getSize();
						is = p.getInputStream();
					} else {
						errorMsg.put("errPicture", "必須挑選圖片檔");
					}
				}
				// 2. 進行必要的資料轉換
				
			}
		}
	}
}
