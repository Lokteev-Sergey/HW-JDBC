package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

public class H2DataBaseConnection {
    private static DbProperties getDbProperties() throws IOException {
        //Читаем из файла db-properties.json
        InputStream stream=ClassLoader.getSystemResourceAsStream("db-properties.json");
        //преобразователь типов
        ObjectMapper objectMapper= new ObjectMapper();
        //Преобразовуем поток байтов в объект dbProperties
        DbProperties dbProperties= objectMapper.readValue(stream, DbProperties.class);
        return dbProperties;
    }
    public static DataSource getH2DataSource() throws IOException {
        JdbcDataSource dataSource = new JdbcDataSource();
        DbProperties properties = getDbProperties();
        dataSource.setURL(properties.getUrl());
        dataSource.setUser(properties.getUser());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }


}
