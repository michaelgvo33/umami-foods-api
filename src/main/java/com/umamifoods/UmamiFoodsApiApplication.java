/* Classe principal: inicia a aplicação Spring Boot (rode esta classe para subir a API na porta 8080). */

package com.umamifoods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.umamifoods.api")
public class UmamiFoodsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmamiFoodsApiApplication.class, args);
    }
}
