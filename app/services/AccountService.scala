package services

import java.util.UUID

import com.google.inject.{Inject, Singleton}
import controllers.{Account, CreateAccountCommand}
import org.joda.time.DateTime
import repositories.{AccountDb, AccountRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountService @Inject() (val accountRepository: AccountRepository)(implicit ec: ExecutionContext) {

  import accountRepository.dbConfig._

  def createAccount(cmd: CreateAccountCommand): Future[Account] = db.run {
    accountRepository.create(AccountDb(
      accountId = UUID.randomUUID(),
      accountHolder = cmd.accountHolder,
      createdOn = DateTime.now()
    )).map(db => Account(id = db.id.get, accountId = db.accountId, accountHolder = db.accountHolder, createdOn = db.createdOn))
  }

  def getAccounts: Future[Seq[Account]] = db.run {
    for {
      dbAccounts <- accountRepository.getAccounts
      apiAccounts = dbAccounts.map(db => Account(id = db.id.get, accountId = db.accountId, createdOn = db.createdOn, db.accountHolder))
    } yield apiAccounts
  }
}
