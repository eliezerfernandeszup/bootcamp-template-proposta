package br.com.zup.bootcamp.proposta.enums;


public enum AnalisePropostaStatus {

    COM_RESTRICAO{
        @Override
        public PropostaStatus toPropostaStatus() {
            return PropostaStatus.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        @Override
        public PropostaStatus toPropostaStatus() {
            return PropostaStatus.ELEGIVEL;
        }
    };

    public abstract PropostaStatus toPropostaStatus();
}
