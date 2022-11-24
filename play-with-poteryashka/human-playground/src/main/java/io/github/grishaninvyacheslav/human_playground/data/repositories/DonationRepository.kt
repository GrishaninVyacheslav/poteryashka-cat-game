package io.github.grishaninvyacheslav.human_playground.data.repositories

import io.github.grishaninvyacheslav.human_playground.data.entities.TimeToPaymentTariff

interface DonationRepository {
    suspend fun getId(): String
    suspend fun addPayment(value: Float)
    suspend fun getTimeToPaymentTariff(): TimeToPaymentTariff
}