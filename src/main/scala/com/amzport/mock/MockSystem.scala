package com.amzport.mock

import akka.Done
import akka.actor.ActorSystem
import akka.actor.typed.ActorRef
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.typed.scaladsl.ActorSource

import scala.concurrent.Future
import scala.util.Try

object MockSystem {

  implicit val system: ActorSystem = ActorSystem()
  import system.dispatcher

  final private val MockCompletion = TextMessage("Completion")
  final private val MockFailure = TextMessage("Failure")

  def setupMock(wsURL: String,
                receive: (Message, ActorRef[Message]) => Unit,
                connectComplete: (Try[Done], ActorRef[Message]) => Unit,
                connectClosed: Done => Unit): ActorRef[Message] = {
    val (outActor, publisher) = ActorSource.actorRef[Message](
      completionMatcher = { case MockCompletion => },
      failureMatcher = { case MockFailure => throw new Exception("failureMatcher") },
      bufferSize = 32,
      OverflowStrategy.dropNew
    ).toMat(Sink.asPublisher(false))(Keep.both).run()

    val theSink: Sink[Message, Future[Done]] =
      Sink.foreach { message =>
        receive(message, outActor)
      }

    val flow: Flow[Message, Message, Future[Done]] =
      Flow.fromSinkAndSourceMat(theSink, Source.fromPublisher(publisher))(Keep.left)

    val (upgradeResponse, closed) =
      Http().singleWebSocketRequest(WebSocketRequest(wsURL), flow)

    val connected = upgradeResponse.map { upgrade =>
      if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
        Done
      } else {
        throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
      }
    }

    connected.onComplete(done => connectComplete(done, outActor))
    closed.foreach(connectClosed)
    outActor
  }

}
