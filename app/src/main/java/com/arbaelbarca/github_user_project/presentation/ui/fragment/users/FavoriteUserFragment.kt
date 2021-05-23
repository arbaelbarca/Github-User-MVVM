package com.arbaelbarca.github_user_project.presentation.ui.fragment.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arbaelbarca.github_user_project.R
import com.arbaelbarca.github_user_project.adapter.AdapterUsersFavorite
import com.arbaelbarca.github_user_project.basebinding.BaseFragmentBinding
import com.arbaelbarca.github_user_project.databinding.FragmentFavoriteUserBinding
import com.arbaelbarca.github_user_project.db.entity.EntityUsers
import com.arbaelbarca.github_user_project.presentation.onclick.OnClickItem
import com.arbaelbarca.github_user_project.presentation.viewmodel.ViewModelUser
import com.arbaelbarca.github_user_project.utils.UiState
import com.arbaelbarca.github_user_project.utils.hideView
import com.arbaelbarca.github_user_project.utils.setRvAdapterVertikal
import com.arbaelbarca.github_user_project.utils.showView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_favorite_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteUserFragment : BaseFragmentBinding<FragmentFavoriteUserBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModelUser: ViewModelUser by viewModel()
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteUserFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteUserBinding
        get() = FragmentFavoriteUserBinding::inflate

    override fun setupView(binding: FragmentFavoriteUserBinding) {
        initial(binding)
    }

    private fun initial(binding: FragmentFavoriteUserBinding) {
        navController = Navigation.findNavController(binding.root)
        viewModelUser.callGetAllFav()
        initObserver(binding)
    }

    private fun initObserver(binding: FragmentFavoriteUserBinding) {
        viewModelUser.observerAllFav()
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is UiState.Loading -> {
                        showView(binding.progressSearchFav)
                    }
                    is UiState.Success -> {
                        hideView(binding.progressSearchFav)
                        val dataItem = it.data
                        if (dataItem.isNotEmpty()) {
                            initData(dataItem, binding)
                        } else {
                            hideView(rvUsersFav)
                        }

                    }
                    is UiState.Failure -> {
                        hideView(binding.progressSearchFav)
                    }
                }
            })
    }

    private fun initData(dataItem: List<EntityUsers>, binding: FragmentFavoriteUserBinding) {
        val groupieAdapter = GroupAdapter<GroupieViewHolder>().apply {
            dataItem.forEach {
                add(AdapterUsersFavorite(requireContext(), it, object : OnClickItem {
                    override fun clickItem(any: Any, pos: Int) {
                        val dataUser = any as EntityUsers
                        val bundle = Bundle()
                        bundle.putString("username", dataUser.username)
                        navController?.navigate(
                            R.id.action_favoriteUserFragment_to_detailUserFragment,
                            bundle
                        )
                    }
                }))
            }
        }

        setRvAdapterVertikal(binding.rvUsersFav, groupieAdapter)
    }
}