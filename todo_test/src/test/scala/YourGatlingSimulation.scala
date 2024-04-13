

//class YourGatlingSimulation extends Simulation {
//
//  val httpProtocol = http.baseUrl("http://localhost:8080")
//
//  val scn = scenario("YourScenario")
//    .repeat(10) {
//      exec(http("GetID")
//        .get("/getID/1"))
//    }
//    .exec(http("GetAll")
//            .get("/getAll"))
//    .exec(http("Startpage")
//                .get("/"))
//    .exec(http("getTitel")
//                .get("/getTitel/Test_titel"))
//
//
//  private val modelJsonString: String =
//    s"""
//       |{
//       |  "Id": 10,
//       |  "createdate": "2022-09-23 00:00:00.0",
//       |  "desci": "test_des",
//       |  "done": false,
//       |  "endDate": "2022-09-25 00:00:00.0",
//       |  "lastupdate": "2022-09-23 00:00:00.0",
//       |  "priority": 1,
//       |  "startDate": "2022-09-23 00:00:00.0",
//       |  "title": "Test_titel"
//       |}
//       |""".stripMargin
//
//
//  private val scn2 = scenario("POST")
//    .exec(http("PostModel")
//      .post("/todoPost")
//      .body(StringBody(modelJsonString)).asJson)
//    .pause(5)
//    .exec(http("GetModel_after_Post")
//      .get("/getID/10"))
//
//  setUp(
//    scn.inject(atOnceUsers(1)).protocols(httpProtocol),
//    scn2.inject(atOnceUsers(1)).protocols(httpProtocol)
//  )
//}
//

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
class YourGatlingSimulation_Counter extends Simulation {

  val httpProtocol = http.baseUrl("http://localhost:8080")

//  val scn = scenario("Scenario_Counter")
//      .exec(http("counterIncrease")
//        .get("/increaseCounter"))
//    }
//
//  val scn2 = scenario("Scenario_Counter_MultiTimes")
//    .repeat(10) {
//      exec(http("increaseCounter")
//        .get("/counterIncrease"))
//    }

    val scn = scenario("Scenario_Counter")
      .exec(http("counterIncrease")
        .get("/increaseCounter"))
      .repeat(10) {
        exec(http("increaseCounter")
          .get("/counterIncrease"))
      }

  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol),
    //scn2.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}