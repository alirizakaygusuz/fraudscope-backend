package com.finscope.fraudscope.common.bootstrap;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("MAIL_FROM", dotenv.get("MAIL_FROM"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        System.setProperty("SYSTEM_AUTH_USER_NAME", dotenv.get("SYSTEM_AUTH_USER_NAME"));
        System.setProperty("SYSTEM_AUTH_USER_EMAIL", dotenv.get("SYSTEM_AUTH_USER_EMAIL"));
        System.setProperty("SYSTEM_AUTH_USER_PASSWORD", dotenv.get("SYSTEM_AUTH_USER_PASSWORD"));
        System.out.println(" Dotenv environment variables loaded before Spring starts.");
        
    }
}
