package com.arbaelbarca.github_user_project.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.github_user_project.db.entity.EntityUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseDetailUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseRepoList
import com.arbaelbarca.github_user_project.domain.response.ResponseSearchUsers
import com.arbaelbarca.github_user_project.repository.IRepositoryUser
import com.arbaelbarca.github_user_project.utils.UiState
import kotlinx.coroutines.launch

class ViewModelUser(val iRepositoryUser: IRepositoryUser) : ViewModel() {
    val stateSearchUser = MutableLiveData<UiState<ResponseSearchUsers>>()
    val stateNextSearchUser = MutableLiveData<UiState<ResponseSearchUsers>>()
    val stateDetailUsers = MutableLiveData<UiState<ResponseDetailUsers>>()
    val stateRepoList = MutableLiveData<UiState<ResponseRepoList>>()
    val stateAddFavUsers = MutableLiveData<UiState<String>>()
    val stateCheckFav = MutableLiveData<UiState<List<EntityUsers>>>()
    val stateCheckFavIscheck = MutableLiveData<UiState<List<EntityUsers>>>()
    val stateDeleteFav = MutableLiveData<UiState<String>>()
    val stateAllFav = MutableLiveData<UiState<List<EntityUsers>>>()

    fun observerUser() = stateSearchUser
    fun observerNextUser() = stateNextSearchUser
    fun observerDetailUser() = stateDetailUsers
    fun observerRepoList() = stateRepoList
    fun observerAddFav() = stateAddFavUsers
    fun observerDeleteFav() = stateDeleteFav
    fun observerCheckFav() = stateCheckFav
    fun observerCheckFavIscheck() = stateCheckFavIscheck
    fun observerAllFav() = stateAllFav

    fun callGetAllFav() {
        stateAllFav.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.getAllFav()
            }.onSuccess {
                stateAllFav.value = UiState.Success(it)
            }.onFailure {
                stateAllFav.value = UiState.Failure(it)
            }
        }
    }

    fun callDeleteFavUsers(entityUsers: EntityUsers) {
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.deleteFav(entityUsers)
            }.onSuccess {
                stateDeleteFav.value = UiState.Success("Berhasil menghapus favorite")
            }.onFailure {
                stateDeleteFav.value = UiState.Failure(it)
            }
        }
    }

    fun callAddFavUsers(entityUsers: EntityUsers) {
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.addFavUser(entityUsers)
            }.onSuccess {
                stateAddFavUsers.value = UiState.Success("Berhasil menambahkan favorite")
            }.onFailure {
                stateAddFavUsers.value = UiState.Failure(it)
            }
        }
    }

    fun callCheckFavUsers(title: String) {
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.checkFavUser(title)
            }.onSuccess {
                stateCheckFav.value = UiState.Success(it)
            }.onFailure {
                stateCheckFav.value = UiState.Failure(it)
            }
        }
    }

    fun callCheckFavUsersIsCheck(title: String) {
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.checkFavUser(title)
            }.onSuccess {
                stateCheckFavIscheck.value = UiState.Success(it)
            }.onFailure {
                stateCheckFavIscheck.value = UiState.Failure(it)
            }
        }
    }

    fun callRepoListUsers(username: String) {
        stateRepoList.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.callApiRepoList(username)
            }.onSuccess {
                stateRepoList.value = UiState.Success(it)
            }.onFailure {
                stateRepoList.value = UiState.Failure(it)
            }
        }
    }


    fun callDetailUser(username: String) {
        stateDetailUsers.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.callApiDetailUser(username)
            }.onSuccess {
                stateDetailUsers.value = UiState.Success(it)
            }.onFailure {
                stateDetailUsers.value = UiState.Failure(it)
            }
        }
    }

    fun callSearchUser(
        textSearch: String,
        perPage: Int, page: Int
    ) {
        stateSearchUser.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.callApiSearchUser(
                    textSearch,
                    perPage, page
                )
            }.onSuccess {
                stateSearchUser.value = UiState.Success(it)
            }.onFailure {
                stateSearchUser.value = UiState.Failure(it)
            }
        }
    }


    fun callNextSearchUser(
        textSearch: String,
        perPage: Int, page: Int
    ) {
        stateNextSearchUser.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                iRepositoryUser.callApiSearchUser(
                    textSearch,
                    perPage, page
                )
            }.onSuccess {
                stateNextSearchUser.value = UiState.Success(it)
            }.onFailure {
                stateNextSearchUser.value = UiState.Failure(it)
            }
        }
    }
}