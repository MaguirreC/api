package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;
import med.voll.api.usuarios.DatosAutenticacionUsuario;
import med.voll.api.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {


     @Autowired
    private AuthenticationManager authenticationManager;

     @Autowired
     private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){

        Authentication authenticationToken= new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),datosAutenticacionUsuario.clave());
        var usarioAutenticado = authenticationManager.authenticate(authenticationToken);
        var JWTtoken = tokenService.generarToken((Usuario) usarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
