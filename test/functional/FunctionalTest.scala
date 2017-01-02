package functional

import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.http.Writeable
import play.api.libs.json.JsValue
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, contentType, route, status, _}
trait FunctionalTest extends PlaySpec with OneAppPerTest {

  protected def jsonRoute[T](req: FakeRequest[T])(implicit writes: Writeable[T]): JsValue = {
    val result = route(app, req).get
    status(result) mustBe OK
    contentType(result) mustBe Some("application/json")
    contentAsJson(result)
  }
}
