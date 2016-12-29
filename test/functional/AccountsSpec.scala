package functional

import controllers.{Account, CreateAccountCommand}
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.http.Writeable
import play.api.libs.json.{JsValue, Json}
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsJson, contentType, route, status, _}

class AccountsSpec extends PlaySpec with OneAppPerTest {

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

  private def jsonRoute[T](req: FakeRequest[T])(implicit writes: Writeable[T]): JsValue = {
    val result = route(app, req).get
    status(result) mustBe OK
    contentType(result) mustBe Some("application/json")
    contentAsJson(result)
  }
}
