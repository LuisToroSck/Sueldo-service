package com.example.sueldoservice.service;

import com.example.sueldoservice.entity.SueldoEntity;
import com.example.sueldoservice.model.*;
import com.example.sueldoservice.repository.SueldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SueldoService {

    @Autowired
    private SueldoRepository sueldoRepository;

    RestTemplate restTemplate = new RestTemplate();

    /*@Autowired
    private OficinaRRHH oficinaService;

    @Autowired
    private DataRelojService dataRelojService;*/

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

    public void calcularPlanilla(List<EmpleadoModel> empleados, List<JustificativoModel> justificativos, List<DatarelojModel> marcasReloj, List<AutorizacionModel> autorizaciones){

        int i=0;
        while(i<empleados.size()){

            // aquí hay que llamar al controlador de oficina rrhh
            double sueldoFijoMensual            = getSueldoFijoMensual(empleados.get(i).getRutEmpleado());
            double bonificacionPorAniosServicio = getBonificacionPorAniosServicio(empleados.get(i).getRutEmpleado());
            double pagoHorasExtras              = oficinaService.calcularPagoHorasExtras(empleados.get(i),autorizaciones);
            List<Integer> atrasos               = dataRelojService.calcularAtrasos(marcasReloj,empleados.get(i));
            double descuentoPorAtraso           = oficinaService.calcularDescuentoPorAtraso(sueldoFijoMensual,atrasos);
            double descuentoPorInasistencia     = oficinaService.calcularDescuentoPorInasistencia(sueldoFijoMensual,justificativos,empleados.get(i));

            double sueldoBruto           = calcularSueldoBruto(sueldoFijoMensual,bonificacionPorAniosServicio,pagoHorasExtras,descuentoPorAtraso,descuentoPorInasistencia);
            double cotizacionPrevisional = calcularCotizacionPrevisional(sueldoBruto);
            double cotizacionSalud       = calcularCotizacionSalud(sueldoBruto);

            double sueldoFinal = calcularSueldoFinal(sueldoBruto,cotizacionPrevisional,cotizacionSalud);

            SueldoEntity nuevoSueldo = new SueldoEntity();

            nuevoSueldo.setRutEmpleado(empleados.get(i).getRutEmpleado());
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
        EmpleadoModel[] empleados = restTemplate.getForObject("http://localhost:8002/empleado", EmpleadoModel[].class);
        return empleados;
    }

    public JustificativoModel[] getJustificativos(){
        JustificativoModel[] justificativos = restTemplate.getForObject("http://localhost:8004/justificativo", JustificativoModel[].class);
        return justificativos;
    }

    public AutorizacionModel[] getAutorizaciones(){
        AutorizacionModel[] autorizaciones = restTemplate.getForObject("http://localhost:8001/autorizacion", AutorizacionModel[].class);
        return autorizaciones;
    }

    public DatarelojModel[] getMarcasReloj(){
        DatarelojModel[] marcasReloj = restTemplate.getForObject("http://localhost:8003/datareloj", DatarelojModel[].class);
        return marcasReloj;
    }

    public double getSueldoFijoMensual(String rutEmpleado){
        double sueldoFijoMensual = restTemplate.getForObject("http://localhost:8005/oficina/getSueldoFijoMensual/" + rutEmpleado, double.class);
        return sueldoFijoMensual;
    }

    public double getBonificacionPorAniosServicio(String rutEmpleado){
        double bonificacionPorAniosServicio = restTemplate.getForObject("http://localhost:8005/oficina/getBonificacionPorAniosServicio/" + rutEmpleado, double.class);
        return bonificacionPorAniosServicio;
    }
}
