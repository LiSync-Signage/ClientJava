package plano;

public enum Plano {
    Basico(10, "Basico"),
    Corporativo(25, "Corporativo"),
    Interprise(50, "Interprise");

    private Integer qtdTvs;
    private String titulo;

    Plano(Integer qtdTvs, String titulo) {
        this.qtdTvs = qtdTvs;
        this.titulo = titulo;

    }

    public Integer getQtdTvs() {
        return qtdTvs;
    }
    public String getTitulo(){
        return titulo;
    }
}
