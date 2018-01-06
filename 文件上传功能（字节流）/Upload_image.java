import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Upload_image extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html");
		

		PrintWriter pw = response.getWriter();
		
		//回复给客户端一个信息	
		pw.println("receive!");
		//利用request对象返回客户端来的输入流
		try (ServletInputStream sis = request.getInputStream()) {
			
			OutputStream os = new FileOutputStream("E:/3.mp4");
			BufferedOutputStream bos = new BufferedOutputStream(os);
			
			byte[] buf= new byte[1024];
			int length = 0;
			length = sis.readLine(buf, 0, buf.length);//使用sis的读取数据的方法
			while(length!=-1) {
				bos.write(buf, 0, length);
				length = sis.read(buf);
			}
			sis.close();
			bos.close();
			os.close();
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//转到doGet中做处理
		doGet(request, response);
	}

}

