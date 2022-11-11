package com.example.sueldoservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SueldoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = false, nullable = false)
    private Long id;

    private String rutEmpleado;
    private double sueldoFijoMensual;
    private double bonificacionAniosServicio;
    private double pagoHorasExtras;
    private double descuentos;
    private double sueldoBruto;
    private double cotizacionPrevisional;
    private double cotizacionSalud;
    private double sueldoFinal;
}
