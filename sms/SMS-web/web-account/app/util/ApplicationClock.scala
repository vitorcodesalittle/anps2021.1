package util

import java.time.Clock

trait ApplicationClock {
  implicit val clock = Clock.systemUTC()
}
