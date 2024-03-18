package org.example.mqtt_spring_dht11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public class dhtLogRepository extends JpaRepository<LocalDateTime, dhtLog> {
    //페이징 구현도 필요하다
    @Override
    List<dhtLog> findAll(Pageable pageable);
}
