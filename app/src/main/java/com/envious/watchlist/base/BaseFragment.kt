package com.envious.watchlist.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<Intent, State, VM : BaseViewModel<Intent, State>> :
    Fragment() {

    abstract val viewModel: VM

    abstract val layoutResourceId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            invalidate(it)
        }
    }

    abstract fun invalidate(state: State)

    protected fun dispatch(intent: Intent) {
        viewModel.onIntentReceived(intent)
    }
}
