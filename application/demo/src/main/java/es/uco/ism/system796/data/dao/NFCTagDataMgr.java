package es.uco.ism.system796.data.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.system796.business.dto.NFC;
import es.uco.ism.system796.data.DBConnect;

public class NFCTagDataMgr {
    private static DBConnect dbConnector;
    private Properties bbddProperties;

    public NFCTagDataMgr() throws IOException {
        dbConnector = DBConnect.getInstance();
        bbddProperties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream databasePropertiesFile = classLoader.getResourceAsStream("bbdd.properties");
        bbddProperties.load(databasePropertiesFile);
    }

    public boolean existsTag(String id) throws SQLException {
        String statement = bbddProperties.get("existsNfcTag").toString();
        PreparedStatement ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public boolean insertTag(NFC newTag) throws SQLException {
        PreparedStatement ps;
        String statement;
        String newId;

        newId = newTag.getId();
        statement = bbddProperties.get("insertTag").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, newId);

        ps.execute();

        return existsTag(newId);
    }

    public boolean borrarTag(String id) throws SQLException {
        PreparedStatement ps;
        String statement;
        boolean borradoCorrecto;

        statement = bbddProperties.get("borrarTag").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);

        ps.setString(1, id);
        borradoCorrecto = ps.execute();

        return borradoCorrecto;
    }

    public ArrayList<String> getTagsDeUsuario(String idUsuario) throws SQLException {
        ArrayList<String> tagsDeUsuario = new ArrayList<String>();
        String statement = bbddProperties.get("getTagsDeUsuario").toString();
        PreparedStatement ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, idUsuario);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            tagsDeUsuario.add(rs.getString(1));
        }

        return tagsDeUsuario;
    }

}
