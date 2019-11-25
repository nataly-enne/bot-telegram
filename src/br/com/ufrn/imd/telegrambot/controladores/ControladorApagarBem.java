package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.*;

import java.io.*;
import java.util.*;

public class ControladorApagarBem extends Controlador {

    Bem bem;
    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorApagarBem() {
        super("/apagarbem", 4);
        bem = new Bem();
    }
    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<Bem> bens = aux.listaBens();
        List<String> mensagem = new ArrayList<String>();
        switch (getPasso()){
            case 1:
                mensagem.add("Qual é o código do bem que vai ser excluido?");
                setPasso(getPasso() + 1);
                break;
            case 2:
                Bem encontrado = aux.buscarBemCodigo(bens,mensagemRecebida);
                if(encontrado == null){
                    mensagem.add("O codigo informado não está sendo utilizado no momento, informe um código diferente");

                }
                else{
                    mensagem.add("O codigo informado está sendo utilizado para o bem:\n"+ encontrado.toString());
                    bem = encontrado;
                    setPasso(getPasso() + 1);
                    break;
                }
            case 3:
                mensagem.add(finalizarOperacao());
                setPasso(getPasso() + 1);
                break;
            case 4:
                if(mensagemRecebida.toLowerCase().equals("s")){
                    //Removendo bem selecionado da lista de bens
                    bens = aux.removerBem(bens, bem.getCodigo());
                    //Apagando os dados do arquivo
                    PrintWriter writer = new PrintWriter("bem.txt");
                    writer.print("");
                    writer.close();

                    //Reescrevendo dados do arquivo com a lista de bens atualizada
                    BufferedWriter file = new BufferedWriter(new FileWriter("bem.txt",true));
                    for(Bem x : bens){
                        file.write(x.getCodigo() + "\n" + x.getNome() + "\n" + x.getDescricao() + "\n"
                                + x.getLocalizacao().getNome() + "\n" + x.getCategoria().getNome() + "\n------");
                        file.newLine();
                    }
                    file.close();

                    mensagem.add("Bem apagado com sucesso!");
                    setPasso(getPasso() + 1);
                }
                else if(mensagemRecebida.toLowerCase().equals("n")){
                    mensagem.add("Operação cancelada");
                    setPasso(getPasso() + 1);
                }
                else{
                    mensagem.add("Resposta inválida");
                }
                break;
            default:
                mensagem.add("Passo desconhecido");
                break;
        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        String mensagem = "Posso Apagar o bem? (s/n)";
        return mensagem;
    }

    @Override
    public void reset() {
        setPasso(1);
    }
}
