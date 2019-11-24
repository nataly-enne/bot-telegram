package br.com.ufrn.imd.telegrambot.controladores;

import br.com.ufrn.imd.telegrambot.util.Bem;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ControladorGerarRelatorioChat extends Controlador {

    FuncoesAuxiliares aux = new FuncoesAuxiliares();

    public ControladorGerarRelatorioChat() {
        super("/relatoriochat", 1);
    }

    @Override
    public List<String> chat(String mensagemRecebida) throws IOException {
        List<String> mensagem = new ArrayList<>();
        List<Bem> bens = aux.listaBens();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDate data = LocalDate.now();

        switch (getPasso()){
            case 1:
                BemLocalizacaoComparator bemLocalizacaoComparator = new BemLocalizacaoComparator();
                BemCategoriaComparator bemCategoriaComparator = new BemCategoriaComparator();
                BemNomeComparator bemNomeComparator = new BemNomeComparator();

                mensagem.add("Relatório criado em: " + dtf.format(data) + "\nListagem de bens agrupados por localização:\n");
                Collections.sort(bens,bemLocalizacaoComparator);
                for(Bem x : bens){
                    mensagem.add(x.toString()+"\n------\n");
                }

                mensagem.add(" Listagem de bens agrupados por categoria:\n");
                Collections.sort(bens,bemCategoriaComparator);
                for(Bem x : bens){
                    mensagem.add(x.toString()+"\n------\n");
                }

                mensagem.add(" Listagem de bens agrupados por nome:\n");
                Collections.sort(bens,bemNomeComparator);
                for(Bem x : bens){
                    mensagem.add(x.toString()+"\n------\n");
                }

                mensagem.add("Relatório finalizado");
                setPasso(getPasso() + 1);
                break;

        }
        return mensagem;
    }

    @Override
    protected String finalizarOperacao() {
        return null;
    }

    @Override
    public void reset() {
        setPasso(1);
    }
}