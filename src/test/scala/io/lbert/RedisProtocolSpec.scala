package io.lbert

import org.scalatest.{AsyncWordSpec, Matchers}
import zio._

class RedisProtocolSpec extends AsyncWordSpec with Matchers {

  val runtime = new DefaultRuntime {}

  "readLong" should {
    "get none if empty queue" in {
      runtime.unsafeRun(for {
        q <- Queue.bounded[Byte](10)
        o <- RedisProtocol.readLong(q)
      } yield o shouldBe None)
    }
  }
}
