import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/ServletShowTime")
public class ServletShowTime extends HttpServlet {
	// ���� ����ϴη�����Ϣ�� cookie ��: lastTime  ֵ: ???
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(321);
		// ���� ���뷽ʽ
		response.setContentType("text/html;charset=utf-8");
		// ��ȡ���е� cookie
		Cookie[] cookies = request.getCookies();
		// �ж� ��� cookies ��Ϊ��,���һ�ȡ����cookieԪ��
		if (cookies != null && cookies.length > 0) {
			// �ж��Ƿ���� Ŀ�� cookie
			for (Cookie cookie : cookies) {
				// ˵��֮ǰ���ʹ�
				if ("lastTime".equals(cookie.getName())) {

					System.out.println("��ӡǰ");
					// �����������д��
					String ved = "��ӭ���� ���ϴη���ʱ��Ϊ" + cookie.getValue();
					// ת�� ��ֹ��������
					response.getWriter().write(decoding(encoding(ved)));
					System.out.println("��ӡ��");

					// ����ʱ��
					// ʱ�� �ַ���
					Date date = new Date();
					String lastDate = new SimpleDateFormat("yyyy��MM��dd�� hhʱmm��ss��").format(date);
					// ���汾�η��ʵ�ʱ��
					cookie.setValue(encoding(lastDate));
					response.addCookie(cookie);

					break;
				}
			}
		}else {
			System.out.println("��һ��ǰ");
			// ˵��֮ǰû�����ʹ�
			String v = "���ã���ӭ���״η���";
			response.getWriter().write(encoding(encoding(v)));
			System.out.println("��һ�κ�");

			// ����ʱ��
			Date date = new Date();
			String lastDate = new SimpleDateFormat("yyyy��MM��dd�� hhʱmm��ss��").format(date);
			Cookie cookie = new Cookie("lastTime",encoding(lastDate));
			response.addCookie(cookie);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(123);
		this.doPost(request, response);
	}

	private String encoding(String str) throws IOException {
		return URLEncoder.encode(str,"utf-8");
	}

	private String decoding(String str) throws IOException {
		return URLDecoder.decode(str,"utf-8");
	}
}
