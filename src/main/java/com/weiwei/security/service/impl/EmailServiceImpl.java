package com.weiwei.security.service.impl;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.weiwei.security.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender mailSender;

	@Value(value = "email.comfirm.address")
	private String emailConfirmAddress;

	@Override
	public void sendConfirmEmail(String emailAddress, String confirmToken, String username) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
				mimeMessage.setFrom(new InternetAddress("tyingkai@gmail.com"));
				mimeMessage.setText("尊敬的" + username + ", 多谢注册账户，请点击链接激活邮箱：" + emailConfirmAddress + confirmToken);
			}
		};
		try {
			this.mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Send email exception: ", e);
		}
	}

}
