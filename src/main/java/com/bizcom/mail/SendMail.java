package com.bizcom.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet (urlPatterns = "/sendmail")
public class SendMail extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getContextPath());
		String rma = request.getParameter("rma");
		String row11 = request.getParameter("row1.1");
		String row12 = request.getParameter("row1.2");
		
		String row21 = request.getParameter("row2.1");
		String row22 = request.getParameter("row2.2");
		
		String row31 = request.getParameter("row3.1");
		String row32 = request.getParameter("row3.2");
		
		String row41 = request.getParameter("row4.1");
		String row42 = request.getParameter("row4.2");
		
		String row51 = request.getParameter("row5.1");
		String row52 = request.getParameter("row5.2");
		
		String row61 = request.getParameter("row6.1");
		String row62 = request.getParameter("row6.2");
		
		String row71 = request.getParameter("row7.1");
		String row72 = request.getParameter("row7.2");
		
		String row81 = request.getParameter("row8.1");
		String row82 = request.getParameter("row8.2");
		
		String row91 = request.getParameter("row9.1");
		String row92 = request.getParameter("row9.2");
		
		String row101 = request.getParameter("row10.1");
		String row102 = request.getParameter("row10.2");
		
		int count = 1;
		String message = "This RMA: "+ rma+" has the following parts requested:\n";
		HashMap<String,String> parts = new HashMap<String,String>();
		checkvalid(row11,row12,parts);
		checkvalid(row21,row22,parts);
		checkvalid(row31,row32,parts);
		checkvalid(row41,row42,parts);
		checkvalid(row51,row52,parts);
		checkvalid(row61,row62,parts);
		checkvalid(row71,row72,parts);
		checkvalid(row81,row82,parts);
		checkvalid(row91,row92,parts);
		checkvalid(row101,row102,parts);
		
		for(Map.Entry<String, String> e : parts.entrySet()) {
			message+= "PN: "+ e.getKey()+" Qty: "+e.getValue()+"\n";
		}
		String registerForm = "/WEB-INF/views/mail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(registerForm);
		dispatcher.forward(request, response);
		
		send("nhonquy2019@gmail.com", "1234567Aa@", "thanh@bizcom-us.com", "WUR Parts Request", message);
		//send("ray.biz1171@gmail.com","customerfirst","viet@bizcom-us.com","WUR Parts Request",message);
		
		
	
	}
	
	public static void checkvalid(String a,String b, HashMap<String,String> map) {
		if(a.length() != 0 || b.length() != 0) {
			map.put(a, b);
		}
	}

	public static void send(String from, String password, String to, String sub, String msg) {
		// Get properties object
		Properties props = new Properties();
		// smtp.gmail.com 465
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			message.setText(msg);

			Transport.send(message);
			System.out.println("message sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
