package example
import monix.execution.Scheduler.Implicits.global

object Hello extends Greeting with App {
  println(greeting)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
