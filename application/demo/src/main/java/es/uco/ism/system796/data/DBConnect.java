package es.uco.ism.system796.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
    private Connection con;
    private static DBConnect instance;

    private DBConnect() {
        setConnection();
    }

    public void setConnection() {
        Properties connectionParameters = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream applicationPropertiesFile = classLoader.getResourceAsStream("connection.properties");
        try {
            connectionParameters.load(applicationPropertiesFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String url, username, passwd;

        url = connectionParameters.getProperty("url");
        username = connectionParameters.getProperty("username");
        passwd = connectionParameters.getProperty("password");

        con = null;
        try {

            con = DriverManager.getConnection(url, username, passwd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {

        if (con == null) {
            setConnection();
        }

        return con;
    }

    public static DBConnect getInstance() {
        if (instance == null) {
            instance = new DBConnect();
        }
        return instance;
    }

    public void closeConnection() throws SQLException {
        if (con != null)
            con.close();
    }

}
