package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.data.FormError
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.Logger

import actions.{UserAction, UserRequest}
import forms.CreateRoleForm
import models.ErrorResponse
import services.RoleService


class RoleController @Inject()(cc: ControllerComponents, userAction: UserAction, roleService: RoleService) extends AbstractController(cc) {

  val logger: Logger = Logger(this.getClass)

  implicit object FormErrorWrites extends Writes[FormError] {
    override def writes(o: FormError): JsValue = Json.obj(
      "key" -> Json.toJson(o.key),
      "message" -> Json.toJson(o.message)
    )
  }

  def listInstanceRoles(instanceId: Long): Action[AnyContent] = userAction { implicit request: UserRequest[AnyContent] =>
    roleService.listInstanceRoles(instanceId, request.user) match {
      case Left(error) =>
        logger.error(error.toString)
        BadRequest(Json.toJson(ErrorResponse(List(error))))
      case Right(roles) => Ok(Json.toJson(roles))
    }
  }

  def addRole(): Action[AnyContent] = userAction { implicit request: UserRequest[AnyContent] =>
    CreateRoleForm.form.bindFromRequest.fold(
      formWithErrors => {
        logger.warn(formWithErrors.errors.toString)
        UnprocessableEntity(Json.toJson(formWithErrors.errors))
      },
      createRoleFormData => {
        roleService.addRole(createRoleFormData, request.user) match {
          case Left(error) =>
            logger.error(error.toString)
            BadRequest(Json.toJson(ErrorResponse(List(error))))
          case Right(instance) => Ok(Json.toJson(instance))
        }
      }
    )
  }
}