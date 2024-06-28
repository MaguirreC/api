package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.ValidacionIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    List<ValidadorDeConsultas> validadores;




    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){

        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionIntegridad("este id para el paciente  no fue encontrado");
        }

        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionIntegridad("este id para el medico no fue encontrado");


        }

        validadores.forEach(v-> v.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();


        var medico = seleccionarMedico(datos);

        if (medico == null){
            throw new ValidacionIntegridad("no existen medicos disponibles para este horario");
        }

        var consulta = new Consulta(null,medico,paciente,datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());

        }
        if (datos.especialidad()== null){
            throw new ValidacionIntegridad("debe ser seleccionada un a especialidad");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());

    }
}
