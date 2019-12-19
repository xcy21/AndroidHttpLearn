package Servlettest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servletdemo
 */
@WebServlet("/Servletdemo")
public class Servletdemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servletdemo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("request---="+req.getRequestURI());
		
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		Map<String,String> result = new HashMap<String,String>();
		
		String number=req.getParameter("number");
		String password=req.getParameter("passwd");
		
		if(number ==null||number.equals("")||password==null||password.equals("")) {
			System.out.println("用户名密码为空");
		}else if(password.equals("123") && number.equals("123"))
		{
			result.put("message", "登陆成功");
		}else{
			result.put("message", "登陆失败");
		}
		byte[] bytes = result.toString().getBytes("utf-8");
		resp.setContentLength(bytes.length);
		resp.getOutputStream().write(bytes);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}

}
