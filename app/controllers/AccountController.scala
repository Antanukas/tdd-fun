package controllers

import java.util.UUID

import com.google.inject.Inject
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.AccountService

import scala.concurrent.ExecutionContext

case class Account(id: Long, accountId: UUID, createdOn: DateTime, accountHolder: String)
case class CreateAccountCommand(accountHolder: String)

class AccountController @Inject()(val accountService: AccountService)(implicit exec: ExecutionContext) extends Controller {

  import Converters._

  def getAccounts = Action.async {
    accountService.getAccounts.map(l => Ok(Json.toJson(l)))
  }

  def createAccount = Action.async(parse.json) { req =>
    val cmd = req.body.as[CreateAccountCommand]
    accountService.createAccount(cmd).map(l => Ok(Json.toJson(l)))
  }
}


