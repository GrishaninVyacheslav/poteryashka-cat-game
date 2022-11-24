package io.github.grishaninvyacheslav.poteryashka_playground.playground.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.github.grishaninvyacheslav.core.data.provider.time.TimeProvider
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.PlaygroundEntity
import io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.playground.PlaygroundUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaygroundViewModel(
    private val playgroundUseCase: PlaygroundUseCase,
    private val realtimeDatabase: FirebaseDatabase,
    private val timeProvider: TimeProvider
) : ViewModel() {
    private val mutablePlaygroundState: MutableLiveData<PlaygroundState> = MutableLiveData()
    val playgroundState: LiveData<PlaygroundState>
        get() {
            mutablePlaygroundState.value = PlaygroundState.Loading
            realtimeDatabase.getReference("playground_1").child("catch_a_mouse")
                .addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                            val value = snapshot.getValue<PlaygroundEntity>()
                            Log.d("[MYLOG_2]", "PlaygroundViewModel value: $value")
                            value?.expiration_timestamp?.let {
                                val currentSeconds = timeProvider.getEpochSeconds()
                                if (it > currentSeconds) {
                                    if (mutablePlaygroundState.value is PlaygroundState.Active) {
                                        return@let
                                    }
                                    mutablePlaygroundState.postValue(
                                        PlaygroundState.Active(it - currentSeconds)
                                    )
                                } else {
                                    Log.d("[MYLOG_2]", "it <= currentSeconds: playgroundUseCase.finishGame()")
                                    playgroundUseCase.finishGame()
                                }
                            } ?: run {
                                Log.d("[MYLOG_2]", "expiration_timestamp == null: playgroundUseCase.finishGame()")
                                playgroundUseCase.finishGame()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                            Log.e("[MYLOG_2]", "Failed to read value.", error.toException())
                            playgroundUseCase.finishGame()
                        }
                    }
                })
            return mutablePlaygroundState
        }

    init {
        playgroundUseCase.playgroundState.observeForever { playgroundState ->
            mutablePlaygroundState.value = playgroundState
        }
    }

    fun gameTimeExpiration() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            Log.d("[MYLOG_2]", "gameTimeExpiration: playgroundUseCase.finishGame()")
            playgroundUseCase.finishGame()
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("[MYLOG_2]", "exceptionHandler: $throwable")
    }
}