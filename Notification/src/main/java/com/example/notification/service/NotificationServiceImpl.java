package com.example.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.notification.config.RabbitMQConfig;
import com.example.notification.controller.NotificationController;
import com.example.notification.dto.NotificationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private JavaMailSender mailSender;
	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@RabbitListener(queues = RabbitMQConfig.NOTIFICATION_SERVICE_QUEUE)
    public void handlePaymentSuccess(NotificationDTO notificationDto) {
		try {
        log.info("Received Payment Success event for email: " + notificationDto.getRecipient());
        if (notificationDto.getRecipient() == null || notificationDto.getMessage() == null) {
            log.warn("Invalid NotificationDTO received: {}", notificationDto);
            return;
        }
        // Send payment success notification email
        sendEmail(notificationDto);
	} catch (Exception e) {
        log.error("Failed to process NotificationDTO: {}", notificationDto, e);
    }
	}

	@Override
	public void sendEmail(NotificationDTO notificationDto) {
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setFrom("emailtesting732@gmail.com");
		mailMessage.setTo(notificationDto.getRecipient());
		mailMessage.setSubject("Pizza Order Update");
		mailMessage.setText(notificationDto.getMessage());
		mailSender.send(mailMessage);
		log.info("Mail Sent Successfully!!");
	}
}
