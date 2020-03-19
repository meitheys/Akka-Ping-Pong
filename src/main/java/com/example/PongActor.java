package com.example;

import akka.actor.UntypedAbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PongActor extends UntypedAbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(PongActor.class);
    }

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
        if (message instanceof PingActor.MensagemDoPing) {
            PingActor.MensagemDoPing ping = (PingActor.MensagemDoPing) message;
            log.info("Mensagem do Pong: {}", ping.getText());
            getSender().tell(new PongMessage("Pong"), getSelf());
        } else {
            unhandled(message);
        }
    }
}