package functional

import controllers.{Account, CreateAccountCommand}
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, _}

class AccountsSpec extends FunctionalTest {

  import controllers.Converters._

  "Accounts routes" should {

    "should provide crud operations for accounts" in {
      val cmd = CreateAccountCommand("test")

      val createdAccount = jsonRoute(FakeRequest(POST, "/api/accounts").withJsonBody(Json.toJson(cmd))).as[Account]
      createdAccount.accountHolder mustBe cmd.accountHolder

      val accounts: Seq[Account] = jsonRoute(FakeRequest(GET, "/api/accounts")).as[Seq[Account]]
      accounts must not be empty
      accounts must contain only createdAccount
    }
  }


}
