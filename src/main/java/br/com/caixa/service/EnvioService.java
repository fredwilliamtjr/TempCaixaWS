package br.com.caixa.service;

import br.com.caixa.wsdl.Manutencao_cobranca_bancariaStub;

import java.rmi.RemoteException;

public class EnvioService {

    public Manutencao_cobranca_bancariaStub.Servico_saida_negocial_Type enviaBoleto(Manutencao_cobranca_bancariaStub.Servico_entrada_negocial_Type parametroEntrada) throws RemoteException {

        Manutencao_cobranca_bancariaStub stub = new Manutencao_cobranca_bancariaStub();

        Manutencao_cobranca_bancariaStub.SERVICO_ENTRADA servicoEntrada = new Manutencao_cobranca_bancariaStub.SERVICO_ENTRADA();
        servicoEntrada.setSERVICO_ENTRADA(parametroEntrada);

        Manutencao_cobranca_bancariaStub.SERVICO_SAIDA servico_saida = stub.iNCLUI_BOLETO(servicoEntrada);

        return servico_saida.getSERVICO_SAIDA();
    }
}
