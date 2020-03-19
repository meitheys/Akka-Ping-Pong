package com.example;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;

public class ApplicationMain {

    public static void main(String[] args) {
        //Criando ATOR "SISTEMA"
        ActorSystem system = ActorSystem.create("Sistema");

        //Criando ATOR 'ping', com as propriedades da classe PingActor
        ActorRef pingActor = system.actorOf(PingActor.props(), "ping");

        //Envia a msg
        pingActor.tell(new PingActor.Initialize(), null);
         system.getWhenTerminated();
    }
}