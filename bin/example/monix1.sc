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

object monix1 {
	val task = Task { 1+1 }                   //> task  : monix.eval.Task[Int] = Task.FlatMap$1374677625
  val future: Future[Int] = task.runAsync         //> future  : scala.concurrent.Future[Int] = Async(Future(Success(2)),monix.exec
                                                  //| ution.cancelables.StackedCancelable$Impl@29b5cd00)
  Await.result(future, 20 seconds)                //> res0: Int = 2


/**
# OBSERVABLE - the lazy & the async iterable
make a tick that gets triggered every second
*/
	import monix.reactive._
	
	val tick = {
		Observable.interval(1 second)
		.filter(_ % 2 == 0)
		.map(_ * 2)
		.flatMap(x => Observable.fromIterable(Seq(x,x)))
		.take(5)
		.dump("Out")                      //> tick  : monix.reactive.Observable[Long] = monix.reactive.internal.operators.
                                                  //| DumpObservable@25359ed8
	}
	
	val cancellable = tick.subscribe()        //> 0: Out --> 0
                                                  //| 1: Out --> 0
                                                  //| cancellable  : monix.execution.Cancelable = monix.execution.Cancelable$Cance
                                                  //| lableTask@36f0f1be
  cancellable.cancel()                            //> 3: Out canceled
	
	
	/**
	# FutureUtils
	## to timeout a future that doesn't complete in dure time
	*/
	import monix.execution.FutureUtils.extensions._
	import concurrent.{Promise, Future}
	val p = Promise[Unit]()                   //> p  : scala.concurrent.Promise[Unit] = Future(<not completed>)
	val never = p.future // never ending future
                                                  //> never  : scala.concurrent.Future[Unit] = Future(<not completed>)
	// create a new future that has a race cond with an error
	// signalling a TimeoutEcveption
	never.timeout(1 second)                   //> res1: scala.concurrent.Future[Unit] = Future(<not completed>)
	
	FutureUtils.timeout(never, 2 seconds)     //> res2: scala.concurrent.Future[Unit] = Future(<not completed>)
}