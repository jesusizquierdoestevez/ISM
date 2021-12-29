package es.uco.ism.system796.business.dto;

import java.io.Serializable;

public class Servicio implements Serializable {

    private String id;
    private String tipoServicio;

    public Servicio(String id, String tipoServicio) {
        this.id = id;
        this.tipoServicio = tipoServicio;
    }

    /**
     * @return String return the tipoServicio
     */
    public String getTipoServicio() {
        return tipoServicio;
    }

    /**
     * @param tipoServicio the tipoServicio to set
     */
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
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

}
