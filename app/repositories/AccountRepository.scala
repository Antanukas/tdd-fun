package repositories

import java.sql.Timestamp
import java.util.UUID

import com.google.inject.{Inject, Singleton}
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.jdbc.{GetResult, PositionedParameters, SetParameter}

import scala.concurrent.ExecutionContext

case class AccountDb(id: Option[Long] = None, accountId: UUID, accountHolder: String, createdOn: DateTime)

@Singleton
class AccountRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {


  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.driver.api._

  implicit val accountDb = GetResult(r => AccountDb(
    Some(r.nextInt()),
    UUID.fromString(r.nextString()),
    r.nextString(),
    new DateTime(r.nextTimestamp().getTime)))

  implicit object SetDateTime extends SetParameter[DateTime] {
    override def apply(v: DateTime, pp: PositionedParameters): Unit = {
      pp.setTimestamp(new Timestamp(v.getMillis))
    }
  }

  implicit object SetUUID extends SetParameter[UUID] {
    override def apply(v: UUID, pp: PositionedParameters): Unit = {
      pp.setString(v.toString)
    }
  }

  def create(db: AccountDb): DBIO[AccountDb] = {
    sqlu"""insert into "accounts" values (null, ${db.accountId}, ${db.accountHolder}, ${db.createdOn})"""
      .flatMap { _ => sql"SELECT LASTVAL()".as[Int].head }
      .map(id => db.copy(id = Some(id)))
  }

  def getAccounts: DBIO[Seq[AccountDb]] = sql"""SELECT * FROM "accounts" """.as[AccountDb]

}
