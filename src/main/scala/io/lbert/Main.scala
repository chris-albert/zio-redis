package io.lbert

import zio.console._
import zio._
import scredis.SRedisConnection.ScredisLive
import zio.internal.PlatformLive

object Main extends scala.App {

  val env = new Console.Live with Redis.Live with ScredisLive
  val runtime = Runtime(env, PlatformLive.Default)

  val program: ZIO[Console with Redis, Throwable, Unit] = for {
    _ <- putStrLn("Staring redis program")
    _ <- RedisAlg.set("foo", "hi")
    r <- RedisAlg.get("foo")
    _ <- putStrLn(s"Got redis value [$r]")
  } yield ()


  runtime.unsafeRun(program)
}

trait Redis {
  val redis: Redis.Service[Any]
}

object Redis {
  trait Service[R] {
    def set(k: String, v: String): ZIO[R, Throwable, String]
    def get(k: String): ZIO[R, Throwable, String]
  }

  trait Live extends Redis with RedisConnection {
    val redis = new Service[Any] {
      override def set(k: String, v: String): Task[String] =
        connection.send(RedisRequest("SET", k, v))

      override def get(k: String): Task[String] =
        connection.send(RedisRequest("GET", k))
    }
  }

}

object RedisAlg extends Redis.Service[Redis] {
  override def set(
    k: String,
    v: String
  ): ZIO[Redis, Throwable, String] =
    ZIO.accessM(_.redis.set(k, v))

  override def get(k: String): ZIO[Redis, Throwable, String] =
    ZIO.accessM(_.redis.get(k))
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