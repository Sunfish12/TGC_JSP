package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.GlobalService;
import model.MemberBean;
import model.MemberService;

@WebServlet(urlPatterns = { "/RegisterServletMB" })
@MultipartConfig(location = "E:\\temp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024 * 1024 * 500 * 5)
public class RegisterServletMB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 準備存放錯誤訊息的Map物件
		request.setCharacterEncoding("UTF-8"); // 文字資料轉內碼
		Map<String, String> errorMsg = new HashMap<String, String>();
		// 準備存放註冊成功之訊息的Map物件
		Map<String, String> msgOK = new HashMap<String, String>();
		// 註冊成功後將用response.sendRedirect()導向新的畫面，所以需要
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		session.setAttribute("MsgOK", msgOK); // 顯示常訊息

		String username = "";
		String pswd = "";
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
		String filename = "";
		long sizeInBytes = 0;
		InputStream is = null;

		Collection<Part> parts = request.getParts(); // 取出HTTP multipart
														// request內所有的parts
		// GlobalService.exploreParts(parts, request);
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
					filename = GlobalService.getFileName(p); // 此為圖片檔的檔名
					if (filename != null && filename.trim().length() > 0) {
						sizeInBytes = p.getSize();
						is = p.getInputStream();
					} else {
						errorMsg.put("errPicture", "必須挑選圖片檔");
					}
				}
			}
			// 2. 進行必要的資料轉換
			// 3. 檢查使用者輸入資料
			if (username == null || username.trim().length() == 0) {
				errorMsg.put("errorUserNameEmpty", "帳號欄必須輸入");
			}
			if (pswd1 == null || pswd1.trim().length() == 0) {
				errorMsg.put("errorPswd1Empty", "密碼欄必須輸入");
			}
			if (pswd2 == null || pswd2.trim().length() == 0) {
				errorMsg.put("errorPswd2Empty", "密碼確認欄必須輸入");
			}
			if (pswd1.trim().length() > 0 && pswd2.trim().length() > 0) {
				if (!pswd1.trim().equals(pswd2.trim())) {
					errorMsg.put("errorPswd2Empty", "密碼欄必須與確認欄一致");
					errorMsg.put("errorPswd1Empty", "*");
				} else {
					pswd = pswd2.trim();
				}
			}
			if (email == null || email.trim().length() == 0) {
				errorMsg.put("errorEmail", "電子郵件欄必須輸入");
			}
		} else {
			errorMsg.put("errTitle", "此表單不是上傳檔案的表單");
		}
		// 如果有錯誤
		if (!errorMsg.isEmpty()) {
			// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
			RequestDispatcher rd = request
					.getRequestDispatcher("RegisterMember.jsp");
			rd.forward(request, response);
			return;
		}
		try {
			// 4. 進行Business Logic運算

			// 4.1.檢查帳號是否已經存在
			// 4.2.儲存會員的資料
			MemberService service = new MemberService();
			MemberBean bean = new MemberBean();
			bean.setUsername(username);
			if (service.select(bean) != null) {
				errorMsg.put("errorUserNameDup", "此帳號已存在，請換個帳號");
			} else {
				bean = new MemberBean();
				bean.setUsername(username);
				bean.setPswd(pswd.getBytes());
				bean.setEmail(email);
				bean.setLastname(lastname);
				bean.setFirstname(firstname);
				bean.setGender(gender);
				bean.setNickname(nickname);
				bean.setBirthday(MemberBean.convertDate(birthday));
				bean.setIdCard(idCard);
				bean.setJoinDate(MemberBean.convertDate(joinDate));
				bean.setPhone(phone);
				bean.setMemberAddress(memberAddress);
				// 將MemberBean bean立即寫入Database
				MemberBean result = service.register(bean, is, sizeInBytes,
						filename);
				if (result != null) {
					msgOK.put("InsertOK",
							"<Font color='red'>新增成功，請開始使用本系統</Font>");
					session.setAttribute("username", bean.getUsername());
					response.sendRedirect("Index.jsp");
					// RequestDispatcher rd = request
					// .getRequestDispatcher("ShowMyMemberData.jsp");
					// rd.forward(request, response);
					return;
				} else {
					errorMsg.put("errorUserNameDup",
							"新增此筆資料有誤(RegisterServletMB)");
				}
			}
			// 5.依照 Business Logic 運算結果來挑選適當的畫面
			if (!errorMsg.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
				RequestDispatcher rd = request
						.getRequestDispatcher("RegisterMember.jsp");
				rd.forward(request, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.put("errorIDDup", e.getMessage());
			RequestDispatcher rd = request
					.getRequestDispatcher("RegisterMember.jsp");
			rd.forward(request, response);
		}
	}
}
