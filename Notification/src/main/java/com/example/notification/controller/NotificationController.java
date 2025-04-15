package com.example.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification.dto.NotificationDTO;
import com.example.notification.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/sendNotification")
	public ResponseEntity<?> sendNotificationEmail(@RequestBody NotificationDTO notificationDto) {
		notificationService.sendEmail(notificationDto);
		return new ResponseEntity<>("Mail Sent Successfully!!",HttpStatus.OK);
	}
}
