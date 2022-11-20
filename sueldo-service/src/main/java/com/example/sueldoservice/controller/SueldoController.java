package com.example.sueldoservice.controller;

import java.util.List;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;*/
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.sueldoservice.entity.SueldoEntity;
import com.example.sueldoservice.model.*;
//import com.example.sueldoservice.service.JwtUtilService;
import com.example.sueldoservice.service.SueldoService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/sueldo")
public class SueldoController {
    @Autowired
    SueldoService sueldoService;

    /*@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;*/

    @GetMapping("/calcularPlanilla")
    public ResponseEntity calcularPlanilla(){
        EmpleadoModel[] empleados           = sueldoService.getEmpleados();
        JustificativoModel[] justificativos = sueldoService.getJustificativos();
        DatarelojModel[] marcasReloj        = sueldoService.getMarcasReloj();
        AutorizacionModel[] autorizaciones  = sueldoService.getAutorizaciones();

        sueldoService.calcularPlanilla(empleados,justificativos,marcasReloj,autorizaciones);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getEmpleados")
    public ResponseEntity<EmpleadoModel[]> getEmpleados(){
        EmpleadoModel[] empleados = sueldoService.getEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/getJustificativos")
    public ResponseEntity<JustificativoModel[]> getJustificativos(){
        JustificativoModel[] justificativos = sueldoService.getJustificativos();
        return ResponseEntity.ok(justificativos);
    }

    @GetMapping("/getAutorizaciones")
    public ResponseEntity<AutorizacionModel[]> getAutorizaciones(){
        AutorizacionModel[] autorizaciones = sueldoService.getAutorizaciones();
        return ResponseEntity.ok(autorizaciones);
    }

    @GetMapping("/getMarcasReloj")
    public ResponseEntity<DatarelojModel[]> getMarcasReloj(){
        DatarelojModel[] marcasReloj = sueldoService.getMarcasReloj();
        return ResponseEntity.ok(marcasReloj);
    }

    @GetMapping("/listarSueldos")
    public ResponseEntity<List<SueldoEntity>> listarSueldos(){
        sueldoService.eliminarSueldos();
        EmpleadoModel[] empleados           = sueldoService.getEmpleados();
        JustificativoModel[] justificativos = sueldoService.getJustificativos();
        DatarelojModel[] marcasReloj        = sueldoService.getMarcasReloj();
        AutorizacionModel[] autorizaciones  = sueldoService.getAutorizaciones();

        sueldoService.calcularPlanilla(empleados,justificativos,marcasReloj,autorizaciones);
        List<SueldoEntity> sueldos = sueldoService.listarSueldos();
        return ResponseEntity.ok(sueldos);
    }

    @PostMapping("/eliminar")
    public ResponseEntity<List<SueldoEntity>> eliminarSueldos(){
        sueldoService.eliminarSueldos();
        List<SueldoEntity> sueldos = sueldoService.listarSueldos();
        return ResponseEntity.ok(sueldos);
    }
    
    /*@PostMapping("/autenticar")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody UserInfo userInfo) {

        authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userInfo.getUsuario(), userInfo.getClave()));

        final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(userInfo.getUsuario());
        final String jwt = jwtUtilService.generateToken(userDetails);
        TokenInfo tokenInfo = new TokenInfo(jwt);

        return ResponseEntity.ok(tokenInfo);
    }*/

}
