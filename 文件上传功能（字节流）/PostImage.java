package com.sskj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class PostImage {

	public static void main(String[] args) throws IOException {
		//����ط������Լ���ʵ����������ģ��������Լ���IP��ַ���Լ�����tomcat�еĹ��̲���
		//����ĵ�ַҪ��web.xml���е�����ƥ��
		String url = "http://localhost:8080/myhttp/Upload_image";
		String file="E:/tomcat/download/3.mp4";
		
		String result = doPost(url,file);
		System.out.println(result);
		
	}
		
	public static String doPost(String urlString,String file)
			throws IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);

		//try�����õ������������˾��Ƿ�������
		try (BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream())) {
			
			//��java��������Windows�����еģ�ͼƬ·�������������			
			InputStream is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			
			byte[] buf= new byte[1024];
			int length = 0;
			length = bis.read(buf);
			while(length!=-1) {
				bos.write(buf, 0, length);
				length = bis.read(buf);
			}
			bis.close();
			is.close();
			bos.close();
		}
	
		//�����Ƿ�����������з������ݵĻ����������õ�
		StringBuilder response = new StringBuilder();
		try (Scanner in = new Scanner(connection.getInputStream())) {
			while (in.hasNextLine()) {
				response.append(in.nextLine());
				response.append("\n");
			}
		} catch (IOException e) {
			if (!(connection instanceof HttpURLConnection))
				throw e;
			InputStream err = ((HttpURLConnection) connection).getErrorStream();
			if (err == null)
				throw e;
			Scanner in = new Scanner(err);
			response.append(in.nextLine());
			response.append("\n");
			in.close();
		}

		return response.toString();
	}

}

