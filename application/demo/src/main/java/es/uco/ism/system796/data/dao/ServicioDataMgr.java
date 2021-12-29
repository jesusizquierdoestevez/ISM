package es.uco.ism.system796.data.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.system796.business.dto.Servicio;
import es.uco.ism.system796.data.DBConnect;

public class ServicioDataMgr {
    private static DBConnect dbConnector;
    private Properties bbddProperties;

    public ServicioDataMgr() throws IOException {
        dbConnector = DBConnect.getInstance();
        bbddProperties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream databasePropertiesFile = classLoader.getResourceAsStream("bbdd.properties");
        bbddProperties.load(databasePropertiesFile);
    }

    public boolean existsServicio(String id) throws SQLException {
        String statement = bbddProperties.get("getIdServicio").toString();
        PreparedStatement ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public String getTipoServicio(String id) throws SQLException {
        String tipoServicio, statement;
        PreparedStatement ps;

        ResultSet rs;

        tipoServicio = "";
        statement = bbddProperties.get("getTipoServicio").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            tipoServicio = rs.getString(1);
        }
        return  "{\"tipoServicio\": \""+tipoServicio+"\"}";
    }

    public ArrayList<String> getTiposServicios() throws SQLException {
        String statement = bbddProperties.get("getTiposServicios").toString();
        PreparedStatement ps = dbConnector.getConnection().prepareStatement(statement);
        ArrayList<String> tiposDeServicios = new ArrayList<String>();

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            tiposDeServicios.add(rs.getString(1));
        }

        return tiposDeServicios;
    }

    public boolean insertServicio(Servicio newServicio) throws SQLException {
        PreparedStatement ps;
        String statement;
        String id;
        String tipoServicio;

        id = newServicio.getId();
        tipoServicio = newServicio.getTipoServicio();

        statement = bbddProperties.get("insertarServicio").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);

        ps.setString(1, id);
        ps.setString(2, tipoServicio);
        ps.execute();

        return existsServicio(id);
    }

    public boolean borrarServicio(String id) throws SQLException {
        PreparedStatement ps;
        String statement;
        boolean borradoCorrecto;

        statement = bbddProperties.get("borrarServicio").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);

        ps.setString(1, id);
        borradoCorrecto = ps.execute();

        return borradoCorrecto;
    }
}
