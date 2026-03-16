package io.github.charliecpshaw.cluedo.email

import io.github.charliecpshaw.cluedo.BuildConfig
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication

object CluedoAuthenticator : Authenticator() {

  private val passWordAuthentication = PasswordAuthentication(
    BuildConfig.GMAIL_ADDRESS,
    BuildConfig.GMAIL_APP_PASSWORD,
  )

  override fun getPasswordAuthentication(): PasswordAuthentication? {
    return passWordAuthentication
  }
}
