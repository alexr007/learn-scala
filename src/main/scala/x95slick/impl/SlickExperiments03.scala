package x95slick.impl

import x95slick.db.DatabaseLayer

final class SlickExperiments03 {
  private val persistence = new DatabaseLayer

  import persistence._
  import persistence.profile.api._ // actually we import slick.jdbc.PostgresProfile.api._

  def books_schema: Unit = {
    exec(books.schema.create)
  }

  def books_insert: Unit = {
    val action = books ++= Seq(
      BookShelfRow("Java", 1),
      BookShelfRow("Java", 4),
      BookShelfRow("JavaScript", 10),
      BookShelfRow("Scala", 2),
      BookShelfRow("Slick", 1)
    )
    exec(action)
  }

  def books_select1: Unit = {
    // count
    val count = exec(books.length.result)
    println(s"count: $count") // 5

    // numbers of distinct
    val count2: Seq[String] = exec(books.map(_.topic).distinct.result)
    println(s"distinct count: ${count2.length}, items: ${count2}") // Vector(Slick, JavaScript, Scala, Java)

    // max count
    val max3 = exec(books.map(_.number).max.result)
    println(s"max count by all topics: ${max3.getOrElse("no")}")

    val partsByVendor =
      partnumbers
//        .join(vendors).on((pn: PartNumbers, ve: VendorTable) => pn.vendor_id === ve.id)
        .join(vendors).on(_.vendor_id === _.id)           // Seq[(PartNumber, Vendor)]
//        .groupBy { case (pn: PartNumbers, vendor: VendorTable) => vendor.name }     // choose ONE field (Vendor.name) from whole set to group by. group by should be followed by MAP !!!
        .groupBy { case (pn, vendor) => vendor.name }     // choose ONE field (Vendor.name) from whole set to group by. group by should be followed by MAP !!!
//        .map { case (name: Rep[String], group_q) => (name, group_q.length) }
        .map { case (name: Rep[String], group_q) => name -> group_q.length }
    println(s"partsByVendor: ${exec(partsByVendor.result)}")

    val action41 = for {
      pn <- partnumbers
      ve <- vendors
      if pn.vendor_id === ve.id
      if ve.name like "TIM%"
    } yield (ve.name, pn.number)

    val result41 = exec(action41.result) // Vector((TIMKEN,345678QWRT), (TIMKEN,ZX345678QWRT), (TIMKEN,2345678QWE))
    println(s"grouped: $result41")

    // they are completely composable!

    val action42 = action41
      .groupBy { case (ve_name: Rep[String], part_num: Rep[String]) => ve_name }
      .map { case (ve_name, list) => ve_name -> list.length }

    val result42 = exec(action42.result)
    println(s"grouped: $result42")

    val action44 = books
      .groupBy { _.topic }
      .map { case (b, g) => (b, g.length) }
    val result44 = exec(action44.result)
    println(s"grouped44: $result44")
    // all
//    val query = books
//    println(s"Just All the books")
//    exec(books.result) foreach println
  }
}
