package models.dao

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.AmazonSQSClient
import play.api.Play
import play.api.libs.json.Json

object FavouriteNotificationDAO {
  val awsAccessKeyId = Play.current.configuration.getString("aws.accessKeyId").get
  val awsSecretAccessKey = Play.current.configuration.getString("aws.secretAccessKey").get

  val sqsQueueName = Play.current.configuration.getString("aws.sqs.queueName").get

  val awsCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)
  val sqsClient = new AmazonSQSClient(awsCredentials)

  // Fetching the queue URL requires a call to the Amazon API so we'll keep this lazy
  lazy val sqsQueueUrl = sqsClient.getQueueUrl(sqsQueueName).getQueueUrl

  def send(recipientId: Int, friendId: Int, favouritedStudioId: Int) = {
    // Compile the notification into a JSON blob
    val jsonNotification = Json.obj(
      "recipient" -> recipientId,
      "friend" -> friendId,
      "studio" -> favouritedStudioId
    )

    // Send that JSON string to our Amazon SQS queue for distribution
    sqsClient.sendMessage(sqsQueueUrl, Json.stringify(jsonNotification))
  }
}
