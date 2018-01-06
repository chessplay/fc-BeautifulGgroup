<%@ page  language="java" contentType="text/html; charset=GBK"
import = "java.io.*,java.lang.*,java.util.*,javax.servlet.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>---接收上传页面---</title>
<link href="css/BasicStyle.css" rel="stylesheet" type="text/css" />
<link href="css/table.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
    try
    {
        PrintWriter pw = new PrintWriter(
            new BufferedWriter(new FileWriter("test.txt")));
       
        ServletInputStream in = request.getInputStream();
        int i = in.read();
        while (i != -1)
        {
            pw.print((char) i);
            i = in.read();
        }
        pw.close();
        out.println("文件生成成功");
    }
    catch (Exception e)
    {
        out.println("文件生成失败，请查看后台日志");
        e.printStackTrace();
    }
%>
</body>
</html>