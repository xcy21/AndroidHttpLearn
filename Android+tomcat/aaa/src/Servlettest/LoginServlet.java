package Servlettest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import mysqltest.DBUtils;
import testclass.BaseBean;
import testclass.UserBean;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
		doPost(req,resp);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String username = request.getParameter("username");//用于接收android前台的输入的值,此处参数必须要与你前台的值相对应
		String password = request.getParameter("password");
		
		if(username == null||username.equals("")||password == null||password.equals("")) {
			System.out.println("用户名或密码为空");
			return;
		}
		
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		BaseBean data = new BaseBean();
		UserBean userBean = new UserBean();
		if (dbUtils.isExistInDB(username, password)) { // 判断账号是否存在
	        data.setCode(0);
	        userBean.setUsername(username);
	        userBean.setPassword(password);
	        data.setData(userBean);
	        data.setMsg("登陆成功！");
	    } else {
	    	data.setCode(-1);
	    	data.setData(userBean);
	    	data.setMsg("登录失败!");
	    }
		response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		String json = gson.toJson(data);
		System.out.println(json);
		try {
	        response.getWriter().println(json); // 将json数据传给客户端
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        response.getWriter().close(); // 关闭这个流，不然会发生错误的
	    }	
	    dbUtils.closeConnect(); // 关闭数据库连接
	}
}
