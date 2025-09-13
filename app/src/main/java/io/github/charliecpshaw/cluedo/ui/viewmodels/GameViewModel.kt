package io.github.charliecpshaw.cluedo.ui.viewmodels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.github.charliecpshaw.cluedo.data.model.PlayerInfo
import io.github.charliecpshaw.cluedo.data.repository.GameRepository
import io.github.charliecpshaw.cluedo.email.MailSender
import io.github.charliecpshaw.cluedo.ui.navigation.GameDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GameViewModel(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val gameId: Long = savedStateHandle.toRoute<GameDestination>().id

    var uiState: StateFlow<GameUiState> = gameRepository.getGameStream(gameId)
        .filterNotNull()
        .map { GameUiState(name = it.name) }
        .combine(
            flow = gameRepository.getAllAlivePlayersInGameStream(gameId),
        ) { uiState, players ->
            uiState.copy(players = players)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GameUiState(),
        )

    suspend fun killPlayer(playerId: Long) {
        gameRepository.killPlayer(gameId, playerId)
    }

    suspend fun deleteGame() {
        gameRepository.deleteGame(gameId)
    }

    fun emailPlayer(context: Context, playerInfo: PlayerInfo) {
        val gameName = uiState.value.name
        MailSender.sendPlayerInfo(context, playerInfo, gameName)
    }
}

data class GameUiState(
    val name: String = "",
    val players: List<PlayerInfo> = listOf(),
)
