package actors

import akka.actor.{Props, Actor}
import models.{FavouriteNotification, Friend, FavouriteStudio}

import scala.concurrent.ExecutionContext.Implicits.global

object NotificationActor {
  def props: Props = Props(new NotificationActor)
}

class NotificationActor extends Actor {
  def receive = {
    case favourite: FavouriteStudio =>
      notifyFriendsOfFavourite(favourite)
  }

  private def notifyFriendsOfFavourite(favourite: FavouriteStudio): Unit = {
    // Retrieve list of friends from external service
    val fFriends = Friend.findAllFriends(favourite.userId)

    // Send a notification to each friend via Amazon SNS
    fFriends.map { friends =>
      friends.map { friend =>
        FavouriteNotification.send(friend, favourite)
      }
    }
  }
}
