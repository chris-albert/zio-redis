package scredis

import zio.{Task, ZIO}
import scredis.{Redis => Scredis}
import _root_.io.lbert.RedisConnection.Service
import _root_.io.lbert.RedisRequest
import scredis.protocol.{Command, Decoder, Request}

object RedisConnection {
  trait ScredisLive extends Service {
    val redis = Scredis()
    override def send[A](request: RedisRequest[A]): Task[A] =
      ZIO.fromFuture(_ =>
        redis.send(ScredisRequest[A](ScredisCommand(request.command), request.args:_*))
      )
  }
}

case class ScredisCommand(cmd: String) extends Command(cmd)
case class ScredisRequest[A](cmd: ScredisCommand, args: String*) extends Request[A](cmd, args) {
  override def decode: Decoder[Nothing] = ???
}
