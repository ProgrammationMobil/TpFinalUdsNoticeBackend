package com.UDSNotice.BackendUdsNotice.Repository;

import com.UDSNotice.BackendUdsNotice.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
