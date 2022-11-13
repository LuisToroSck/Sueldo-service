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

    /*@GetMapping("/calcularPlanilla")
    public ResponseEntity<List<SueldoEntity>> calcularPlanilla(){
        List<SueldoEntity> sueldos;
        return ResponseEntity.ok(sueldos);
    }*/

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
}
