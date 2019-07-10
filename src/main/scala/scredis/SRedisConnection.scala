package scredis

import zio.{Task, ZIO}
import scredis.{Redis => Scredis}
import _root_.io.lbert.RedisConnection
import _root_.io.lbert.RedisConnection.Service
import _root_.io.lbert.RedisRequest
import scredis.protocol._

object SRedisConnection {
  trait ScredisLive extends RedisConnection {
    val connection = new Service {
      val redis = Scredis()

      override def send[A](request: RedisRequest[A]): Task[A] =
        ZIO.fromFuture(_ =>
          redis.send(ScredisRequest[A](ScredisCommand(request.command), request.args: _*))
        )
    }
  }
}

case class ScredisCommand(cmd: String) extends Command(cmd)
case class ScredisRequest[A](cmd: ScredisCommand, args: String*) extends Request[A](cmd, args) {
  override def decode: Decoder[A] = {
    case ErrorResponse(value) =>
      println(s"ErrorResponse($value)")
      ???
    case SimpleStringResponse(value) =>
      println(s"SimpleStringResponse($value)")
      ???
    case IntegerResponse(value) =>
      println(s"IntegerResponse($value)")
      ???
    case ClusterErrorResponse(error, message) =>
      println(s"ClusterErrorResponse($error, $message)")
      ???
    case BulkStringResponse(valueOpt) =>
      println(s"BulkStringResponse($valueOpt)")
      ???
    case ArrayResponse(length, buffer) =>
      println(s"ArrayResponse($length, $buffer)")
      ???
  }
}
