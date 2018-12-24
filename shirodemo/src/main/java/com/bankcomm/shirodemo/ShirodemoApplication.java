package com.bankcomm.shirodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author auto
 */
@SpringBootApplication(scanBasePackages = "com.bankcomm.shirodemo")
@EnableAutoConfiguration

//        (exclude = {DataSourceAutoConfiguration.class})
public class ShirodemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShirodemoApplication.class, args);
    }
}
