package br.com.caixa;

import br.com.caixa.service.EnvioService;
import br.com.caixa.wsdl.Manutencao_cobranca_bancariaStub;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        try {
            EnvioService envioService = new EnvioService();
            Manutencao_cobranca_bancariaStub.Servico_entrada_negocial_Type servicoEntrada = montaBoleto();
            Manutencao_cobranca_bancariaStub.Servico_saida_negocial_Type retorno = envioService.enviaBoleto(servicoEntrada);

            Manutencao_cobranca_bancariaStub.CONTROLE_NEGOCIAL_TYPE[] controle_negocial = retorno.getDADOS().getCONTROLE_NEGOCIAL();
            if (controle_negocial != null) {
                for (Manutencao_cobranca_bancariaStub.CONTROLE_NEGOCIAL_TYPE controle_negocial_type : controle_negocial) {
                    Manutencao_cobranca_bancariaStub.Mensagens_controle_negocial_Type mensagens1 = controle_negocial_type.getMENSAGENS();
                    String retorno1 = mensagens1.getRETORNO();
                    System.out.println("msg_retorno : " + retorno1);
                }
            }

            Manutencao_cobranca_bancariaStub.Inclui_boleto_saida_Type inclui_boleto = retorno.getDADOS().getINCLUI_BOLETO();
            if (inclui_boleto != null) {

                Manutencao_cobranca_bancariaStub.CODIGO_BARRAS_type1 codigo_barras = inclui_boleto.getCODIGO_BARRAS();
                System.out.println("codigo_barras : " + codigo_barras.getCODIGO_BARRAS_type0());
                Manutencao_cobranca_bancariaStub.LINHA_DIGITAVEL_type1 linha_digitavel = inclui_boleto.getLINHA_DIGITAVEL();
                System.out.println("linha_digitavel : " + linha_digitavel.getLINHA_DIGITAVEL_type0());
                Manutencao_cobranca_bancariaStub.URL_type1 url = inclui_boleto.getURL();
                System.out.println("url : " + url.getURL_type0());



            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Manutencao_cobranca_bancariaStub.Servico_entrada_negocial_Type montaBoleto() {

        Manutencao_cobranca_bancariaStub.Servico_entrada_negocial_Type model = new Manutencao_cobranca_bancariaStub.Servico_entrada_negocial_Type();

        Manutencao_cobranca_bancariaStub.HEADER_BARRAMENTO_TYPE header_barramento_type = montaHeader();
        model.setHEADER(header_barramento_type);

        Manutencao_cobranca_bancariaStub.Dados_entrada_Type dados_entrada_type = montaDadosIncluir();
        model.setDADOS(dados_entrada_type);

        return model;
    }

    private static Manutencao_cobranca_bancariaStub.Dados_entrada_Type montaDadosIncluir() {
        Manutencao_cobranca_bancariaStub.Dados_entrada_Type dados = new Manutencao_cobranca_bancariaStub.Dados_entrada_Type();

        Manutencao_cobranca_bancariaStub.Inclui_boleto_entrada_Type incluirBoleto = new Manutencao_cobranca_bancariaStub.Inclui_boleto_entrada_Type();

        Manutencao_cobranca_bancariaStub.CODIGO_BENEFICIARIO_type1 codigoBeneficiario = new Manutencao_cobranca_bancariaStub.CODIGO_BENEFICIARIO_type1();
        codigoBeneficiario.setCODIGO_BENEFICIARIO_type0(1138791);
        incluirBoleto.setCODIGO_BENEFICIARIO(codigoBeneficiario);

        Manutencao_cobranca_bancariaStub.Titulo_entrada_Type titulo = new Manutencao_cobranca_bancariaStub.Titulo_entrada_Type();

        Manutencao_cobranca_bancariaStub.NOSSO_NUMERO_type3 nossoNumero = new Manutencao_cobranca_bancariaStub.NOSSO_NUMERO_type3();
        nossoNumero.setNOSSO_NUMERO_type2(0);
        titulo.setNOSSO_NUMERO(nossoNumero);

        Manutencao_cobranca_bancariaStub.NUMERO_DOCUMENTO_type1 numeroDocumento = new Manutencao_cobranca_bancariaStub.NUMERO_DOCUMENTO_type1();
        numeroDocumento.setNUMERO_DOCUMENTO_type0("00000000001");
        titulo.setNUMERO_DOCUMENTO(numeroDocumento);

        Date dataVencimento = Date.from(LocalDate.now().plusDays(10).atStartOfDay().toInstant(ZoneOffset.UTC));
        titulo.setDATA_VENCIMENTO(dataVencimento);

        Manutencao_cobranca_bancariaStub.Valor_Type valor = new Manutencao_cobranca_bancariaStub.Valor_Type();
        valor.setValor_Type(BigDecimal.TEN);
        titulo.setVALOR(valor);

        Manutencao_cobranca_bancariaStub.TIPO_ESPECIE_type1 tipoEspecie = new Manutencao_cobranca_bancariaStub.TIPO_ESPECIE_type1();
        tipoEspecie.setTIPO_ESPECIE_type0((short) 99);
        titulo.setTIPO_ESPECIE(tipoEspecie);

        Manutencao_cobranca_bancariaStub.FLAG_ACEITE_type1 flagAceite = new Manutencao_cobranca_bancariaStub.FLAG_ACEITE_type1("S", true);
        titulo.setFLAG_ACEITE(flagAceite);

        //titulo.setDATA_EMISSAO();

        Manutencao_cobranca_bancariaStub.Juros_mora_Type jurosMora = new Manutencao_cobranca_bancariaStub.Juros_mora_Type();
        Manutencao_cobranca_bancariaStub.TIPO_type1 tipo = new Manutencao_cobranca_bancariaStub.TIPO_type1("ISENTO", true);
        jurosMora.setTIPO(tipo);
        //jurosMora.setDATA();
        Manutencao_cobranca_bancariaStub.Juros_mora_TypeChoice_type0 jurosMoraType = new Manutencao_cobranca_bancariaStub.Juros_mora_TypeChoice_type0();
        Manutencao_cobranca_bancariaStub.Valor_Type valorJurosMora = new Manutencao_cobranca_bancariaStub.Valor_Type();
        valorJurosMora.setValor_Type(BigDecimal.ZERO);
        jurosMoraType.setVALOR(valorJurosMora);
        //jurosMoraType.setPERCENTUAL();
        jurosMora.setJuros_mora_TypeChoice_type0(jurosMoraType);
        titulo.setJUROS_MORA(jurosMora);

        //titulo.setVALOR_ABATIMENTO();

        Manutencao_cobranca_bancariaStub.Pos_vencimento_Type posVencimento = new Manutencao_cobranca_bancariaStub.Pos_vencimento_Type();
        Manutencao_cobranca_bancariaStub.ACAO_type1 acao = new Manutencao_cobranca_bancariaStub.ACAO_type1("DEVOLVER", true); //PROTESTAR, DEVOLVER
        posVencimento.setACAO(acao);
        Manutencao_cobranca_bancariaStub.NUMERO_DIAS_type1 numeroDias = new Manutencao_cobranca_bancariaStub.NUMERO_DIAS_type1();
        numeroDias.setNUMERO_DIAS_type0((short) 999);
        posVencimento.setNUMERO_DIAS(numeroDias);
        titulo.setPOS_VENCIMENTO(posVencimento);

        Manutencao_cobranca_bancariaStub.CODIGO_MOEDA_type1 codigoMoenda = new Manutencao_cobranca_bancariaStub.CODIGO_MOEDA_type1();
        codigoMoenda.setCODIGO_MOEDA_type0((short) 9);
        titulo.setCODIGO_MOEDA(codigoMoenda);

        Manutencao_cobranca_bancariaStub.Pagador_Type pagador = new Manutencao_cobranca_bancariaStub.Pagador_Type();

        Manutencao_cobranca_bancariaStub.Pagador_TypeChoice_type0 pagadorType = new Manutencao_cobranca_bancariaStub.Pagador_TypeChoice_type0();

        Manutencao_cobranca_bancariaStub.Pagador_TypeSequence_type0 pagadorType0 = new Manutencao_cobranca_bancariaStub.Pagador_TypeSequence_type0();
        Manutencao_cobranca_bancariaStub.Cpf_Type cpf = new Manutencao_cobranca_bancariaStub.Cpf_Type();
        cpf.setCpf_Type(8013606767L);
        pagadorType0.setCPF(cpf);
        Manutencao_cobranca_bancariaStub.NOME_type1 nome = new Manutencao_cobranca_bancariaStub.NOME_type1();
        nome.setNOME_type0("FRED WILLIAM TORNO JUNIOR");
        pagadorType0.setNOME(nome);
        pagadorType.setPagador_TypeSequence_type0(pagadorType0);

//        Manutencao_cobranca_bancariaStub.Pagador_TypeSequence_type1 pagadorType1 = new Manutencao_cobranca_bancariaStub.Pagador_TypeSequence_type1();
//        Manutencao_cobranca_bancariaStub.Cnpj_Type cnpj = new Manutencao_cobranca_bancariaStub.Cnpj_Type();
//        cnpj.setCnpj_Type(40251563000177L);
//        pagadorType1.setCNPJ(cnpj);
//        Manutencao_cobranca_bancariaStub.RAZAO_SOCIAL_type1 raaoSocial = new Manutencao_cobranca_bancariaStub.RAZAO_SOCIAL_type1();
//        raaoSocial.setRAZAO_SOCIAL_type0("LM INFORMATICA LTDA");
//        pagadorType1.setRAZAO_SOCIAL(raaoSocial);
//        pagadorType.setPagador_TypeSequence_type1(pagadorType1);

        pagador.setPagador_TypeChoice_type0(pagadorType);

        titulo.setPAGADOR(pagador);

        incluirBoleto.setTITULO(titulo);

        dados.setINCLUI_BOLETO(incluirBoleto);

        return dados;
    }

    private static Manutencao_cobranca_bancariaStub.HEADER_BARRAMENTO_TYPE montaHeader() {
        Manutencao_cobranca_bancariaStub.HEADER_BARRAMENTO_TYPE header = new Manutencao_cobranca_bancariaStub.HEADER_BARRAMENTO_TYPE();

        Manutencao_cobranca_bancariaStub.VERSAO_type1 versao = new Manutencao_cobranca_bancariaStub.VERSAO_type1();
        versao.setVERSAO_type0("3.0");
        header.setVERSAO(versao);

        String autenticacao = getAutenticacao(
                "1138791",
                "14000000000000001",
                LocalDate.now().plusDays(10),
                new BigDecimal("90.00"),
                "37.294.377/0001-00");
        header.setAUTENTICACAO(autenticacao);

        Manutencao_cobranca_bancariaStub.USUARIO_SERVICO_type1 usuarioServico = new Manutencao_cobranca_bancariaStub.USUARIO_SERVICO_type1();
        usuarioServico.setUSUARIO_SERVICO_type0("SGCBS02P"); //SEMPRE SGCBS02P
        header.setUSUARIO_SERVICO(usuarioServico);

        //header.setUSUARIO();

        Manutencao_cobranca_bancariaStub.OPERACAO_type1 operacao = new Manutencao_cobranca_bancariaStub.OPERACAO_type1();
        operacao.setOPERACAO_type0("INCLUI_BOLETO"); //INCLUI_BOLETO, BAIXA_BOLETO, ALTERA_BOLETO
        header.setOPERACAO(operacao);

        //header.setINDICE();

        Manutencao_cobranca_bancariaStub.SISTEMA_ORIGEM_type1 sistemaOrigem = new Manutencao_cobranca_bancariaStub.SISTEMA_ORIGEM_type1();
        sistemaOrigem.setSISTEMA_ORIGEM_type0("SIGCB"); //Sempre SIGCB
        header.setSISTEMA_ORIGEM(sistemaOrigem);

        Manutencao_cobranca_bancariaStub.UNIDADE_type1 unidade = new Manutencao_cobranca_bancariaStub.UNIDADE_type1();
        unidade.setUNIDADE_type0("2904"); //NÚMERO DA AGÊNCIA DE RELACIONAMENTO, COM 4 POSIÇÕES, SEM DV
        header.setUNIDADE(unidade);

        //Manutencao_cobranca_bancariaStub.IDENTIFICADOR_ORIGEM_type1 identificadorOrigem = new Manutencao_cobranca_bancariaStub.IDENTIFICADOR_ORIGEM_type1();
        //identificadorOrigem.setIDENTIFICADOR_ORIGEM_type0("127.0.0.1"); //IP DA MÁQUINA DO PAGADOR QUE REQUISITOU O REGISTRO DO BOLETO
        //header.setIDENTIFICADOR_ORIGEM(identificadorOrigem);

        Manutencao_cobranca_bancariaStub.DATA_HORA_type1 dataHora = new Manutencao_cobranca_bancariaStub.DATA_HORA_type1();
        dataHora.setDATA_HORA_type0(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        header.setDATA_HORA(dataHora);

        Manutencao_cobranca_bancariaStub.ID_PROCESSO_type1 idProcesso = new Manutencao_cobranca_bancariaStub.ID_PROCESSO_type1();
        idProcesso.setID_PROCESSO_type0("1138791"); //Código do BENEFICIÁRIO, sem DV
        header.setID_PROCESSO(idProcesso);

        return header;
    }

    private static String getAutenticacao(String codigoBeneficiario,
                                          String nossoNumero,
                                          LocalDate dataVencimento,
                                          BigDecimal valor,
                                          String cpfCnpjBeneficiario) {

        // codigoBeneficiario = ""; //7
        // nossoNumero = ""; //17
        // dataVencimento = ""; //DDMMAAAA
        // valor = ""; //15
        // cpfCnpjBeneficiario = ""; //12

        if (StringUtils.isNumeric(codigoBeneficiario)) {
            codigoBeneficiario = StringUtils.leftPad(codigoBeneficiario, 7, "0");
        }

        if (StringUtils.isNumeric(nossoNumero)) {
            nossoNumero = StringUtils.leftPad(nossoNumero, 17, "0");
        }

        String dataVencimentoString = "";
        if (dataVencimento != null) {
            dataVencimentoString = dataVencimento.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        }

        String valorString = "";
        if (valor != null) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            numberFormat.setMinimumFractionDigits(2);
            String valueOf = numberFormat.format(valor);
            valueOf = valueOf.replaceAll("\\D+", "");
            valorString = StringUtils.leftPad(valueOf, 15, "0");
        }

        if (StringUtils.isNotBlank(cpfCnpjBeneficiario)) {
            cpfCnpjBeneficiario = cpfCnpjBeneficiario.replaceAll("\\D+", "");
            cpfCnpjBeneficiario = StringUtils.leftPad(cpfCnpjBeneficiario, 14, "0");
        }

        String dados = codigoBeneficiario
                .concat(nossoNumero)
                .concat(dataVencimentoString)
                .concat(valorString)
                .concat(cpfCnpjBeneficiario);

        MessageDigest MD;
        byte[] HASH;
        try {
            MD = MessageDigest.getInstance("SHA-256");
            HASH = MD.digest(dados.getBytes("ISO8859-1"));
            BASE64Encoder enc = new BASE64Encoder();
            return enc.encode(HASH);

        } catch (Exception EX) {
            return null;
        }
    }

    private static void baixarBoleto(String urlBoleto, String codigoBarras) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, KeyManagementException {

        SSLContext context = SSLContext.getInstance("TLSv1.2");

        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(Main.class.getResourceAsStream("/caixa/cacert-caixa"), "changeit".toCharArray());
        trustManagerFactory.init(trustStore);

        context.init(null, trustManagerFactory.getTrustManagers(), null);

        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        URL url = new URL(urlBoleto);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        FileUtils.copyURLToFile(connection.getURL(),
                new File("/caixa/Boleto-" + codigoBarras + ".pdf"));

    }

}