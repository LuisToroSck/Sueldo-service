package com.example.sueldoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.sueldoservice.entity.SueldoEntity;
import com.example.sueldoservice.model.*;
import com.example.sueldoservice.service.SueldoService;

@Controller
@RequestMapping("/sueldo")
public class SueldoController {
    @Autowired
    SueldoService sueldoService;

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
        List<SueldoEntity> sueldos = sueldoService.listarSueldos();
        return ResponseEntity.ok(sueldos);
    }

    @PostMapping("/eliminar")
    public ResponseEntity<List<SueldoEntity>> eliminarSueldos(){
        sueldoService.eliminarSueldos();
        List<SueldoEntity> sueldos = sueldoService.listarSueldos();
        return ResponseEntity.ok(sueldos);
    }

}
