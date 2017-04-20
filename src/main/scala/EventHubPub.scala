import scala.io.StdIn
import com.microsoft.azure.eventhubs.{EventData, EventHubClient}
import com.microsoft.azure.servicebus.ConnectionStringBuilder

/**
 * A simple program to publish lines from stdin to Event Hub.
 */
object EventHubPub {

  private val parameters = collection.mutable.Map[String, Option[String]](
    "--sasKeyName" -> None,
    "--sasKey" -> None,
    "--namespace" -> None,
    "--name" -> None
  )

  def expectNextArg(key: String, argsItr: BufferedIterator[String]) : String =
    if (argsItr.hasNext && !parameters.contains(argsItr.head))
      argsItr.next()
    else
      throw new IllegalArgumentException(s"Missing value for parameter '$key'")

  def main(args: Array[String]): Unit = {

    val itr = args.iterator.buffered
    while (itr.hasNext) {
      val key = itr.next()

      def nextVal(): String =
        expectNextArg(key, itr)

      if (parameters.contains(key)) {
        parameters(key) = Some(nextVal())
      }
      else
      {
        throw new IllegalArgumentException(s"Unknown parameter '$key'")
      }
    }

    // TODO: read missing values from config file

    val missingParam = parameters.collectFirst{case missing if missing._2.isEmpty => missing._1}

    if (missingParam.nonEmpty)
      throw new IllegalArgumentException(s"Missing parameter '$missingParam'")

    val connStr = new ConnectionStringBuilder(
      parameters("--namespace").get,
      parameters("--name").get,
      parameters("--sasKeyName").get,
      parameters("--sasKey").get
    )

    val ehClient = EventHubClient.createFromConnectionStringSync(connStr.toString)

    var line = ""
    while ({line = StdIn.readLine(); line != null}) {
      ehClient.sendSync(new EventData(line.getBytes("UTF-8")))
    }
  }
}
