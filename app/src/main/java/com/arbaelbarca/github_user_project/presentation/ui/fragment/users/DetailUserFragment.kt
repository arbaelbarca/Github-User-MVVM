package com.arbaelbarca.github_user_project.presentation.ui.fragment.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.arbaelbarca.github_user_project.R
import com.arbaelbarca.github_user_project.adapter.AdapterRepoList
import com.arbaelbarca.github_user_project.basebinding.BaseFragmentBinding
import com.arbaelbarca.github_user_project.databinding.FragmentDetailUserBinding
import com.arbaelbarca.github_user_project.db.entity.EntityUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseDetailUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseRepoList
import com.arbaelbarca.github_user_project.presentation.viewmodel.ViewModelUser
import com.arbaelbarca.github_user_project.utils.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_detail_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailUserFragment : BaseFragmentBinding<FragmentDetailUserBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModelUser: ViewModelUser by viewModel()
    var bundle: Bundle? = null
    var dataItemUsers: ResponseDetailUsers? = null
    var dataEntityUsers: EntityUsers? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = this.arguments
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailUserFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailUserBinding
        get() = FragmentDetailUserBinding::inflate

    override fun setupView(binding: FragmentDetailUserBinding) {
        initial(binding)
    }


    private fun initial(binding: FragmentDetailUserBinding) {
        val getUsername = bundle?.getString("username")
        initObserver(binding)
        initGetListRepo(getUsername)
        initClick(binding)
        viewModelUser.callDetailUser(getUsername.toString())
    }

    private fun initObserver(binding: FragmentDetailUserBinding) {
        observerDataDetail(binding)
        observerRepoList(binding)
        observerAddFav(binding)
        observerDeleteFav(binding)
        observerCheckFav(binding)
        observerIsCheckFav(binding)
    }

    private fun observerIsCheckFav(binding: FragmentDetailUserBinding) {
        viewModelUser.observerCheckFavIscheck()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                    }
                    is UiState.Success -> {
                        val listEntityUsers = it.data
                        println("respon Id fav $listEntityUsers")
                        if (listEntityUsers.isNotEmpty()) {
                            listEntityUsers.forEach {
                                val idUser = it.id
                                if (idUser == dataItemUsers?.id)
                                    binding.floatFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                                else binding.floatFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            }
                        }
                    }
                    is UiState.Failure -> {
                    }
                }
            })
    }

    private fun observerCheckFav(binding: FragmentDetailUserBinding) {
        viewModelUser.observerCheckFav()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                    }
                    is UiState.Success -> {
                        val listEntityUsers = it.data
                        println("respon Id fav $listEntityUsers")
                        if (listEntityUsers.isNotEmpty()) {
                            listEntityUsers.forEach {
                                val idUser = it.id
                                if (idUser == dataEntityUsers?.id)
                                    dataEntityUsers?.let { it1 ->
                                        viewModelUser.callDeleteFavUsers(
                                            it1
                                        )
                                    }
                                else dataEntityUsers?.let { it1 ->
                                    viewModelUser.callAddFavUsers(
                                        it1
                                    )
                                }

                            }
                        } else {
                            dataEntityUsers?.let { it1 -> viewModelUser.callAddFavUsers(it1) }
                        }
                    }
                    is UiState.Failure -> {
                    }
                }
            })
    }

    private fun initClick(binding: FragmentDetailUserBinding) {
        binding.floatFav.setOnClickListener {
            dataEntityUsers = dataItemUsers?.id?.let { it1 ->
                EntityUsers(
                    it1,
                    dataItemUsers?.login.toString(),
                    dataItemUsers?.name.toString(),
                    dataItemUsers?.avatarUrl.toString()
                )
            }
            viewModelUser.callCheckFavUsers(dataEntityUsers?.titleUser.toString())
        }
    }


    private fun observerDeleteFav(binding: FragmentDetailUserBinding) {
        viewModelUser.observerDeleteFav()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                    }
                    is UiState.Success -> {
                        val dataItem = it.data
                        binding.floatFav.apply {
                            setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        }
                        showToast(dataItem)
                    }
                    is UiState.Failure -> {
                    }
                }
            })
    }

    private fun observerAddFav(binding: FragmentDetailUserBinding) {
        viewModelUser.observerAddFav()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                    }
                    is UiState.Success -> {
                        val dataItem = it.data
                        binding.floatFav.apply {
                            setImageResource(R.drawable.ic_baseline_favorite_24)
                        }
                        showToast(dataItem)
                    }
                    is UiState.Failure -> {
                    }
                }
            })
    }

    private fun observerRepoList(binding: FragmentDetailUserBinding) {
        viewModelUser.observerRepoList()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                        showView(binding.progressDetail)
                    }
                    is UiState.Success -> {
                        hideView(progressDetail)
                        showView(cslDetail)
                        showView(cslFollower)
                        val dataItem = it.data
                        initDataRepo(binding, dataItem)
                    }
                    is UiState.Failure -> {
                    }
                }
            })
    }

    private fun initDataRepo(
        binding: FragmentDetailUserBinding,
        dataItem: ResponseRepoList
    ) {
        val groupieAdapter = GroupAdapter<GroupieViewHolder>().apply {
            dataItem.forEach {
                add(AdapterRepoList(requireContext(), it))
            }
        }
        setRvAdapterVertikal(binding.rvListRepo, groupieAdapter)

    }

    private fun observerDataDetail(binding: FragmentDetailUserBinding) {
        viewModelUser.observerDetailUser()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                        showView(binding.progressDetail)
                    }
                    is UiState.Success -> {
                        hideView(progressDetail)
                        showView(cslDetail)
                        showView(cslFollower)
                        val dataItem = it.data
                        initData(binding, dataItem)
                    }
                    is UiState.Failure -> {
                    }
                }
            })
    }

    private fun initData(
        binding: FragmentDetailUserBinding,
        dataItem: ResponseDetailUsers
    ) {
        dataItemUsers = dataItem
        viewModelUser.callCheckFavUsersIsCheck(dataItemUsers!!.name.toString())
        binding.tvTitleDetailUser.text = dataItem.name
        binding.tvEmailDetailUser.text = dataItem.email
        binding.circleImgDetailUser.loadImageUrl(dataItem.avatarUrl.toString(), requireContext())
        binding.tvDescDetailUser.text = dataItem.bio
        binding.tvOrigin.text = dataItem.location
        binding.tvTotalFollower.text = "${dataItem.followers} Follwers"
        binding.tvTotalFollowing.text = "${dataItem.following} Following"
        binding.tvGmailDetailUser.text = dataItem.email
        showView(binding.floatFav)
    }

    private fun initGetListRepo(username: String?) {
        viewModelUser.callRepoListUsers(username.toString())
    }
}