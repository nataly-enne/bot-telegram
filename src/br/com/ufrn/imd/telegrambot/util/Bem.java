package br.com.ufrn.imd.telegrambot.util;

public class Bem {
    private String codigo; // Inicialmente foi pensado como INTEIRO, mas como códigos de itens de patrimônio normalmente são misturas de números e letras, foi modificado para String
    private String nome;
    private String descricao;
    private Localizacao localizacao;
    private Categoria categoria;

    public Bem() {
        this.codigo = "";
        this.nome = "";
        this.descricao = "";
        this.localizacao = new Localizacao();
        this.categoria = new Categoria();
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String toString() {
        String bem = "Código: " + this.getCodigo()
                   + "\nNome: " + this.getNome()
                   + "\nDescrição: " + this.getDescricao()
                   + "\nLocalização: " + this.getLocalizacao().getNome()
                   + "\nCategoria: " + this.getCategoria().getNome() + "\n";
        return bem;
    }
}
