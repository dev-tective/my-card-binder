package org.gatodev.my_card_binder.tcg_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TcgDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcgDataApplication.class, args);
    }

}
