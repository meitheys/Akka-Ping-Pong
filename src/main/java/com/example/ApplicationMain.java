package com.example;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;

public class ApplicationMain {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Sistema: ");
        ActorRef pingActor = system.actorOf(PingActor.props(), "ping");
        pingActor.tell(new PingActor.Initialize(), null);
        system.getWhenTerminated();
    }
}