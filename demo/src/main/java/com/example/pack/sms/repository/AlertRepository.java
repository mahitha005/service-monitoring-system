package com.example.pack.sms.repository;

import com.example.pack.sms.entity.Alert;
import com.example.pack.sms.entity.MonitoredService;
import com.example.pack.sms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<Alert> findByServiceAndTypeAndStatus(
            MonitoredService service,
            String type,
            String status
    );

    long countByStatus(String status);

    long countByServiceUserAndStatus(User user, String status);

    List<Alert> findByServiceUser(User user);

}