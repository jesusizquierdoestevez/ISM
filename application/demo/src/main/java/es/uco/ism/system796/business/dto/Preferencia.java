package es.uco.ism.system796.business.dto;

import java.io.Serializable;

public class Preferencia implements Serializable {

    private String id;
    private String preferencia;
    private String servicioId;
    private String idUser;
    private String idTag;

    public Preferencia(String id, String preferencia, String servicioId, String idUser, String idTag) {
        setId(id);
        setIdTag(idTag);
        setIdUser(idUser);
        setPreferencia(preferencia);
        setServicioId(servicioId);
    }

    /**
     * @return int return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the preferencia
     */
    public String getPreferencia() {
        return preferencia;
    }

    /**
     * @param preferencia the preferencia to set
     */
    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    /**
     * @return int return the servicioId
     */
    public String getServicioId() {
        return servicioId;
    }

    /**
     * @param servicioId the servicioId to set
     */
    public void setServicioId(String servicioId) {
        this.servicioId = servicioId;
    }

    /**
     * @return int return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @return int return the idTag
     */
    public String getIdTag() {
        return idTag;
    }

    /**
     * @param idTag the idTag to set
     */
    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

}
