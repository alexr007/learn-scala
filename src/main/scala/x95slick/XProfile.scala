package x95slick

import slick.jdbc.JdbcProfile

trait XProfile {
  /**
    * an abstract declaration,
    * should be declared explicitly
    * on implementation phase
    */
  val profile: JdbcProfile

}
