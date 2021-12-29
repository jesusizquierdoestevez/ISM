package es.uco.ism.system796.business.dto;

import java.io.Serializable;

public class Usuario implements Serializable {

    // atributos
    private String id;
    private String password;
    private String nombre;
    private String direccion;
    private String mail;
    private int telefono;
    private String rol;
    private String token;

    public Usuario(String id, String password, String nombre, String direccion, String mail, int telefono,
            String rol) {
        setId(id);
        setPassword(password);
        setNombre(nombre);
        setDireccion(direccion);
        setMail(mail);
        setTelefono(telefono);
        setRol(rol);
    }

    public Usuario() {
    }

    // funciones
    // id
    public String getId() {
        return id;
    }

    public void setId(String newId) {
        id = newId;
    }

    // rol
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // password
    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    // nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String newNombre) {
        nombre = newNombre;
    }

    // direccion
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String newDireccion) {
        direccion = newDireccion;
    }

    // mail
    public String getMail() {
        return mail;
    }

    public void setMail(String newMail) {
        mail = newMail;
    }

    // telefono
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int newTelefono) {
        telefono = newTelefono;
    }

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

}
