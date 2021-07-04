package services

import javax.inject.Inject
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import repositories.UserRepository
import models.{ErrorMessage, LdapUser, User}


class UserService @Inject()(ldapService: LdapService, tokenService: TokenService ,userRepository: UserRepository) {

  def login(username: String, password: String): Either[List[ErrorMessage], String] = {
    ldapService.authenticate(username, password) match {
      case Left(errors) => Left(errors)
      case Right(ldapUser) => Await.result(userRepository.getByUsername(ldapUser.username), Duration.Inf) match {
        case Some(user) => Right(tokenService.encode(user.username))
        case None => Await.result(
          userRepository.addUser(getUserFor(ldapUser)), Duration.Inf) match {
          case user => Right(tokenService.encode(user.username))
        }
      }
    }
  }

  private def getUserFor(ldapUser: LdapUser): User = {
    User(0L, ldapUser.username, ldapUser.firstName, ldapUser.lastName, ldapUser.mail, ldapUser.employeeType)
  }

}
