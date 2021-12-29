package es.uco.ism.system796.data.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import es.uco.ism.system796.business.dto.Preferencia;
import es.uco.ism.system796.data.DBConnect;

public class PreferenciaDataMgr {
    private static DBConnect dbConnector;
    private Properties bbddProperties;

    public PreferenciaDataMgr() throws IOException {
        dbConnector = DBConnect.getInstance();
        bbddProperties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream databasePropertiesFile = classLoader.getResourceAsStream("bbdd.properties");
        bbddProperties.load(databasePropertiesFile);
    }

    public ArrayList<String> getIDServiciosParaTagYuser(String idUser, String idTag) throws SQLException {
        String statement;
        PreparedStatement ps;
        ArrayList<String> setDeIdsDeServicios;
        ResultSet rs;

        statement = bbddProperties.get("getIDServiciosParaTagYuser").toString();
        setDeIdsDeServicios = new ArrayList<String>();
        ps = dbConnector.getConnection().prepareStatement(statement);

        ps.setString(1, idUser);
        ps.setString(2, idTag);
        rs = ps.executeQuery();
        while (rs.next()) {
            setDeIdsDeServicios.add(rs.getString(1));
        }
        return setDeIdsDeServicios;
    }

    public HashMap<String, String> getPreferenciasParaUserServicioYtag(String isServicio, String idUser,
            String idTag)
            throws SQLException {
        String statement = bbddProperties.get("getPreferenciasParaUserServicioYtag").toString();
        PreparedStatement ps = dbConnector.getConnection().prepareStatement(statement);
        HashMap<String, String> setServicioPreferencia = new HashMap<String, String>();

        ps.setString(1, isServicio);
        ps.setString(2, idUser);
        ps.setString(3, idTag);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            setServicioPreferencia.put("preferencia", rs.getString(1));
            setServicioPreferencia.put("idPreferencia", rs.getString(2));
        }

        return setServicioPreferencia;
    }

    public boolean insertPreferencia(Preferencia newPreferencia) throws SQLException {
        PreparedStatement ps;
        String statement;
        boolean correctInsertion;
        String preferencia, id, servicioId, idUser, idTag;

        id = newPreferencia.getId();
        servicioId = newPreferencia.getServicioId();
        idUser = newPreferencia.getIdUser();
        idTag = newPreferencia.getIdTag();
        preferencia = newPreferencia.getPreferencia();

        statement = bbddProperties.get("addPreferencia").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, id);
        ps.setString(2, preferencia);
        ps.setString(3, servicioId);
        ps.setString(4, idUser);
        ps.setString(5, idTag);

        correctInsertion = ps.executeUpdate() > 0;

        return correctInsertion;
    }

    public boolean borrarPreferencia(String id) throws SQLException {
        PreparedStatement ps;
        String statement;
        boolean borradoCorrecto;

        statement = bbddProperties.get("borrarPreferencia").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);

        ps.setString(1, id);
        borradoCorrecto = ps.executeUpdate() > 0;

        return borradoCorrecto;
    }

    public boolean updatePreferencia(Preferencia newPreferencia) throws SQLException {
        PreparedStatement ps;
        String statement;
        String preferencia, servicioId, idUser, idTag;
        Integer numberOfAffectedRows;

        servicioId = newPreferencia.getServicioId();
        idUser = newPreferencia.getIdUser();
        idTag = newPreferencia.getIdTag();
        preferencia = newPreferencia.getPreferencia();

        statement = bbddProperties.get("updatePreferencia").toString();
        ps = dbConnector.getConnection().prepareStatement(statement);
        ps.setString(1, preferencia);
        ps.setString(2, servicioId);
        ps.setString(3, idUser);
        ps.setString(4, idTag);

        numberOfAffectedRows = ps.executeUpdate();

        return numberOfAffectedRows > 0;
    }

}
