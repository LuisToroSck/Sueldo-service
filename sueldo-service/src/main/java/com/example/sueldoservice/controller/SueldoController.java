package com.example.sueldoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.sueldoservice.entity.SueldoEntity;
import com.example.sueldoservice.service.SueldoService;

@Controller
@RequestMapping("/sueldo")
public class SueldoController {
    @Autowired
    SueldoService sueldoService;

    @GetMapping("/calcularPlanilla")
    public ResponseEntity<List<SueldoEntity>> calcularPlanilla(){
        List<SueldoEntity> sueldos;
        return ResponseEntity.ok(sueldos);
    }
}
