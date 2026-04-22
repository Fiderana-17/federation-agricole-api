package com.hei.federation_api.Config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DataSource {

    public Connection getConnection() {
        try {
            Dotenv dotenv = Dotenv.load();
            return DriverManager.getConnection(
                    dotenv.get("DB_URL"),
                    dotenv.get("DB_USER"),
                    dotenv.get("DB_PASSWORD")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
