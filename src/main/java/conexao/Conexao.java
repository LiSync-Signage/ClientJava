package conexao;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class Conexao {
    public abstract JdbcTemplate getconexaoLocal();
}
