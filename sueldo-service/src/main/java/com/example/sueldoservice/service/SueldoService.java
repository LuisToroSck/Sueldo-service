package com.example.sueldoservice.service;

import com.example.sueldoservice.entity.SueldoEntity;
import com.example.sueldoservice.model.*;
import com.example.sueldoservice.repository.SueldoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SueldoService {

    @Autowired
    private SueldoRepository sueldoRepository;

    @Autowired
    RestTemplate restTemplate;

    public void eliminarSueldos(){
        sueldoRepository.deleteAll();
    }

    public SueldoEntity guardarSueldo(SueldoEntity sueldo){
        return sueldoRepository.save(sueldo);
    }

    public List<SueldoEntity> listarSueldos(){
        return sueldoRepository.findAll();
    }

    public double calcularSueldoBruto(double sueldoFijoMensual, double bonificacionPorAniosServicio, double pagoHorasExtras, double descuentoPorAtraso, double descuentoPorInasistencia){

        double sueldoBruto = 0;

        sueldoBruto = sueldoFijoMensual + bonificacionPorAniosServicio + pagoHorasExtras - descuentoPorAtraso - descuentoPorInasistencia;

        return sueldoBruto;
    }

    public double calcularCotizacionPrevisional(double sueldoBruto){

        double cotizacionPrevisional = sueldoBruto*0.1;

        return cotizacionPrevisional;
    }

    public double calcularCotizacionSalud(double sueldoBruto){

        double cotizacionSalud = sueldoBruto*0.08;

        return cotizacionSalud;
    }

    public double calcularSueldoFinal(double sueldoBruto, double cotizacionPrevisional, double cotizacionSalud){

        double sueldoFinal = sueldoBruto - cotizacionPrevisional - cotizacionSalud;

        return sueldoFinal;
    }

    public void calcularPlanilla(EmpleadoModel[] empleados, JustificativoModel[] justificativos, DatarelojModel[] marcasReloj, AutorizacionModel[] autorizaciones){

        int i=0;
        while(i<empleados.length){

            // aquí hay que llamar al controlador de oficina rrhh
            double sueldoFijoMensual            = getSueldoFijoMensual(empleados[i].getId());
            double bonificacionPorAniosServicio = getBonificacionPorAniosServicio(empleados[i].getId());
            double pagoHorasExtras              = getPagoHorasExtras(empleados[i].getId());
            List<Integer> atrasos               = getAtrasos(empleados[i].getId());
            double descuentoPorAtraso           = getDescuentoPorAtrasos(empleados[i].getId());
            double descuentoPorInasistencia     = getDescuentoPorInasistencia(empleados[i].getId());

            double sueldoBruto           = calcularSueldoBruto(sueldoFijoMensual,bonificacionPorAniosServicio,pagoHorasExtras,descuentoPorAtraso,descuentoPorInasistencia);
            double cotizacionPrevisional = calcularCotizacionPrevisional(sueldoBruto);
            double cotizacionSalud       = calcularCotizacionSalud(sueldoBruto);

            double sueldoFinal = calcularSueldoFinal(sueldoBruto,cotizacionPrevisional,cotizacionSalud);

            SueldoEntity nuevoSueldo = new SueldoEntity();

            nuevoSueldo.setRutEmpleado(empleados[i].getRutEmpleado());
            nuevoSueldo.setSueldoFijoMensual(sueldoFijoMensual);
            nuevoSueldo.setBonificacionAniosServicio(bonificacionPorAniosServicio);
            nuevoSueldo.setPagoHorasExtras(pagoHorasExtras);
            nuevoSueldo.setDescuentos(descuentoPorAtraso+descuentoPorInasistencia);
            nuevoSueldo.setSueldoBruto(sueldoBruto);
            nuevoSueldo.setCotizacionPrevisional(cotizacionPrevisional);
            nuevoSueldo.setCotizacionSalud(cotizacionSalud);
            nuevoSueldo.setSueldoFinal(sueldoFinal);

            guardarSueldo(nuevoSueldo);

            i=i+1;
        }
    }

    public EmpleadoModel[] getEmpleados(){
        EmpleadoModel[] empleados = restTemplate.getForObject("http://empleado-service/empleado", EmpleadoModel[].class);
        return empleados;
    }

    /*public List<EmpleadoModel> getEmpleados() {
        String url = "http://empleado-service/empleado";
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class); // Se usa lista de Object para mapear la repuesta JSON
        Object[] records = response.getBody(); // Obtener lista de empleados desde microservicio empleados
        if (records == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper(); // Mapper desde object a modelo Empleado
        return Arrays.stream(records)
                .map(empleado -> mapper.convertValue(empleado, EmpleadoModel.class))
                .collect(Collectors.toList());
    }*/

    public JustificativoModel[] getJustificativos(){
        JustificativoModel[] justificativos = restTemplate.getForObject("http://justificativo-service/justificativo", JustificativoModel[].class);
        return justificativos;
    }
    /*public List<JustificativoModel> getJustificativos() {
        String url = "http://justificativo-service/justificativo";
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class); // Se usa lista de Object para mapear la repuesta JSON
        Object[] records = response.getBody(); // Obtener lista de empleados desde microservicio empleados
        if (records == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper(); // Mapper desde object a modelo Empleado
        return Arrays.stream(records)
                .map(justificacion -> mapper.convertValue(justificacion, JustificativoModel.class))
                .collect(Collectors.toList());
    }*/

    public AutorizacionModel[] getAutorizaciones(){
        AutorizacionModel[] autorizaciones = restTemplate.getForObject("http://autorizacion-service/autorizacion", AutorizacionModel[].class);
        return autorizaciones;
    }

    public DatarelojModel[] getMarcasReloj(){
        DatarelojModel[] marcasReloj = restTemplate.getForObject("http://datareloj-service/datareloj", DatarelojModel[].class);
        return marcasReloj;
    }

    public double getSueldoFijoMensual(Long id){
        double sueldoFijoMensual = restTemplate.getForObject("http://oficinarrhh-service/oficina/getSueldoFijoMensual/" + id, double.class);
        return sueldoFijoMensual;
    }

    public double getBonificacionPorAniosServicio(Long id){
        double bonificacionPorAniosServicio = restTemplate.getForObject("http://oficinarrhh-service/oficina/getBonificacionPorAniosServicio/" + id, double.class);
        return bonificacionPorAniosServicio;
    }

    public double getPagoHorasExtras(Long id){
        double pagoHorasExtras = restTemplate.getForObject("http://oficinarrhh-service/oficina/getPagoHorasExtras/" + id, double.class);
        return pagoHorasExtras;
    }

    public List<Integer> getAtrasos(Long id){
        List<Integer> atrasos = restTemplate.getForObject("http://datareloj-service/datareloj/getAtrasos/" + id, List.class);
        return atrasos;
    }

    public double getDescuentoPorAtrasos(Long id){
        double descuentoPorAtrasos = restTemplate.getForObject("http://oficinarrhh-service/oficina/getDescuentoPorAtrasos/" + id, double.class);
        return descuentoPorAtrasos;
    }

    public double getDescuentoPorInasistencia(Long id){
        double descuentoPorInasistencia = restTemplate.getForObject("http://oficinarrhh-service/oficina/getDescuentoPorInasistencia/" + id, double.class);
        return descuentoPorInasistencia;
    }

}
