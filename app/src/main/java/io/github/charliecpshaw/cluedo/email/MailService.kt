package io.github.charliecpshaw.cluedo.email

import io.github.charliecpshaw.cluedo.BuildConfig
import java.util.Properties
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

fun sendTestEmail() {
    val recipient = "charliecpshaw@gmail.com"

    val props = Properties()
    props["mail.smtp.host"] = "smtp.gmail.com"
    props["mail.smtp.port"] = "587"
    props["mail.smtp.auth"] = "true"
    props["mail.smtp.starttls.enable"] = "true"
    props["mail.smtp.socketFactory.port"] = "587"
    props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"

    val session = Session.getDefaultInstance(props, CluedoAuthenticator)
    val email = MimeMessage(session)
    email.setFrom(InternetAddress(BuildConfig.GMAIL_ADDRESS))
    email.addRecipient(Message.RecipientType.TO, InternetAddress(recipient))
    email.subject = "Test email"
    email.setText("Testing 123")

    Transport.send(email)
}
