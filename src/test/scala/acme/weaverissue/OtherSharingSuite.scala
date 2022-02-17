package acme.weaverissue

import cats.effect.Resource
import monix.eval.Task
import weaver.monixcompat.{GlobalRead, MutableTaskSuite}

class OtherSharingSuite(global: GlobalRead)
    extends MutableTaskSuite {
  type Res = String

  def sharedResource: Resource[Task, String] = SharedResources.sharedResourceOrFallback(global)

  test("oops, forgot something here")(sharedString => Task(expect(sharedString == "hello world!")))
}
