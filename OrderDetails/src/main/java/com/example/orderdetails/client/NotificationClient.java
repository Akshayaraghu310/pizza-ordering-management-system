package com.example.orderdetails.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.orderdetails.dto.NotificationDTO;

@FeignClient(name="Notification-Service")
public interface NotificationClient {
	
	@PostMapping("/notification/sendNotification")
	public ResponseEntity<?> sendNotificationEmail(@RequestBody NotificationDTO notificationDto);

}
