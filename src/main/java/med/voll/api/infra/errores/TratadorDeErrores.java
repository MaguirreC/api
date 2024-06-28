package med.voll.api.infra.errores;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import med.voll.api.infra.ValidacionIntegridad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(TypeNotPresentException.class)
    public ResponseEntity tratarError400(){
        return ResponseEntity.badRequest().build();
    }


    @ExceptionHandler(ValidacionIntegridad.class)
    public ResponseEntity errorHandlerValidacionesDeIntegridad(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
