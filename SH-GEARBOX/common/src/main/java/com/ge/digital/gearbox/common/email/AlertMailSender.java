package com.ge.digital.gearbox.common.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class AlertMailSender {
	@Autowired
	private JavaMailSender sender;

	public void sendMail(Mail mail) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(new InternetAddress("jesse_ji521@sina.com"));
			
			helper.setTo(mail.getToList());
			
			helper.setCc(mail.getCcList());
			helper.setSubject(mail.getSubject());

			helper.setText("<html>"+mail.getHeader() + "</br></br>" + mail.getBody() + "</br></br>" + mail.getFooter()+"</html>", true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sender.send(message);
	}
}
