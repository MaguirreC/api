package med.voll.api.infra;

public class ValidacionIntegridad extends RuntimeException{
    public ValidacionIntegridad(String s) {
        super(s);
    }
}
