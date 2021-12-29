package es.uco.ism.system796.data.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import es.uco.ism.system796.business.dto.Usuario;
import es.uco.ism.system796.data.DBConnect;

public class PersonaDataMgr {
    private static DBConnect dbConnector;
    private Properties bbddProperties;

    public PersonaDataMgr() throws IOException {

        dbConnector = DBConnect.getInstance();

        bbddProperties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream databasePropertiesFile = classLoader.getResourceAsStream("bbdd.properties");
        bbddProperties.load(databasePropertiesFile);
    }

    public boolean existsUser(String mailOrid) throws SQLException {
        String statement = bbddProperties.get("getUserId").toString();
        PreparedStatement ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, mailOrid);
        ps.setString(2, mailOrid);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public Usuario getUserDataById(String id) throws SQLException {
        String nombre, direccion, correoElectronico, rol, statement;
        Integer numeroTelefono;
        PreparedStatement ps;
        ResultSet rs;

        statement = bbddProperties.get("getDataById").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, id);
        rs = ps.executeQuery();
        nombre = "";
        direccion = "";
        correoElectronico = "";
        rol = "";
        numeroTelefono = null;

        if (rs.next()) {
            nombre = rs.getString(1);
            direccion = rs.getString(2);
            numeroTelefono = rs.getInt(3);
            correoElectronico = rs.getString(4);
            rol = rs.getString(5);
        }
        // dbConnector.closeConnection();
        return new Usuario(id, "", nombre, direccion, correoElectronico, numeroTelefono, rol);
    }

    public String getIdUser(String mailOrId) throws SQLException {
        String statement, idBuscado = "";
        PreparedStatement ps;
        ResultSet rs;

        statement = bbddProperties
                .get("getUserId")
                .toString();
        ps = dbConnector.getConnection()
                .prepareStatement(statement);
        ps.setString(1, mailOrId);
        ps.setString(2, mailOrId);

        rs = ps.executeQuery();

        if (rs.next()) {
            idBuscado = rs.getString(1);
        }

        return idBuscado;
    }

    public String getPasswdByUserId(String id) throws SQLException {
        String statement, unformatedPasswd;
        PreparedStatement ps;
        ResultSet rs;

        statement = bbddProperties
                .get("getPasswdByUserId")
                .toString();
        ps = dbConnector.getConnection()
                .prepareStatement(statement);
        ps.setString(1, id);
        rs = ps.executeQuery();
        unformatedPasswd = "";

        if (rs.next()) {
            unformatedPasswd = rs.getString(1);
        }
        // dbConnector.closeConnection();
        return unformatedPasswd;
    }

    public boolean insertUser(Usuario newUser) throws SQLException {
        PreparedStatement ps;
        String statement;
        boolean correctInsertion;
        Integer numeroTelefono;
        String id, nombre, direccion, correo, passwd;

        id = newUser.getId();
        numeroTelefono = newUser.getTelefono();
        nombre = newUser.getNombre();
        direccion = newUser.getDireccion();
        correo = newUser.getMail();
        passwd = newUser.getPassword();

        statement = bbddProperties.get("insertUser").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, id);
        ps.setString(2, nombre);
        ps.setString(3, direccion);
        ps.setInt(4, numeroTelefono);
        ps.setString(5, correo);
        ps.setString(6, passwd);
        correctInsertion = ps.execute();
        // dbConnector.closeConnection();

        return correctInsertion;
    }

    public boolean changeRol(String rol, String id) throws SQLException {
        PreparedStatement ps;
        String statement;
        Integer numberOfAffectedRows;

        statement = bbddProperties.get("changeRol").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, rol);
        ps.setString(2, id);
        numberOfAffectedRows = ps.executeUpdate();
        // dbConnector.closeConnection();

        return numberOfAffectedRows > 0;
    }
}