package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;



    public void  validar(DatosAgendarConsulta datos){
        var primerHorario = datos.fecha().withHour(7);
        var ultimaHorario = datos.fecha().withHour(18);


        var pacienteConConsulta =repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(),primerHorario,ultimaHorario);

        if (pacienteConConsulta){
            throw  new ValidationException("");
        }
    }

}
