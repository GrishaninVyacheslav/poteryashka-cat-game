package io.github.grishaninvyacheslav.human_playground.presentation.donation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.grishaninvyacheslav.human_playground.data.repositories.DonationRepository
import io.github.grishaninvyacheslav.navigation.domain.use_cases.ExitUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class DonationViewModel(
    private val donationRepository: DonationRepository,
    private val exitUseCase: ExitUseCase
) : ViewModel() {
    fun receiveDonation(paymentValue: Float) {
        viewModelScope.launch(exceptionHandler) {
            donationRepository.addPayment(paymentValue)
            exitUseCase()
        }
        Log.d("[MYLOG]", "receiveDonation paymentValue: $paymentValue")
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("[MYLOG]", "exceptionHandler: ${throwable.message}")
    }
}