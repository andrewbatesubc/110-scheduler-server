package com.dao;

import com.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component("sqlScheduleDao")
public class HerokuPGScheduleDao implements ScheduleDao {

    private final DataSourceCredentialsDao credentialsDao;
    private final PostgresSQLStatements sqlStatements;

    @Autowired
    public HerokuPGScheduleDao(@Qualifier("herokuCredentialsDao") final DataSourceCredentialsDao credentialsDao,
                               PostgresSQLStatements storedProcedures){
        this.credentialsDao = credentialsDao;
        this.sqlStatements = storedProcedures;
    }

    @Override
    public void setScheduleInDataSource(final ScheduleDto newSchedule) throws URISyntaxException, SQLException {
        upsertSchedule(newSchedule.getTaName(), newSchedule.getSchedulesByDay());
    }

    @Override
    public ScheduleDto getScheduleFromDataSource(String taName) throws URISyntaxException, SQLException {
        return new ScheduleDto(taName, selectSchedule(taName));
    }

    private Connection getDBConnection() throws URISyntaxException, SQLException {
        return DriverManager.getConnection(credentialsDao.getDBConnectionURL());
    }

    private void createTable() throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sqlStatements.createTableSQL());
            pstmt.execute();
            connection.commit();
            pstmt.close();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void dropTable() throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sqlStatements.dropTableSQL());
            pstmt.execute();
            connection.commit();
            pstmt.close();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void upsertSchedule(final String taName, final String[] schedule) throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sqlStatements.createUpsertSQL(taName, schedule));
            pstmt.execute();
            connection.commit();
            pstmt.close();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private String[] selectSchedule(final String taName) throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        Statement statement = connection.createStatement();
        String[] results = null;
        try {
            ResultSet rs = statement.executeQuery(sqlStatements.createSelectSQL(taName));
            while(rs.next()){
                results = new String[7];
                results[0] = rs.getString("Monday");
                results[1] = rs.getString("Tuesday");
                results[2] = rs.getString("Wednesday");
                results[3] = rs.getString("Thursday");
                results[4] = rs.getString("Friday");
                results[5] = rs.getString("Saturday");
                results[6] = rs.getString("Sunday");
            }
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
        return results;
    }
}
