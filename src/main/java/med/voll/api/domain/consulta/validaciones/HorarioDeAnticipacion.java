package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos){
        var ahora = LocalDateTime.now();
        var horaDeconsulta = datos.fecha();

        var diferenciaDe30Min = Duration.between(ahora,horaDeconsulta).toMinutes()<30;


        if (diferenciaDe30Min){
            throw new ValidationException("las consultas se deben promar con 30 minutos de anticipacion");
        }



    }
}
