package org.example.mqtt_spring_dht11.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "dhtLog")
public class dhtLog {
    @Id @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    public Double humidity;

    @Column(nullable = false)
    public Double temperature;
}
