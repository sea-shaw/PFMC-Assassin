package io.github.charliecpshaw.cluedo.email

import io.github.charliecpshaw.cluedo.BuildConfig
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

    fun sendPlayerInfo(playerInfo: PlayerInfo, gameName: String) {
        val message = MimeMessage(session)
        message.setFrom(BuildConfig.GMAIL_ADDRESS)
        message.addRecipient(
            Message.RecipientType.TO,
            InternetAddress(playerInfo.playerEmailAddress!!),
        )
        val sanitisedGameName = gameName.removeCRLF()
        message.subject = "Cluedo Generator: $sanitisedGameName"
        message.setText("""
            Game: $sanitisedGameName
            Person: ${playerInfo.targetName}
            Place: ${playerInfo.placeName}
            Weapon: ${playerInfo.weaponName}
        """.trimIndent())
        Transport.send(message)
    }
}

private fun String.removeCRLF(): String {
    return filter { c -> c != '\n' && c != '\r' }
}
