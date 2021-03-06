package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;
import br.com.ufrn.imd.telegrambot.util.Categoria;
import br.com.ufrn.imd.telegrambot.util.Localizacao;

import java.io.*;
import java.util.*;

public class ControladorCadastroBem extends Controlador {

    Bem bem;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorCadastroBem() {
        super("/addbem", 12);
        bem = new Bem();
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        
        List<Bem> bens = aux.listaBens(); // Lista com os bens listadas em 'bem.txt'.
        List<Localizacao> localizacoes = aux.listaLocalizacoes(); // Lista com as loocalizações listadas em 'localizacao.txt'.
        List<Categoria> categorias = aux.listaCategorias(); // Lista com as categorias listadas em 'categoria.txt'.
        List<String> mensagem = new ArrayList<String>();
        
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o código do bem a ser cadastrado?");
                incrementarPasso();
                break;
            case 2:
                String codigo = (mensagemRecebida);
                Bem encontrado = aux.buscarBemCodigo(bens,codigo);
                if(encontrado == null){ //Tratamento para checar se o código já esta sendo utilizado
                    bem.setCodigo(codigo);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida);
                    break;
                }
                else{
                    mensagem.add("O código informado já está sendo utilizado para o bem: "+ encontrado.getNome()+"\n Informe um código diferente.");
                }
            case 3:
                mensagem.add("Qual é o nome do bem a ser cadastrado?");
                incrementarPasso();
                break;
            case 4:
                bem.setNome(mensagemRecebida);
                incrementarPasso();
                mensagem = chat(mensagemRecebida);
                break;
            case 5:
                mensagem.add("Escreva uma pequena descrição desse bem.");
                incrementarPasso();
                break;
            case 6:
                bem.setDescricao(mensagemRecebida);
                incrementarPasso();
                mensagem = chat(mensagemRecebida);
                break;
            case 7:
                mensagem.add("Qual é a localização desse bem?\nAbaixo estão todas localizações cadastradas.");
                List<String> nomesLocalizacoes = aux.ImprimirNomeLocalizacoes(localizacoes); //Cria uma lista com os nomes das localizações presentes em 'localizacao.txt'
                for(String x : nomesLocalizacoes){
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 8: //Confere se a localização informada ja foi cadastrada anteriormente
                Localizacao localizacao = aux.buscaLocalizacao(localizacoes,mensagemRecebida);
                if (localizacao == null){
                    mensagem.add("A localização informada não foi encontrada no sistema, para cadastrar uma localização, " +
                            "primeiro insira '/cancelar' para sair desta operação e '/addlocalizacao' para iniciar o " +
                            "cadastro da localização.");
                }
                else {
                    bem.setLocalizacao(localizacao);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida);
                    break;
                }
            case 9:
                mensagem.add("Qual é a categoria desse bem?\nAbaixo estão todas categorias cadastradas.");
                List<String> nomesCategoria = aux.ImprimirNomeCategorias(categorias); //Cria uma lista com apenas os nomes das categorias presentes em 'categoria.txt'
                for(String x : nomesCategoria){
                    mensagem.add(x);
                }
                incrementarPasso();
                break;
            case 10: //Confere se a categoria informada ja foi cadastrada anteriormente
                Categoria categoria = aux.buscaCategoria(categorias,mensagemRecebida);
                if(categoria == null){
                    mensagem.add("A categoria informada não foi encontrada no sistema, para cadastrar uma nova categoria," +
                            " primeiro insira '/cancelar' para sair desta operação e '/addcategoria' para iniciar o cadastro" +
                            " da categoria.");
                }
                else{
                    bem.setCategoria(categoria);
                    incrementarPasso();
                    mensagem = chat(mensagemRecebida);
                    break;
                }
            case 11:
                mensagem.add(finalizarOperacao());
                incrementarPasso();
                break;
            case 12:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Armazenando em arquivo
                    BufferedWriter file = new BufferedWriter(new FileWriter("bem.txt",true));
                    file.write(bem.getCodigo() + "\n" + bem.getNome() + "\n" + bem.getDescricao() + "\n" + bem.getLocalizacao().getNome() +
                            "\n" + bem.getCategoria().getNome() + "\n------");
                    file.newLine();
                    file.close();

                    mensagem.add("Bem cadastrado com sucesso!");
                    incrementarPasso();
                    break;
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada.");
                    incrementarPasso();
                    break;
                }
                else{
                    mensagem.add("Resposta inválida.");
                }
            default:
                mensagem.add("Passo desconhecido, saindo da operação.");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        String mensagem = "Confirme se os dados abaixo estão corretos: \nCódigo: "+ bem.getCodigo() +"\nNome: " + bem.getNome() +
                "\nDescrição: " + bem.getDescricao() +"\nLocalização: " + bem.getLocalizacao().getNome() + "\nCategoria: " + bem.getCategoria().getNome() +
                "\n\nPosso salvar esses dados? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        bem = new Bem();
        setPasso(1);
    }
}
