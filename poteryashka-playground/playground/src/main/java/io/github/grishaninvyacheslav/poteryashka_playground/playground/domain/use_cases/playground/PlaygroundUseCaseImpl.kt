package io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.playground

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.PlaygroundReservationStatusEntity
import io.github.grishaninvyacheslav.poteryashka_playground.playground.presentation.PlaygroundState
import kotlinx.coroutines.tasks.await

class PlaygroundUseCaseImpl(
    private val cloudDatabase: FirebaseFirestore,
    private val realtimeDatabase: FirebaseDatabase
) : PlaygroundUseCase {
    private val mutablePlaygroundState: MutableLiveData<PlaygroundState> = MutableLiveData()
    override val playgroundState: LiveData<PlaygroundState> = mutablePlaygroundState

    override suspend fun finishGame() {
        realtimeDatabase
            .getReference("playground_1")
            .child("catch_a_mouse")
            .removeValue().await()
        cloudDatabase
            .collection("reservation_status")
            .document("playground_1")
            .get().await().data?.let { document ->
                with(
                    PlaygroundReservationStatusEntity(
                        document["curr_id"] as String,
                        document["expiration_timestamp"] as Long,
                        document["paid_ids"] as MutableList<String>
                    )
                ) {
                    this.curr_id = ""
                    this.expiration_timestamp = 0
                    cloudDatabase
                        .collection("reservation_status")
                        .document("playground_1")
                        .set(this).await()
                    mutablePlaygroundState.postValue(PlaygroundState.Finished)
                }
            }
    }
}