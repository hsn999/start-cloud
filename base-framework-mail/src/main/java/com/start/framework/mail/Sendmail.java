package com.start.framework.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

public class Sendmail {

	@Autowired
	private JavaMailSenderImpl mailSender;

	public void sendTxtMail() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人
		simpleMailMessage.setTo(new String[] { "xxx@126.com" });
		simpleMailMessage.setFrom("fhhe@126.com");
		simpleMailMessage.setSubject("Spring Boot Mail 邮件测试【文本】");
		simpleMailMessage.setText("这里是一段简单文本。");
		// 发送邮件
		mailSender.send(simpleMailMessage);

		System.out.println("邮件已发送");
	}

	/**
	 * 发送包含HTML文本的邮件
	 * 
	 * @throws Exception
	 */
	public void sendHtmlMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setTo("xxx@126.com");
		mimeMessageHelper.setFrom("xx1x@126.com");
		mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【HTML】");

		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head>");
		sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
		sb.append("</html>");

		// 启用html
		mimeMessageHelper.setText(sb.toString(), true);
		// 发送邮件
		mailSender.send(mimeMessage);

		System.out.println("邮件已发送");

	}

	/**
	 * 发送包含内嵌图片的邮件
	 * 
	 * @throws Exception
	 */

	public void sendAttachedImageMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		// multipart模式
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo("xxx@126.com");
		mimeMessageHelper.setFrom("xxx1@126.com");
		mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【图片】");

		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head>");
		sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p>");
		// cid为固定写法，imageId指定一个标识
		sb.append("<img src=\"cid:imageId\"/></body>");
		sb.append("</html>");

		// 启用html
		mimeMessageHelper.setText(sb.toString(), true);

		// 设置imageId
		FileSystemResource img = new FileSystemResource(new File("E:/new.jpg"));
		mimeMessageHelper.addInline("imageId", img);

		// 发送邮件
		mailSender.send(mimeMessage);

		System.out.println("邮件已发送");
	}

	/**
	 * 发送包含附件的邮件
	 * 
	 * @throws Exception
	 */
	public void sendAttendedFileMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		// multipart模式
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		mimeMessageHelper.setTo("xxx@126.com");
		mimeMessageHelper.setFrom("xxx1@126.com");
		mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【附件】");

		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head>");
		sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
		sb.append("</html>");

		// 启用html
		mimeMessageHelper.setText(sb.toString(), true);
		// 设置附件
		FileSystemResource img = new FileSystemResource(new File("E:/new.jpg"));
		mimeMessageHelper.addAttachment("image.jpg", img);

		// 发送邮件
		mailSender.send(mimeMessage);

		System.out.println("邮件已发送");
	}

}
