package io.lbert

import zio.{Chunk, Queue, Task}

trait RedisProtocol {
}

object RedisProtocol {

  def readLong(queue: Queue[Byte]): Task[Option[Long]] = {
    for {
      b <- queue.poll
    } yield None
  }
}
