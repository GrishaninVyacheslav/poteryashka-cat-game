package io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.cat_side

import android.os.Bundle
import android.view.View
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ViewCompositionStrategy
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.game_catch_a_mouse.databinding.FragmentCatchAMouseBinding
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.Playground
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatchAMouseCatSideFragment :
    BaseFragment<FragmentCatchAMouseBinding>(FragmentCatchAMouseBinding::inflate) {

    companion object {
        fun newInstance() = CatchAMouseCatSideFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val playgroundLocationOnScreen = IntArray(2)
                binding.mapComposeView.getLocationOnScreen(playgroundLocationOnScreen)
                Playground(
                    viewModel,
                    Offset(
                        playgroundLocationOnScreen[0].toFloat(),
                        playgroundLocationOnScreen[1].toFloat()
                    )
                )
            }
        }
    }

    private val viewModel: CatchAMouseCatSideViewModelImpl by viewModel()
}