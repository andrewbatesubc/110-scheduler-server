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

    int count = 0;
    @Override
    public ScheduleDto getScheduleFromDataSource(String taName) {
        try {
            dropTable();
            createTable();
            String[] testArray = new String[]{
                    "mmmmmmmmmmmmmmmmmmmmmmmmmmmm",
                    "tttttttttttttttttttttttttttttttt",
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
                    "thththththththththththththththth",
                    "ffffffffffffffffffffffffffffffff",
                    "sasasasasasasasasasasasasasasasa",
                    "susususususususususususususususu"};

            if(count == 0){
                System.out.println("UPDATING ... ");
                upsertSchedule("andrew_bates", testArray);
                count++;
            }

            selectSchedule("andrew_bates");
           // String[] newTest = selectSchedule("andrew_bates");
           // System.out.println("Shedule returned: ");
            //for(int i = 0; i < newTest.length; i++){
                //System.out.println(newTest[i]);
           // }
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

    private String[] selectSchedule(final String taName) throws URISyntaxException, SQLException {
        Connection connection = getDBConnection();
        Statement statement = connection.createStatement();
        String[] results = null;
        try {
            ResultSet rs = statement.executeQuery(sqlStatements.createSelectSQL(taName));
            while(rs.next()){
               // results = new String[7];

                System.out.println(rs.getString("Monday"));

               // results[1] = rs.getString("Tuesday");
                System.out.println(rs.getString("Tuesday"));
                //results[2] = rs.getString("Wednesday");
                System.out.println(rs.getString("Wednesday"));
               // results[3] = rs.getString("Thursday");
                System.out.println(rs.getString("Thursday"));
               // results[4] = rs.getString("Friday");
                System.out.println(rs.getString("Friday"));
               // results[5] = rs.getString("Saturday");
                System.out.println(rs.getString("Saturday"));
                //results[6] = rs.getString("Sunday");
                System.out.println(rs.getString("Sunday"));
            }
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
        return results;
    }
}
