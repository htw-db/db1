package models

import play.api.libs.json.{Json, OWrites}

sealed trait Response

case class ErrorResponse(errors: List[ErrorMessage]) extends Response

object ErrorResponse {
  implicit val errorBodyWrites: OWrites[ErrorResponse] = Json.writes[ErrorResponse]
}

case class ExistsInstanceResponse(exists: Boolean) extends Response

object ExistsInstanceResponse {
  implicit val existsInstanceResponseWrites: OWrites[ExistsInstanceResponse] = Json.writes[ExistsInstanceResponse]
}

case class LoginSuccessResponse(token: String) extends Response

object LoginSuccessResponse {
  implicit val loginSuccessResponseWrites: OWrites[LoginSuccessResponse] = Json.writes[LoginSuccessResponse]
}
