package com.example.notification.service;

import com.example.notification.dto.NotificationDTO;

public interface NotificationService {

	void sendEmail(NotificationDTO notificationDto);

}
