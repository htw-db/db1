package services.cluster

import javax.inject.Inject
import play.api.Logger
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

import models.ErrorMessage
import repositories.cluster.MongoDBRepository


class MongoDBClusterService @Inject()(mongoDBRepository: MongoDBRepository) {

  val logger: Logger = Logger(this.getClass)

  def createDatabase(databaseName: String): Either[ErrorMessage, Boolean] = {
    Await.result(mongoDBRepository.createDatabase(databaseName), Duration.Inf) match {
      case Failure(exception) =>
        val errorMessage = ErrorMessage(
          ErrorMessage.CODE_MONGODB_CLUSTER_CREATED_DATABASE_FAILED,
          ErrorMessage.MESSAGE_MONGODB_CLUSTER_CREATED_DATABASE_FAILED,
          developerMessage = exception.getMessage
        )
        logger.error(errorMessage.toString)
        Left(errorMessage)
      case Success(value) => Right(value)
    }
  }

  def createUser(databaseName: String, username: String, password: String): Either[ErrorMessage, Boolean] = {
    Await.result(mongoDBRepository.createUser(databaseName, username, password), Duration.Inf) match {
      case Failure(exception) =>
        val errorMessage = ErrorMessage(
          ErrorMessage.CODE_MONGODB_CLUSTER_CREATED_USER,
          ErrorMessage.MESSAGE_MONGODB_CLUSTER_CREATED_USER,
          developerMessage = exception.getMessage
        )
        logger.error(errorMessage.toString)
        Left(errorMessage)
      case Success(value) => Right(value)
    }
  }


}
