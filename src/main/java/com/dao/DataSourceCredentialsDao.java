package com.dao;

/**
 * Responsible for surfacing data source credentials to DAO objects
 */
public interface DataSourceCredentialsDao {
    String getDBConnectionURL();
}
