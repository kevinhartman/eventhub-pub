
/**
 * A simple program to publish lines from stdin to Event Hub.
 */
object EventHubPub {

  def main(args: Array[String]): Unit = {

    if (args.length != 6) {
      println("Usage: program progressDir PolicyName PolicyKey EventHubNamespace EventHubName" +
        " BatchDuration(seconds)")
      sys.exit(1)
    }

    val progressDir = args(0)
    val policyName = args(1)
    val policykey = args(2)
    val eventHubNamespace = args(3)
    val eventHubName = args(4)
    val batchDuration = args(5).toInt


  }
}
