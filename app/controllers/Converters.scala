package controllers

import play.api.libs.json.Json

object Converters {

  implicit val AccountFormat = Json.format[Account]
  implicit val CreateAccountCommandFormat = Json.format[CreateAccountCommand]
}
