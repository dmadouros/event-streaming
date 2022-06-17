package nile

import com.maxmind.geoip2.DatabaseReader
import java.io.File

fun main(args: Array<String>) {
    args.forEachIndexed { x: Int, y: String -> println("arg[$x] = $y") }
    val servers = args[0]
    val groupId = args[1]
    val inTopic = args[2]
    val goodTopic = args[3]
    val badTopic = args[4]
    val maxmindFile = args[5]

    val consumer = Consumer(servers, groupId, inTopic)
//    val producer = PassThruProducer(servers, goodTopic)
    val maxmind: DatabaseReader = DatabaseReader.Builder(File(maxmindFile)).build()
    val producer = FullProducer(servers, goodTopic, badTopic, maxmind)
    consumer.run(producer)
}
