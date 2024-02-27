package com.touchin.prosto.feature.start

import androidx.fragment.app.viewModels
import com.anadolstudio.core.util.common_extention.makeGone
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.touchin.prosto.R
import com.touchin.prosto.base.fragment.BaseActionFragment
import com.touchin.prosto.databinding.FragmentStartBinding

class StartFragment : BaseActionFragment<StartViewModel, StartController>(R.layout.fragment_start) {

    private val binding: FragmentStartBinding by viewBinding { FragmentStartBinding.bind(requireView()) }

    override fun createViewModelLazy() = viewModels<StartViewModel> { viewModelFactory }

    override fun initView(controller: StartController) {
        binding.offerButton.scaleAnimationOnClick(action = controller::onOffersClicked)
        binding.supportButton.scaleAnimationOnClick(action = controller::onSupportClicked)
        binding.nightBtn.scaleAnimationOnClick(action = controller::onNightModeClicked)
    }
}
