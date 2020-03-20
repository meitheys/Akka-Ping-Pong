package com.example;


import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingActor extends UntypedAbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //Criando PROPS, = configurações imutaveis do Ator
    public static Props props() {
        return Props.create(PingActor.class);
    }

    public static class Initialize {
    }

    public static class MensagemDoPing {
        private final String text;

        public MensagemDoPing(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private int counter = 0;

    //Criando Atror Pong com propriedades do Pong Actor
    private ActorRef pongActor = getContext().actorOf(PongActor.props(), "Pong");

    //Ao receber
    public void onReceive(Object mensagemAPrintar) throws Exception {

        //Se mensagem for do tipo Initialize, começa o jogo
        if (mensagemAPrintar instanceof Initialize) {
            log.info("Iniciando o Ping-Pong");
            pongActor.tell(new MensagemDoPing("Ping"), getSelf());

            //Se for do tipo Pong e
        } else if (mensagemAPrintar instanceof PongActor.PongMessage) {
            PongActor.PongMessage pong = (PongActor.PongMessage) mensagemAPrintar;
            log.info("Mensagem do Ping: {}", pong.getTextoMSG());
            counter += 1;

            //Se ação acontecer 3 vezes, termina
            if (counter == 3) {
                getContext().system().terminate();

                //Se não, continua comunicando com 'PongActor', mandando a msg: 'Ping'
            } else {
                getSender().tell(new MensagemDoPing("Ping"), getSelf());
            }
            //Erro
        } else {
            unhandled(mensagemAPrintar);
        }
    }
}