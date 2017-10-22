package com.dao;

import com.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.sql.*;

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
    public void setScheduleInDataSource(ScheduleDto newSchedule) {
    }

    @Override
    public ScheduleDto getScheduleFromDataSource(String taName) {
        try {
            //dropTable();
            createTable();
            String[] testArray = new String[]{
                    "napnapnapnapnapnapnapnappp",
                    "napnapnapnapnapnapnapnappp",
                    "napnapnapnapnapnapnapnappp",
                    "napnapnapnapnapnapnapnappp",
                    "napnapnapnapnapnapnapnappp",
                    "napnapnapnapnapnapnapnappp",
                    "napnapnapnapnapnapnapnappp"};
            upsertSchedule("andrew_bates", testArray);
            upsertSchedule("andrew_bates", testArray);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] schedule = new String[]{"44", "33", "22"};
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setSchedulesByDay(schedule);
        return scheduleDto;
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

    private void selectSchedule(final String taName) throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sqlStatements.createSelectSQL(taName));
            pstmt.execute();
            connection.commit();
            pstmt.close();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
