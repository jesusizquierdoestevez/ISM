package es.uco.ism.system796.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uco.ism.system796.data.dao.NFCTagDataMgr;
import es.uco.ism.system796.data.dao.PersonaDataMgr;
import es.uco.ism.system796.data.dao.PreferenciaDataMgr;
import es.uco.ism.system796.data.dao.ServicioDataMgr;
import es.uco.ism.system796.Seguridad.Cipher;
import es.uco.ism.system796.business.dto.NFC;
import es.uco.ism.system796.business.dto.Preferencia;
import es.uco.ism.system796.business.dto.Servicio;
import es.uco.ism.system796.business.dto.Usuario;

@RestController
@RequestMapping("/")
@CrossOrigin(origins="*")
public class System796 {

    @GetMapping("registrarUsuario")
    public ResponseEntity<Boolean> registrarUsuario(@RequestParam String mail,
                            @RequestParam String nombre,
                            @RequestParam String direccion,
                            @RequestParam String telefono,
                            @RequestParam String password) 
    {

        Usuario newUsuario;
        PersonaDataMgr personaGest = null;
        String id;

        try {
            personaGest = new PersonaDataMgr();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        id = mail + System.currentTimeMillis();

        id = new Cipher().sha1(id);
        newUsuario = new Usuario(id, password, nombre, direccion, mail, Integer.valueOf(telefono), password);

        try {
            personaGest.insertUser(newUsuario);
        } catch (SQLException e) {
            return ResponseEntity.badRequest().body(false);
        }

        return ResponseEntity.ok().body(true);
    }

    @GetMapping("changeRol")
    public ResponseEntity<Boolean> changeRol(@RequestParam String id, @RequestParam String newRol) {

        if (newRol.intern() != "Cliente".intern() && newRol.intern() != "Administrador".intern()) {
            ResponseEntity.badRequest().body(Boolean.FALSE);
        }
        PersonaDataMgr personaGest = null;

        try {
            personaGest = new PersonaDataMgr();
        } catch (IOException e) {
            ResponseEntity.badRequest().body(Boolean.FALSE);
        }
        try {
            personaGest.changeRol(newRol, id);
        } catch (SQLException e) {
            ResponseEntity.badRequest().body(Boolean.FALSE);
        }

       return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("obtenerInfoUsuario")
    public ResponseEntity<Usuario> obtenerInfoUsuario(@RequestParam String id) {
        PersonaDataMgr personaGest = null;
        try {
            personaGest = new PersonaDataMgr();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return ResponseEntity.ok(personaGest.getUserDataById(id));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("setPreferencia")
    public ResponseEntity<Boolean> setPreferencia(@RequestParam String preferencia,
            @RequestParam String servicioId,
            @RequestParam String idUser,
            @RequestParam String idTag) {

        PreferenciaDataMgr preferenciaGest = null;
        NFCTagDataMgr nfcGest = null;
        ServicioDataMgr serviceGest = null;

        Preferencia infoAInsertar;
        String idPreferencia = "";
        try {
            preferenciaGest = new PreferenciaDataMgr();
            nfcGest = new NFCTagDataMgr();
            serviceGest = new ServicioDataMgr();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (!nfcGest.existsTag(idTag) || !serviceGest.existsServicio(servicioId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }

            idPreferencia = servicioId + idUser + idTag;
            idPreferencia = new Cipher().sha1(idPreferencia);

            infoAInsertar = new Preferencia(idPreferencia, preferencia, servicioId, idUser, idTag);
            if (preferenciaGest.insertPreferencia(infoAInsertar)) {
                return ResponseEntity.ok(true);
            }
        } catch (SQLException e) {
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @GetMapping("obtenerPreferencia")
    public ResponseEntity<HashMap<String, String>> obtenerPreferencia(@RequestParam String idServicio,
            @RequestParam String idUser,
            @RequestParam String idTag) {

        PreferenciaDataMgr preferenciaGest = null;

        try {
            preferenciaGest = new PreferenciaDataMgr();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return ResponseEntity.ok(preferenciaGest.getPreferenciasParaUserServicioYtag(idServicio, idUser, idTag));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("eliminarPreferencia")
    public ResponseEntity<Boolean> eliminarPreferencia(@RequestParam String idPreferencia) {
        PreferenciaDataMgr preferenciaGest = null;
        try {
            preferenciaGest = new PreferenciaDataMgr();
        } catch (IOException e) {
            return ResponseEntity.ok(false);
        }

        try {
            return ResponseEntity.ok(preferenciaGest.borrarPreferencia(idPreferencia));
        } catch (SQLException e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("actualizarPreferencia")
    public ResponseEntity<Boolean> actualizarPreferencia(@RequestParam String preferencia,
            @RequestParam String servicioId,
            @RequestParam String idUser,
            @RequestParam String idTag) {

        PreferenciaDataMgr preferenciaGest = null;
        Preferencia infoAactualizar;
        try {
            preferenciaGest = new PreferenciaDataMgr();
        } catch (IOException e) {
            return ResponseEntity.ok(false);
        }

        infoAactualizar = new Preferencia(null, preferencia, servicioId, idUser, idTag);

        try {
            return ResponseEntity.ok(preferenciaGest.updatePreferencia(infoAactualizar));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("obtenerTotalServicios")
    public ResponseEntity<ArrayList<String>> obtenerTotalServicios() {
        ServicioDataMgr serviciosGest = null;
        try {
            serviciosGest = new ServicioDataMgr();
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            return ResponseEntity.ok(serviciosGest.getTiposServicios());
        } catch (SQLException e) {
            return null;
        }
    }

    @GetMapping("obtenerTipoServicio")
    public ResponseEntity<String> obtenerTipoServicio(@RequestParam("idServicio") String id) {
        ServicioDataMgr serviciosGest = null;
        try {
            serviciosGest = new ServicioDataMgr();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }

        try {
            if (serviciosGest.existsServicio(id)) {
                return ResponseEntity.ok(serviciosGest.getTipoServicio(id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

            }
        } catch (SQLException e) {
            return null;
        }
    }

    @GetMapping("addServicio")
    public ResponseEntity<String> addServicio(@RequestParam("tipoServicio") String tipoServicio,
            @RequestParam("idTag") String idTag) {

        ServicioDataMgr serviciosGest = null;
        NFCTagDataMgr nfcGest = null;
        try {
            serviciosGest = new ServicioDataMgr();
            nfcGest = new NFCTagDataMgr();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
        boolean success = false;
        String idServicio;
        idServicio = idTag + System.currentTimeMillis();
        idServicio = new Cipher().sha1(idServicio);

        try {
            if (nfcGest.existsTag(idTag)) {
                System.out.println("entra1");
                if (serviciosGest.existsServicio(idServicio)) {
                    return ResponseEntity.accepted().body("alreadyExists");
                } else {
                    success = (serviciosGest.insertServicio(new Servicio(idServicio, tipoServicio)));
                    if (success) {
                        return ResponseEntity.ok().body("{\"idServicio\": \""+idServicio+"\"}");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error");
                    }

                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error");
            }
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("error");
        }
    }

    @GetMapping("addTag")
    public ResponseEntity<String> addTag(@RequestParam String idTag) {
        NFCTagDataMgr nfcGest = null;
        try {
            nfcGest = new NFCTagDataMgr();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }


        try {
            if (nfcGest.existsTag(idTag)) {
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

            } else {
                if (nfcGest.insertTag(new NFC(idTag))) {
                    return ResponseEntity.ok(idTag);

                } else {
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("error1");
                }
            }
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("error");
        }
    }

    @GetMapping("getTagsDeUsuario")
    public ResponseEntity<ArrayList<String>> getTagsDeUsuario(@RequestParam("idUser") String idUser) {
        NFCTagDataMgr nfcGest = null;
        try {
            nfcGest = new NFCTagDataMgr();
        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
        try {
            return ResponseEntity.ok(nfcGest.getTagsDeUsuario(idUser));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }


    @GetMapping("getServiciosUsuarioTag")
    public ResponseEntity<ArrayList<String>> getServiciosUsuarioTag(@RequestParam("idUser") String idUser,
                                                                    @RequestParam("idTag") String idTag) {
        PreferenciaDataMgr preferMGR = null;
        try {
            preferMGR = new PreferenciaDataMgr();
        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
        try {
            return ResponseEntity.ok(preferMGR.getIDServiciosParaTagYuser(idUser, idTag));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }
}
