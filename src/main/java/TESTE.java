import dao.ComponenteDAO;
import dao.TelevisaoDAO;
import models.Componente;

public class TESTE {
    public static void main(String[] args) {
        Componente componente = new Componente();
        ComponenteDAO componenteDAO = new ComponenteDAO();
        TelevisaoDAO televisaoDAO = new TelevisaoDAO();

        System.out.println(componenteDAO.buscarTipoComponentePorIdTv("CPU",1).get(0).getIdComponente());

    }
}
