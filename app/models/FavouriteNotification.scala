package models

import models.dao.FavouriteNotificationDAO

object FavouriteNotification {
  def send(recipient: Friend, favourite: FavouriteStudio) = {
    FavouriteNotificationDAO.send(
      recipientId = recipient.friendId,
      friendId = favourite.userId,
      favouritedStudioId = favourite.studioId
    )
  }
}
