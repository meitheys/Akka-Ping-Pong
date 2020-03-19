package com.example;


import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingActor extends UntypedAbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(PingActor.class);
    }

    public static class Initialize {
    }

    public static class PingMessage {
        private final String text;

        public PingMessage(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private int counter = 0;
    private ActorRef pongActor = getContext().actorOf(PongActor.props(), "Pong");

    public void onReceive(Object message) throws Exception {
        if (message instanceof Initialize) {
            log.info("Iniciando o Ping-Pong");
            pongActor.tell(new PingMessage("Ping"), getSelf());
        } else if (message instanceof PongActor.PongMessage) {
            PongActor.PongMessage pong = (PongActor.PongMessage) message;
            log.info("Mensagem do Ping: {}", pong.getTextoMSG());
            counter += 1;
            if (counter == 3) {
                getContext().system().terminate();
            } else {
                getSender().tell(new PingMessage("Ping"), getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}