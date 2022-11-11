package com.example.sueldoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatarelojModel {
    private String rutEmpleadoReloj;
    private Date fecha;
    private Time hora;
}