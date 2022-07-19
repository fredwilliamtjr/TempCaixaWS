/**
 * Manutencao_cobranca_bancariaCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.5  Built on : May 06, 2017 (03:45:26 BST)
 */
package br.com.caixa.wsdl;


/**
 *  Manutencao_cobranca_bancariaCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class Manutencao_cobranca_bancariaCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public Manutencao_cobranca_bancariaCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public Manutencao_cobranca_bancariaCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for iNCLUI_BOLETO method
     * override this method for handling normal response from iNCLUI_BOLETO operation
     */
    public void receiveResultiNCLUI_BOLETO(
        br.com.caixa.wsdl.Manutencao_cobranca_bancariaStub.SERVICO_SAIDA result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from iNCLUI_BOLETO operation
     */
    public void receiveErroriNCLUI_BOLETO(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for aLTERA_BOLETO method
     * override this method for handling normal response from aLTERA_BOLETO operation
     */
    public void receiveResultaLTERA_BOLETO(
        br.com.caixa.wsdl.Manutencao_cobranca_bancariaStub.SERVICO_SAIDA result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from aLTERA_BOLETO operation
     */
    public void receiveErroraLTERA_BOLETO(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for bAIXA_BOLETO method
     * override this method for handling normal response from bAIXA_BOLETO operation
     */
    public void receiveResultbAIXA_BOLETO(
        br.com.caixa.wsdl.Manutencao_cobranca_bancariaStub.SERVICO_SAIDA result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from bAIXA_BOLETO operation
     */
    public void receiveErrorbAIXA_BOLETO(java.lang.Exception e) {
    }
}
