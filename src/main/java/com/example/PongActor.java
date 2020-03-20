package com.example;

import akka.actor.UntypedAbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PongActor extends UntypedAbstractActor {

    //Logger
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //Criando propriedades
    public static Props props() {
        return Props.create(PongActor.class);
    }

    //Mensagem e Set and Getters
    public static class PongMessage {
        private final String textoMSG;

        public PongMessage(String text) {
            this.textoMSG = text;
        }

        public String getTextoMSG() {
            return textoMSG;
        }
    }

    //onReceive já é um método do UntypedAbstractActor, que faz literalmente o que o nome do método fala.
    public void onReceive(Object message) throws Exception {

        //Se mensagem vier do PingActor
        if (message instanceof PingActor.MensagemDoPing) {
            PingActor.MensagemDoPing ping = (PingActor.MensagemDoPing) message;

            //Printando 'Ping' que foi feita na classe PingActor
            log.info("Mensagem do Pong: {}", ping.getText());
            getSender().tell(new PongMessage("Pong"), getSelf());
        } else {
            unhandled(message);
        }
    }
}