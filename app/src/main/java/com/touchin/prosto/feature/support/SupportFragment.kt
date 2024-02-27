package com.touchin.prosto.feature.support

import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.touchin.prosto.R
import com.touchin.prosto.base.fragment.BaseContentFragment
import com.touchin.prosto.databinding.FragmentSupportBinding
import com.touchin.prosto.util.SimpleTextWatcher

class SupportFragment :
    BaseContentFragment<SupportState, SupportViewModel, SupportController>(R.layout.fragment_support) {

    private val binding: FragmentSupportBinding by viewBinding { FragmentSupportBinding.bind(requireView()) }

    override fun createViewModelLazy() = viewModels<SupportViewModel> { viewModelFactory }

    override fun initView(controller: SupportController) {
        binding.toolbar.setBackClickListener(controller::onBackClicked)
        binding.sendButton.scaleAnimationOnClick { controller.onSendClicked() }
        binding.emailInput.afterTextChanged(controller::onEmailChanged)
        binding.subjectInput.afterTextChanged(controller::onSubjectChanged)
        binding.bodyInput.afterTextChanged(controller::onBodyChanged)
    }

    override fun handleEvent(event: SingleEvent) = when(event){
        is SupportEvents.DraftEvent -> {
            binding.emailInput.setText(event.emailText)
            binding.subjectInput.setText(event.subjectText)
            binding.bodyInput.setText(event.bodyText)
        }
        else -> super.handleEvent(event)
    }

    override fun render(state: SupportState, controller: SupportController) {
        binding.sendButton.isEnabled = state.sendButtonEnable
        binding.emailInput.showError(state.hasEmailError, R.string.common_email_error)
        binding.subjectInput.showError(state.hasSubjectError, R.string.common_invalid_text_error)
        binding.bodyInput.showError(state.hasBodyError, R.string.common_invalid_text_error)
    }

    private fun EditText.afterTextChanged(action: (String) -> Unit) {
        addTextChangedListener(SimpleTextWatcher(afterTextChanged = action))
    }

    private fun EditText.showError(hasError: Boolean, @StringRes textRes: Int) {
        error = if (hasError) getString(textRes) else null
    }
}
