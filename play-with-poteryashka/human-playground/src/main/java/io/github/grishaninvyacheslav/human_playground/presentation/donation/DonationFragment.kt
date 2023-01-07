package io.github.grishaninvyacheslav.human_playground.presentation.donation

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.human_playground.databinding.FragmentDonationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DonationFragment : BaseFragment<FragmentDonationBinding>(FragmentDonationBinding::inflate) {

    companion object {
        fun newInstance() = DonationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() = with(binding) {
        donationFormWebView.settings.javaScriptEnabled = true
        donationFormWebView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java", ReplaceWith("false"))
            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                return false
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView?, handler: SslErrorHandler, error: SslError?
            ) {
                handler.proceed()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                viewModel.receiveDonation(2F) // TODO: Remove this line. It is for testing purposes

                if (url != null) {
                    if (url.startsWith("http://poteryashka.spb.ru/payments/robokassa/success/")) {
                        viewModel.receiveDonation(url.substringAfter("OutSum=").substringBefore('&').toFloat())
                    }
                }
            }
        }
        donationFormWebView.loadUrl("http://poteryashka.spb.ru/payments/robokassa/pay.html")
    }

    private val viewModel: DonationViewModel by viewModel()
}