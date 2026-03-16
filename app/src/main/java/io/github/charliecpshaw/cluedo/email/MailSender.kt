package io.github.charliecpshaw.cluedo.email

import android.content.Context
import io.github.charliecpshaw.cluedo.BuildConfig
import io.github.charliecpshaw.cluedo.R
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import java.util.Properties
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MailSender {
  private val props = Properties()
  private val session = Session.getDefaultInstance(props, CluedoAuthenticator)

  init {
    props["mail.smtp.host"] = "smtp.gmail.com"
    props["mail.smtp.port"] = "587"
    props["mail.smtp.auth"] = "true"
    props["mail.smtp.starttls.enable"] = "true"
    props["mail.smtp.socketFactory.port"] = "587"
    props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
  }

  fun sendPlayerInfo(context: Context, playerInfo: PlayerInfo, gameName: String) {
    val message = MimeMessage(session)
    message.setFrom("${context.resources.getString(R.string.email_from)} <${BuildConfig.GMAIL_ADDRESS}>")
    message.addRecipient(
      Message.RecipientType.TO,
      InternetAddress(playerInfo.playerEmailAddress!!),
    )
    val sanitisedGameName = gameName.removeCRLF()
    message.subject = context.resources.getString(R.string.email_subject, sanitisedGameName)
    val text = context.resources.getString(
      R.string.email_text,
      playerInfo.targetName,
      playerInfo.placeName,
      playerInfo.weaponName,
    )
    message.setText(text)
    Transport.send(message)
  }
}

private fun String.removeCRLF(): String {
  return filter { c -> c != '\n' && c != '\r' }
}
