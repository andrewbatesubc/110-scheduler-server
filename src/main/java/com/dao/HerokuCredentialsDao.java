package com.dao;

import org.springframework.stereotype.Component;

@Component("herokuCredentialsDao")
public class HerokuCredentialsDao implements DataSourceCredentialsDao {

    @Override
    public String getDBConnectionURL() {
        return System.getenv("JDBC_DATABASE_URL");
    }
}
