package io.lbert

import zio.App
import zio.console._
import zio._

object Main extends App {

  def run(args: List[String]) =
    program.fold(_ => 1, _ => 0)

  val program = for {
    _ <- putStrLn("hi")
  } yield ()
}

trait Redis {
  val redis: Redis.Service
}

object Redis {
  trait Service {
    def set(k: String, v: String): Task[String]
    def get(k: String): Task[String]
  }

  trait Live extends Service {
    override def set(k: String, v: String): Task[String] = ???

    override def get(k: String): Task[String] = ???
  }

  object Live extends Live
}

case class RedisRequest[A](command: String, args: String*)
trait RedisConnection {
  val connection: RedisConnection.Service
}

object RedisConnection {
  trait Service {
    def send[A](request: RedisRequest[A]): Task[A]
  }
}