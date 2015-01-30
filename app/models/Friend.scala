package models

import models.dao.FriendDAO

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Friend {
  def findAllFriends(userId: Int): Future[Set[Friend]] =
    FriendDAO.index(userId).map(_.toSet)
}

case class Friend(friendId: Int)
