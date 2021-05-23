package com.arbaelbarca.github_user_project.presentation.ui.fragment.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.arbaelbarca.github_user_project.R
import com.arbaelbarca.github_user_project.adapter.AdapterSearchUser
import com.arbaelbarca.github_user_project.basebinding.BaseFragmentBinding
import com.arbaelbarca.github_user_project.databinding.FragmentHomeBinding
import com.arbaelbarca.github_user_project.domain.response.ItemsItem
import com.arbaelbarca.github_user_project.presentation.onclick.OnClickItem
import com.arbaelbarca.github_user_project.presentation.viewmodel.ViewModelUser
import com.arbaelbarca.github_user_project.utils.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModelUser: ViewModelUser by viewModel()

    var page = 1
    val perPage = 10
    var adapterSearchUser: AdapterSearchUser? = null
    var layoutLinearManager: LinearLayoutManager? = null
    var mutableListDataSearch: ArrayList<ItemsItem?> = ArrayList()
    var textSearch: String = ""
    private var navController: NavController? = null

    val onClickItem = object : OnClickItem {
        override fun clickItem(any: Any, pos: Int) {
            val dataUser = any as ItemsItem
            val bundle = Bundle()
            bundle.putString("username", dataUser.login)
            navController?.navigate(R.id.action_homeFragment_to_detailUserFragment, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupView(binding: FragmentHomeBinding) {
        navController = Navigation.findNavController(binding.root)
        initial(binding)
    }

    private fun initial(binding: FragmentHomeBinding) {
        initObserver(binding)
        initOnclick(binding)
        viewModelUser.callSearchUser(binding.edSearchUser.text.toString(), perPage, page)
        hideKeyboard(binding.edSearchUser, requireContext())
    }

    private fun initOnclick(binding: FragmentHomeBinding) {
        binding.edSearchUser.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                textSearch = binding.edSearchUser.text.toString()
                page = 1
                viewModelUser.callSearchUser(
                    binding.edSearchUser.text.toString(),
                    perPage,
                    page
                )
                hideKeyboard(binding.edSearchUser, requireContext())
            }
            true
        }
    }

    private fun initObserver(binding: FragmentHomeBinding) {
        observerSearchUser(binding)
        observerNextUser(binding)
    }

    private fun observerNextUser(binding: FragmentHomeBinding) {
        viewModelUser.observerNextUser()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                        hideView(llEmpty)
                        adapterSearchUser?.showLoading()
                    }
                    is UiState.Success -> {
                        val dataItem = it.data.items
                        if (dataItem?.isNotEmpty()!!) {
                            dataItem.forEach {
                                mutableListDataSearch.addAll(listOf(it))
                            }
                            adapterSearchUser?.apply {
                                notifyDataSetChanged()
                                hideLoading()
                            }
                        } else {
                            showView(binding.llEmpty)
                            hideView(rvUsers)
                        }

                    }
                    is UiState.Failure -> {
                        adapterSearchUser?.hideLoading()
                    }
                }
            })
    }

    private fun observerSearchUser(binding: FragmentHomeBinding) {
        mutableListDataSearch.clear()
        viewModelUser.observerUser()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                        hideView(rvUsers)
                        hideView(llEmpty)
                        showView(binding.progressSearch)
                    }
                    is UiState.Success -> {
                        hideView(binding.progressSearch)
                        showView(rvUsers)
                        val dataItem = it.data.items
                        if (dataItem?.isNotEmpty()!!)
                            initData(binding, dataItem)
                        else {
                            showView(binding.llEmpty)
                            hideView(rvUsers)
                        }
                    }
                    is UiState.Failure -> {
                        showView(binding.llEmpty)
                        hideView(binding.progressSearch)
                    }
                }
            })
    }

    private fun initData(binding: FragmentHomeBinding, dataItem: MutableList<ItemsItem?>) {
        mutableListDataSearch = dataItem as ArrayList<ItemsItem?>
        initAdapter(binding)
    }

    private fun initAdapter(binding: FragmentHomeBinding) {
        if (mutableListDataSearch.isNotEmpty()) {
            adapterSearchUser =
                AdapterSearchUser(
                    requireContext(),
                    mutableListDataSearch,
                    onClickItem
                )
            layoutLinearManager = LinearLayoutManager(requireContext())
            binding.rvUsers.apply {
                adapter = adapterSearchUser
                layoutManager = layoutLinearManager
                hasFixedSize()
                addOnScrollListener(OnLoadMoreScrollListener(layoutLinearManager!!) {
                    if (page != perPage) {
                        page++
                        viewModelUser.callNextSearchUser(textSearch, perPage, page)
                    }
                })
            }
        } else {
            println("Tidak ada data")
        }
    }

}