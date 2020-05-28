package x91slick

import slick.lifted.{TableQuery, Tag}
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration._

object SlickApp01a extends App {

  // Case class representing a row in our table:
  final case class Message(
                            sender:  String,
                            content: String,
                            id:      Long = 0L)

  // Helper method for creating test data:
  def freshTestData = Seq(
    Message("Dave", "Hello, HAL. Do you read me, HAL?"),
    Message("HAL",  "Affirmative, Dave. I read you."),
    Message("Dave", "Open the pod bay doors, HAL."),
    Message("HAL",  "I'm sorry, Dave. I'm afraid I can't do that.")
  )

  // Schema for the "message" table:
  final class MessageTable(tag: Tag) extends Table[Message](tag, "message") {

    def id      = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def sender  = column[String]("sender")
    def content = column[String]("content")

    def * = (sender, content, id).mapTo[Message]
  }

  // Base query for querying the messages table:
  lazy val messages: TableQuery[MessageTable] = TableQuery[MessageTable]
  val halSays: Query[MessageTable, Message, Seq] = messages.filter(_.sender === "HAL")

  val db: Database = Database.forConfig("chapter01my")

  // Helper method for running a query in this example file:
  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2 seconds)

//   Create the "messages" table:
  println("Creating database table")
  exec(messages.schema.createIfNotExists)

  // Create and insert the test data:
//  println("\nInserting test data")
//  exec(messages ++= freshTestData)

  // Run the test query and print the results:
//  println("\nSelecting all messages:")
//  exec( messages.result ) foreach { println }

  println("\nSelecting only messages from HAL:")
  exec( halSays.result ) foreach { println }


}
