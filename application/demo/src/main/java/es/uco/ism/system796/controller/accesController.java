package es.uco.ism.system796.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uco.ism.system796.Seguridad.Cipher;
import es.uco.ism.system796.business.dto.Usuario;
import es.uco.ism.system796.data.dao.PersonaDataMgr;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

@RestController
@CrossOrigin(origins="*", methods={RequestMethod.POST, RequestMethod.GET})
public class accesController {

    @PostMapping("ExitsUser")
    public ResponseEntity<String> existeUser(String mail) {
        PersonaDataMgr personaGest = null;
        try {
            personaGest = new PersonaDataMgr();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String idUsuario="";
        try {
            idUsuario = personaGest.getIdUser(mail);
            return ResponseEntity.ok( "{\"idUsuario\": \""+idUsuario+"\"}");

        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return ResponseEntity.ok( "{\"idUsuario\": \""+idUsuario+"\"}");

    }

    @PostMapping("user")
    public ResponseEntity<Usuario> login(@RequestParam("user") String mail, @RequestParam("password") String pwd) {

        PersonaDataMgr personaGest = null;
        String idUsuario, unformatedPassword, userSalt, userSaltedPasswd;
        String[] passwdParts;
        Usuario user = new Usuario();

        boolean existeUsuario = false;
        try {
            personaGest = new PersonaDataMgr();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            existeUsuario = personaGest.existsUser(mail);
        } catch (SQLException e) {
            // eturn ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
            // Usuario());
            e.printStackTrace();
        }

        if (!existeUsuario) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        try {
            idUsuario = personaGest.getIdUser(mail);
            user.setId(idUsuario);
            unformatedPassword = personaGest.getPasswdByUserId(idUsuario);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // passwdParts = unformatedPassword.split("$");
        // userSalt = passwdParts[0];
        // userSaltedPasswd = passwdParts[1];

        if (unformatedPassword.intern() == pwd.intern()) {
            String token = "";
            try {
                token = makeToken(mail);
            } catch (UnsupportedEncodingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            user.setMail(mail);
            user.setToken(token);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.ok(null);
    }

    private static String makeToken(String username) throws UnsupportedEncodingException {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes())
                .compact();

        return "Bearer " + token;
    }
}
