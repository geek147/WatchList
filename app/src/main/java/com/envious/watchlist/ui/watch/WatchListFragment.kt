package com.envious.watchlist.ui.watch

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.envious.watchlist.R
import com.envious.watchlist.base.BaseFragment
import com.envious.watchlist.databinding.FragmentWatchListBinding
import com.envious.watchlist.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_watch_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WatchListFragment : BaseFragment<WatchListContract.Intent, WatchListContract.State, WatchListViewModel>() {

    private lateinit var binding: FragmentWatchListBinding
    private lateinit var adapter: WatchListAdapter

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var currentPage = 0

    override val viewModel: WatchListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchListBinding.inflate(layoutInflater)
        setupRecyclerView()
        dispatch(
            WatchListContract.Intent.GetFirstData
        )
        return binding.root
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerview.layoutManager = linearLayoutManager
            recyclerview.itemAnimator = null
            adapter = WatchListAdapter(requireContext())
            adapter.setHasStableIds(true)
            recyclerview.adapter = adapter
            scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ) {
                    currentPage = page + 1
                    dispatch(
                        WatchListContract.Intent.LoadNext(currentPage)
                    )
                }
            }
            recyclerview.addOnScrollListener(scrollListener)
            swipeContainer.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(requireContext(), R.color.teal_200)
            )
            swipeContainer.setColorSchemeColors(Color.WHITE)

            swipeContainer.setOnRefreshListener {
                currentPage += 1
                dispatch(
                    WatchListContract.Intent.LoadNext(currentPage)
                )
            }
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_watch_list

    override fun invalidate(state: WatchListContract.State) {
        when (state.viewState) {
            WatchListContract.ViewState.EmptyList -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        showError(
                            title = resources.getString(R.string.empty_state_title),
                            message = resources.getString(R.string.empty_state_message)
                        )
                    }
                    pgProgressList.visibility = View.GONE
                    adapter.setList(emptyList())
                    recyclerview.visibility = View.GONE
                    swipeContainer.isRefreshing = false
                }
            }
            WatchListContract.ViewState.ErrorFirstInit -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        showError()
                    }
                    pgProgressList.visibility = View.GONE
                    adapter.setList(emptyList())
                    recyclerview.visibility = View.GONE
                    swipeContainer.isRefreshing = false
                }
            }
            WatchListContract.ViewState.ErrorLoadMore -> {
                with(binding) {
                    errorView.visibility = View.GONE
                    pgProgressList.visibility = View.GONE
                    recyclerview.visibility = View.VISIBLE
                    swipeContainer.isRefreshing = false
                }
                Toast.makeText(
                    requireContext(),
                    "Gagal menambahkan data baru",
                    Toast.LENGTH_SHORT
                ).show()
            }
            WatchListContract.ViewState.Idle -> TODO()
            WatchListContract.ViewState.Loading -> {
                with(binding) {
                    errorView.visibility = View.GONE
                    pgProgressList.visibility = View.VISIBLE
                    recyclerview.visibility = View.VISIBLE
                }
            }
            WatchListContract.ViewState.SuccessFirstInit -> {
                with(binding) {
                    errorView.visibility = View.GONE
                    pgProgressList.visibility = View.GONE
                    recyclerview.visibility = View.VISIBLE
                    adapter.setList(state.listItem)
                    swipeContainer.isRefreshing = false
                }
            }
            WatchListContract.ViewState.SuccessLoadMore -> {
                with(binding) {
                    errorView.visibility = View.GONE
                    pgProgressList.visibility = View.GONE
                    recyclerview.visibility = View.VISIBLE
                    adapter.addData(state.listItem)
                    swipeContainer.isRefreshing = false
                }
            }
        }
    }
}
