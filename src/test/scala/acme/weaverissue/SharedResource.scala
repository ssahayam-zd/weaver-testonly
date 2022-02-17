package acme.weaverissue

import monix.eval.Task
import weaver._
import weaver.monixcompat.{GlobalResource, GlobalWrite, GlobalRead}
import cats.effect.Resource
import cats.syntax.all._

object SharedResources extends GlobalResource {

  override def sharedResources(global: GlobalWrite): Resource[Task, Unit] =
    baseResources.flatMap(global.putR(_))

  def baseResources: Resource[Task, String] = 
    Resource.eval(Task(println("========> creating base resource"))) >> Resource.pure[Task, String]("hello world!")

  // Provides a fallback to support running individual tests via testOnly
  def sharedResourceOrFallback(read: GlobalRead): Resource[Task, String] =
    read.getR[String]().flatMap {
      case Some(value) => Resource.eval(Task(value))
      case None        => baseResources 
    } 
}
