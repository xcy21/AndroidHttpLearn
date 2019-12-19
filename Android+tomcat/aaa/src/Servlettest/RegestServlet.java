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

public class RegestServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
		doPost(req,resp);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String username = request.getParameter("username");//���ڽ���androidǰ̨�������ֵ,�˴���������Ҫ����ǰ̨��ֵ���Ӧ
		String password = request.getParameter("password");
//		String username = "xwr";
//		String password = "123456";
		if(username == null||username.equals("")||password == null||password.equals("")) {
			System.out.println("�û���������Ϊ��");
			return;
		}
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		BaseBean data = new BaseBean();
		UserBean userBean = new UserBean();
		if (dbUtils.isExistInDB(username, password)) { // �ж��˺��Ƿ����
	        data.setCode(-1);
	        data.setData(userBean);
	        data.setMsg("���˺��Ѵ���");
	    } else if (!dbUtils.insertDataToDB(username, password)) { // ע��ɹ�
	        data.setCode(0);
	        data.setMsg("ע��ɹ�����");
	        ResultSet rs = dbUtils.getUser();
	        int id = -1;
	        if (rs != null) {
	            try {
	                while (rs.next()) {
	                    if (rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
	                        id = rs.getInt("userID");
	                    }
	                }
	                userBean.setId(id);
	            } catch(SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        userBean.setUsername(username);
	        userBean.setPassword(password);
	        data.setData(userBean);
	    } else { // ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
	        data.setCode(500);
	        data.setData(userBean);
	        data.setMsg("���ݿ����");
	    }
	    Gson gson = new Gson();
	    String json = gson.toJson(data);  //������ת����json�ַ���
	    try {
	        response.getWriter().println(json); // ��json���ݴ����ͻ���
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
	    }
	    dbUtils.closeConnect(); // �ر����ݿ�����
	}
}
