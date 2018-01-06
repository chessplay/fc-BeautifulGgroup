package com.sskj;
import java.net.*;
import java.io.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
class HttpUp{
    public static int access(String URLString){
        try{
            StringBuffer response = new StringBuffer();
   
            HttpClient client = new HttpClient();  
            PostMethod method = new PostMethod(URLString);  
   
            //����Http Post���ݣ��������ϴ��ļ�
            File f=new File("E:/tomcat/download/test.txt");
            FileInputStream fi=new FileInputStream(f);
            InputStreamRequestEntity fr=new InputStreamRequestEntity(fi);
            method.setRequestEntity((RequestEntity)fr);
            try{  
                client.executeMethod(method); //��һ���Ͱ��ļ��ϴ���
                //�����Ƕ�ȡ��վ�ķ�����ҳ�������ϴ��ɹ�֮��� 
                if (method.getStatusCode() == HttpStatus.SC_OK){  
                    //��ȡΪ InputStream������ҳ������������ʱ���Ƽ�ʹ��  
                    BufferedReader reader = new BufferedReader(  
                            new InputStreamReader(method.getResponseBodyAsStream(),  
                                    "GBK"));  
                    String line;  
                    while ((line = reader.readLine()) != null){  
                            response.append(line);  
                    }  
                    reader.close();  
                }  
            }
            catch (IOException e){  
                System.out.println("ִ��HTTP Post����" + URLString + "ʱ�������쳣��");  
                e.printStackTrace();  
            }
            finally{  
                method.releaseConnection();  
            }  
            System.out.println("--------------------"+response.toString());  
            return 1;
         }
         catch (Exception e){
             e.printStackTrace();
             return -1;
         }
    }

    public static void main(String args[]){
        new HttpUp();
//        if(args.length > 0)
//            access(args[0], args[1]);
//        else
            access("http://localhost:8080/myhttp/upload.jsp");
    }
}