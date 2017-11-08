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
                               PostgresSQLStatements storedProcedures) throws URISyntaxException, SQLException {
        this.credentialsDao = credentialsDao;
        this.sqlStatements = storedProcedures;
        createTablesIfDoesntExist();
    }

    @Override
    public void setScheduleInDataSource(final ScheduleDto newSchedule) throws URISyntaxException, SQLException {
        updateTable(sqlStatements.createUpsertScheduleSQL(newSchedule.getTaName().trim().toLowerCase(),
                newSchedule.getScheduleType().trim().toLowerCase(), newSchedule.getSchedulesByDay()));
    }

    @Override
    public void setScheduleTypeInDataSource(final String newScheduleType) throws URISyntaxException, SQLException {
        updateTable(sqlStatements.createInsertScheduleTypeSQL(newScheduleType.trim().toLowerCase()));
    }

    @Override
    public ResultSet getScheduleFromDataSource(final String taName, final String scheduleType) throws URISyntaxException, SQLException {
        return executeGetQuery(sqlStatements.createSelectSQL(taName, scheduleType));
    }

    @Override
    public ResultSet getScheduleTypesFromDataSource() throws URISyntaxException, SQLException {
        return executeGetQuery(sqlStatements.selectAllScheduleTypesSQL());
    }

    @Override
    public ResultSet getAllSchedulesFromDataSource() throws URISyntaxException, SQLException {
        return executeGetQuery(sqlStatements.selectAllSQL());
    }

    private ResultSet executeGetQuery(final String sql) throws URISyntaxException, SQLException{
        Connection connection = getDBConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
        return rs;
    }

    private Connection getDBConnection() throws URISyntaxException, SQLException {
        return DriverManager.getConnection(credentialsDao.getDBConnectionURL());
    }

    public void createTablesIfDoesntExist() throws URISyntaxException, SQLException {
        updateTable(sqlStatements.createScheduleTypesTableSQL());
        updateTable(sqlStatements.createScheduleTableSQL());
    }

    public void dropTables() throws URISyntaxException, SQLException {
        updateTable(sqlStatements.dropScheduleTableSQL());
        updateTable(sqlStatements.dropScheduleTypeTableSQL());
    }

    @Override
    public void deleteTAScheduleType(final String scheduleType) throws URISyntaxException, SQLException {
        //We need to delete all schedules that contain this type to avoid data consistency issues.
        updateTable(sqlStatements.createDeleteScheduleByTypeSQL(scheduleType.trim().toLowerCase()));
        updateTable(sqlStatements.createDeleteScheduleTypeSQL(scheduleType.trim().toLowerCase()));
    }

    @Override
    public void deleteTASchedule(final String taName, final String scheduleType) throws URISyntaxException, SQLException {
        updateTable(sqlStatements.createDeleteTASQL(taName.trim().toLowerCase(), scheduleType.trim().toLowerCase()));
    }

    @Override
    public void deleteAllSchedules() throws URISyntaxException, SQLException {
        updateTable(sqlStatements.deleteAllSQL());
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

}
