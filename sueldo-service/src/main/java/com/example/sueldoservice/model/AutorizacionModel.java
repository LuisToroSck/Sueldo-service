package com.example.sueldoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionModel {
    private String rutEmpleado;
    private Date fecha;
    private int cantidadHorasExtras;
    private int autorizado;
}