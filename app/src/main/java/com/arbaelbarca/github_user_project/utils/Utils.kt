package com.arbaelbarca.github_user_project.utils


import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.github_user_project.R
import com.bumptech.glide.Glide
import com.orhanobut.hawk.Hawk
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*

fun ImageView.loadImageUrl(url: String, context: Context) {
    Glide.with(context)
        .load(url)
//        .error(R.drawable.no_image)
//        .placeholder(R.drawable.no_image)
        .into(this)
}

fun showView(view: View) {
    view.visibility = View.VISIBLE
}


fun Toolbar.setToolbar(
    toolbar: Toolbar, message: String?, context: Context,
    appCompatActivity: AppCompatActivity
) {
    appCompatActivity.setSupportActionBar(toolbar)
    appCompatActivity.supportActionBar?.title = message
    appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.white))
    toolbar.setNavigationOnClickListener {
        appCompatActivity.finish()
    }
}


fun hideView(view: View) {
    view.visibility = View.GONE
}

fun hideKeyboard(view: View, context: Context) {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.hideKeyboardNew(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

//fun initHeader(): Map<String, String> {
//    val getToken = Hawk.get<String>(Constant.ACCESS_TOKEN)
//    val map = HashMap<String, String>()
//    map["Authorization"] = "Bearer $getToken"
//    map["Content-Type"] = "application/json"
//    return map
//}
//
//fun getAccessToken(): String? {
//    return Hawk.get<String>(Constant.ACCESS_TOKEN)
//}


fun setRvAdapterVertikal(
    recyclerView: RecyclerView,
    adapterGroupieViewHolder: GroupAdapter<GroupieViewHolder>
) {
    recyclerView.apply {
        adapter = adapterGroupieViewHolder
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
    }
}


fun Toast.showToast(message: String?, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun setRvAdapterHorizontal(
    recyclerView: RecyclerView,
    adapterGroupieViewHolder: GroupAdapter<GroupieViewHolder>
) {
    recyclerView.apply {
        adapter = adapterGroupieViewHolder
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hasFixedSize()
    }
}


