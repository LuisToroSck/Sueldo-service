package com.example.sueldoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JustificativoModel {
    private Long idJustificativo;
    private String rutEmpleado;
    private int justificada;
    private Date fecha;
}