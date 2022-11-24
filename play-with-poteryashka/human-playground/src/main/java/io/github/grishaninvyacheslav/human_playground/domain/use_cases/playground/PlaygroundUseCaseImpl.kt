package io.github.grishaninvyacheslav.human_playground.domain.use_cases.playground

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.core.data.provider.time.TimeProvider
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.PlaygroundReservationStatusEntity
import io.github.grishaninvyacheslav.human_playground.data.repositories.DonationRepository
import io.github.grishaninvyacheslav.human_playground.presentation.playground.PlaygroundState
import kotlinx.coroutines.tasks.await

class PlaygroundUseCaseImpl(
    private val realtimeDatabase: FirebaseDatabase,
    private val cloudDatabase: FirebaseFirestore,
    private val donationRepository: DonationRepository,
    private val timeProvider: TimeProvider
) : PlaygroundUseCase {
    private val mutablePlaygroundState: MutableLiveData<PlaygroundState> = MutableLiveData()
    override val playgroundState: LiveData<PlaygroundState> = mutablePlaygroundState

    override suspend fun tryLaunchGame() {
        cloudDatabase
            .collection("reservation_status")
            .document("playground_1")
            .get().await().data?.let { it ->
                with(
                    PlaygroundReservationStatusEntity(
                        it["curr_id"] as String,
                        it["expiration_timestamp"] as Long,
                        it["paid_ids"] as MutableList<String>
                    )
                ) {
                    val userId = donationRepository.getId()
                    if (this.curr_id.isNotBlank()) {
                        setPlaygroundAsReserved()
                        return@with
                    }
                    if (!this.paid_ids.contains(userId)) {
                        mutablePlaygroundState.postValue(PlaygroundState.Free)
                        return@with
                    }
                    if (mutablePlaygroundState.value != PlaygroundState.Paid) {
                        return@with
                    }
                    this.paid_ids.remove(userId)
                    this.curr_id = userId
                    launchGame(this)
                }
            }
    }

    override suspend fun updateState() {
        cloudDatabase
            .collection("reservation_status")
            .document("playground_1")
            .get().await().data?.let { it ->
                with(
                    PlaygroundReservationStatusEntity(
                        it["curr_id"] as String,
                        it["expiration_timestamp"] as Long,
                        it["paid_ids"] as MutableList<String>
                    )
                ) {
                    val currentSeconds = timeProvider.getEpochSeconds()
                    if(this.curr_id == donationRepository.getId() && this.expiration_timestamp > currentSeconds){
                        mutablePlaygroundState.postValue(
                            PlaygroundState.Playing(this.expiration_timestamp - currentSeconds)
                        )
                        return@with
                    }
                    if (this.curr_id.isNotBlank()) {
                        setPlaygroundAsReserved()
                        return@with
                    }
                    if (!this.paid_ids.contains(donationRepository.getId())) {
                        mutablePlaygroundState.postValue(PlaygroundState.Free)
                        return@with
                    } else {
                        mutablePlaygroundState.postValue(PlaygroundState.Paid)
                        return@with
                    }
                }
            }
    }

    private suspend fun launchGame(reservationStatus: PlaygroundReservationStatusEntity) {
        val tariffSeconds = (donationRepository.getTimeToPaymentTariff().minutes * 60F).toLong()
        val currentSeconds = timeProvider.getEpochSeconds()
        Log.d("[MYLOG]", "launchGame tariffSeconds: $tariffSeconds")
        val expirationTimestamp = currentSeconds + tariffSeconds
        Log.d("[MYLOG]", "launchGame expirationTimestamp: $expirationTimestamp") // TODO: expirationTimestamp отправлять в при playground_status == READY и после этого присваивать PLAYING
        reservationStatus.expiration_timestamp = expirationTimestamp
        cloudDatabase
            .collection("reservation_status")
            .document("playground_1")
            .set(reservationStatus).await()
        realtimeDatabase
            .getReference("playground_1")
            .child("catch_a_mouse")
            .child("expiration_timestamp")
            .setValue(expirationTimestamp).await() // TODO: вместо expirationTimestamp отправлять playground_status: STANDBY

        // TODO: Playing присваивать в слушателе playground_status, когда он равен READY
        mutablePlaygroundState.postValue(
            PlaygroundState.Playing(expirationTimestamp - currentSeconds)
        )
        Log.d(
            "[MYLOG]",
            "mutablePlaygroundState.value: ${mutablePlaygroundState.value}"
        )
    }

    private suspend fun setPlaygroundAsReserved() {
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
                    val currentSeconds = timeProvider.getEpochSeconds()
                    mutablePlaygroundState.postValue(PlaygroundState.Reserved(this.expiration_timestamp - currentSeconds))
                }
            }
    }
}
