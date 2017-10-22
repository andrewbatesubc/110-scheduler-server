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
                               PostgresSQLStatements storedProcedures) throws URISyntaxException, SQLException {
        this.credentialsDao = credentialsDao;
        this.sqlStatements = storedProcedures;
        createTableIfDoesntExist();
    }

    @Override
    public void setScheduleInDataSource(final ScheduleDto newSchedule) throws URISyntaxException, SQLException {
        upsertSchedule(newSchedule.getTaName(), newSchedule.getSchedulesByDay());
    }

    @Override
    public ScheduleDto getScheduleFromDataSource(String taName) throws URISyntaxException, SQLException {
        return selectSchedule(taName);
    }

    @Override
    public ScheduleDto[] getAllSchedulesFromDataSource() throws URISyntaxException, SQLException {
        return selectAllSchedules();
    }

    private Connection getDBConnection() throws URISyntaxException, SQLException {
        return DriverManager.getConnection(credentialsDao.getDBConnectionURL());
    }

    private void updateTable(final String sqlString) throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sqlString);
            pstmt.execute();
            connection.commit();
            pstmt.close();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void createTableIfDoesntExist() throws URISyntaxException, SQLException {
        updateTable(sqlStatements.createTableSQL());
    }

    public void dropTable() throws URISyntaxException, SQLException {
        updateTable(sqlStatements.dropTableSQL());
    }

    private void upsertSchedule(final String taName, final String[] schedule) throws URISyntaxException, SQLException {
        updateTable(sqlStatements.createUpsertSQL(taName, schedule));
    }

    @Override
    public void deleteTASchedule(String taName) throws URISyntaxException, SQLException {
        updateTable(sqlStatements.createDeleteTASQL(taName.trim()));
    }

    @Override
    public void deleteAllSchedules() throws URISyntaxException, SQLException {
        updateTable(sqlStatements.deleteAllSQL());
    }

    private ScheduleDto selectSchedule(final String taName) throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        Statement statement = connection.createStatement();
        ScheduleDto schedule = new ScheduleDto(taName, null);
        try {
            ResultSet rs = statement.executeQuery(sqlStatements.createSelectSQL(taName));
            while(rs.next()){
                String[] results = new String[7];
                results[0] = rs.getString("Monday");
                results[1] = rs.getString("Tuesday");
                results[2] = rs.getString("Wednesday");
                results[3] = rs.getString("Thursday");
                results[4] = rs.getString("Friday");
                results[5] = rs.getString("Saturday");
                results[6] = rs.getString("Sunday");
                schedule.setSchedulesByDay(results);
            }
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
        return schedule;
    }

    private ScheduleDto[] selectAllSchedules() throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        Statement statement = connection.createStatement();
        List<ScheduleDto> schedules = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(sqlStatements.selectAllSQL());
            while(rs.next()){
                String[] results = new String[7];
                results[0] = rs.getString("Monday");
                results[1] = rs.getString("Tuesday");
                results[2] = rs.getString("Wednesday");
                results[3] = rs.getString("Thursday");
                results[4] = rs.getString("Friday");
                results[5] = rs.getString("Saturday");
                results[6] = rs.getString("Sunday");
                schedules.add(new ScheduleDto(rs.getString("TAName"), results));
            }
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
        return schedules.toArray(new ScheduleDto[schedules.size()]);
    }
}
