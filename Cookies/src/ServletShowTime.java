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
	// 设置 存放上次访问信息的 cookie 键: lastTime  值: ???
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(321);
		// 设置 解码方式
		response.setContentType("text/html;charset=utf-8");
		// 获取所有的 cookie
		Cookie[] cookies = request.getCookies();
		// 判断 如果 cookies 不为空,并且获取到了cookie元素
		if (cookies != null && cookies.length > 0) {
			// 判断是否存在 目标 cookie
			for (Cookie cookie : cookies) {
				// 说明之前访问过
				if ("lastTime".equals(cookie.getName())) {

					System.out.println("打印前");
					// 向浏览器界面写入
					String ved = "欢迎回来 您上次访问时间为" + cookie.getValue();
					// 转码 防止出现乱码
					response.getWriter().write(decoding(encoding(ved)));
					System.out.println("打印后");

					// 设置时间
					// 时间 字符串
					Date date = new Date();
					String lastDate = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒").format(date);
					// 保存本次访问的时间
					cookie.setValue(encoding(lastDate));
					response.addCookie(cookie);

					break;
				}
			}
		}else {
			System.out.println("第一次前");
			// 说明之前没被访问过
			String v = "您好，欢迎您首次访问";
			response.getWriter().write(encoding(encoding(v)));
			System.out.println("第一次后");

			// 设置时间
			Date date = new Date();
			String lastDate = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒").format(date);
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
