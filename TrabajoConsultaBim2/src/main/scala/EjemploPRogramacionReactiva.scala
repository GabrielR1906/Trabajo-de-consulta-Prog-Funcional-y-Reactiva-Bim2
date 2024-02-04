import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import monix.reactive._

import scala.concurrent.duration._
import scala.util.Random

object SimpleReactiveExample {
  def main(args: Array[String]): Unit = {
    val source = Observable.interval(1.second).map(_ => generateNonZeroRandom())
    val evenNumbers = source.filter(_ % 2 == 0)

    var counter = 1
    var product = 1

    val processedNumbers = evenNumbers.map { num =>
      println(s"Paso $counter: Generado número aleatorio: $num")

      if (num % 2 == 0) {
        println(s"Paso $counter: El número $num es par")
      }

      counter += 1
      println(s"Paso $counter: Multiplicando producto acumulativo por $num.")
      product *= num

      println(s"Paso $counter: Producto acumulativo actualizado: $product")

      if (counter % 3 == 0) {
        println(s"Paso $counter: ¡Múltiplo de 3 detectado!")
        println(s"Paso $counter: Elevando al cubo el producto acumulativo.")
        product = math.pow(product, 3).toInt
      }

      println()

      product
    }

    val consumer = Consumer.foreach[Int](result => println(s"Resultado final: $result"))
    val task = processedNumbers.consumeWith(consumer)

    task.runSyncUnsafe()
    Thread.sleep(10000)  // 10 segundos de pausa
  }

  // Genera un número aleatorio diferente de cero
  def generateNonZeroRandom(): Int = {
    val randomNum = Random.nextInt(9) + 1 // Rango de 1 a 9
    randomNum
  }
}

