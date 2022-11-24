package io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.presentation

import android.os.Bundle
import android.view.View
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.databinding.FragmentRoomsListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomsListFragment :
    BaseFragment<FragmentRoomsListBinding>(FragmentRoomsListBinding::inflate) {

    companion object {
        fun newInstance() = RoomsListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            room1CameraWebView.settings.javaScriptEnabled = true
            room1CameraWebView.loadUrl("https://ipeye.ru/ipeye_service/api/iframe.php?iframe_player=1&dev=ieiLmTufliX8iOvLWBULmwJ0SZKB7P&autoplay=1&archive=1")

            room2CameraWebView.settings.javaScriptEnabled = true
            room2CameraWebView.loadUrl("https://ipeye.ru/ipeye_service/api/iframe.php?iframe_player=1&dev=aKPBsQqGRcKk9vvMlDFHvebn32Tp13&autoplay=1&archive=1")

            room3CameraWebView.settings.javaScriptEnabled = true
            room3CameraWebView.loadUrl("https://ipeye.ru/ipeye_service/api/iframe.php?iframe_player=1&dev=C9Fw1rToT5Z47CkDLv5Gm6AUise6Kp&autoplay=1&archive=1")

            room4CameraWebView.settings.javaScriptEnabled = true
            room4CameraWebView.loadUrl("https://ipeye.ru/ipeye_service/api/iframe.php?iframe_player=1&dev=O5AVrgFbfS7L9s4eBzUffEmXmWhfkT&autoplay=1&archive=1")

            room1Card.setOnClickListener {
                viewModel.openRoom()
            }
        }
    }

    private val viewModel: RoomsListViewModel by viewModel()
}