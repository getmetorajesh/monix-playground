package example



/**
we need a Scheduler when async execution happens
*/
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 # TASK - the lazy future
 - task is lazy compared to Scala's future. nothing gets evaluated
*/
import monix.eval._
import scala.concurrent.Future
import monix.execution.FutureUtils

object monix1 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(418); 
	val task = Task { 1+1 };System.out.println("""task  : monix.eval.Task[Int] = """ + $show(task ));$skip(42); 
  val future: Future[Int] = task.runAsync;System.out.println("""future  : scala.concurrent.Future[Int] = """ + $show(future ));$skip(35); val res$0 = 
  Await.result(future, 20 seconds)


/**
# OBSERVABLE - the lazy & the async iterable
make a tick that gets triggered every second
*/
	import monix.reactive._;System.out.println("""res0: Int = """ + $show(res$0));$skip(285); 
	
	val tick = {
		Observable.interval(1 second)
		.filter(_ % 2 == 0)
		.map(_ * 2)
		.flatMap(x => Observable.fromIterable(Seq(x,x)))
		.take(5)
		.dump("Out")
	};System.out.println("""tick  : monix.reactive.Observable[Long] = """ + $show(tick ));$skip(41); 
	
	val cancellable = tick.subscribe();System.out.println("""cancellable  : monix.execution.Cancelable = """ + $show(cancellable ));$skip(23); 
  cancellable.cancel()
	
	
	/**
	# FutureUtils
	## to timeout a future that doesn't complete in dure time
	*/
	import monix.execution.FutureUtils.extensions._
	import concurrent.{Promise, Future};$skip(198); 
	val p = Promise[Unit]();System.out.println("""p  : scala.concurrent.Promise[Unit] = """ + $show(p ));$skip(45); 
	val never = p.future;System.out.println("""never  : scala.concurrent.Future[Unit] = """ + $show(never ));$skip(118); val res$1 =  // never ending future
	// create a new future that has a race cond with an error
	// signalling a TimeoutEcveption
	never.timeout(1 second);System.out.println("""res1: scala.concurrent.Future[Unit] = """ + $show(res$1));$skip(41); val res$2 = 
	
	FutureUtils.timeout(never, 2 seconds);System.out.println("""res2: scala.concurrent.Future[Unit] = """ + $show(res$2))}
}
