package io.github.grishaninvyacheslav.human_playground.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.PlaygroundReservationStatusEntity
import io.github.grishaninvyacheslav.human_playground.data.entities.TimeToPaymentTariff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class DonationRepositoryImpl(
    private val cloudDatabase: FirebaseFirestore
) : DonationRepository {
    private var guid: String? = null
    private var timeToPaymentTariff: TimeToPaymentTariff? = null
    private var currentPayment = 0F

    override suspend fun addPayment(value: Float) = withContext(Dispatchers.IO) {
        Log.d("[MYLOG]", "addPayment value: $value")
        currentPayment += value
        Log.d("[MYLOG]", "addPayment currentPayment: $currentPayment")
        val cost = getTimeToPaymentTariff().cost
        Log.d("[MYLOG]", "getTimeToPaymentTariff().cost: $cost")
        Log.d("[MYLOG]", "currentPayment >= cost: ${currentPayment >= cost}")
        if (currentPayment >= cost) {
            val reservationStatusEntity = PlaygroundReservationStatusEntity()
            cloudDatabase
                .collection("reservation_status")
                .document("playground_1")
                .get().await().data?.let { it ->
                    Log.d("[MYLOG]", "cloudDatabase.reservation_status.playground_1: $it")
                    reservationStatusEntity.curr_id = it["curr_id"] as String
                    reservationStatusEntity.paid_ids = it["paid_ids"] as MutableList<String>
                    Log.d("[MYLOG]", "get reservationStatusEntity: $reservationStatusEntity")
                }
            reservationStatusEntity.paid_ids.add(getId())
            cloudDatabase
                .collection("reservation_status")
                .document("playground_1")
                .set(reservationStatusEntity).await()
            Log.d("[MYLOG]", "set reservationStatusEntity: $reservationStatusEntity")
            currentPayment = 0F
        }
    }

    override suspend fun getTimeToPaymentTariff(): TimeToPaymentTariff {
        Log.d("[MYLOG]", "getTimeToPaymentTariff() timeToPaymentTariff: $timeToPaymentTariff")
        if (timeToPaymentTariff == null) {
            Log.d("[MYLOG]", "timeToPaymentTariff == null")
            cloudDatabase
                .collection("payment_time_limits")
                .document("time_to_payment")
                .get().await().data!!.let {
                    timeToPaymentTariff = TimeToPaymentTariff(
                        it.keys.first().toFloat(),
                        (it[it.keys.first()] as Long).toFloat()
                    )
                    Log.d("[MYLOG]", "timeToPaymentTariff: $timeToPaymentTariff")
                }
        }
        return timeToPaymentTariff!!
    }

    override suspend fun getId(): String {
        if (guid == null) {
            guid = UUID.randomUUID().toString()
        }
        return guid!!
    }
}