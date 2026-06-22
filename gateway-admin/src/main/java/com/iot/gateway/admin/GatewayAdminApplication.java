package com.iot.gateway.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.iot.gateway.admin", "com.iot.gateway.core"})
@EntityScan(basePackages = "com.iot.gateway.admin.entity")
@EnableJpaAuditing
@EnableScheduling
public class GatewayAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAdminApplication.class, args);
    }
}
