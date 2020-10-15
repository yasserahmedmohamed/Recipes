package com.yasser.recipes.ui.recipe_list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yasser.recipes.Adapters.RecipeRecycleAdapter
import com.yasser.recipes.R
import com.yasser.recipes.interfaces.RecipeItemListener
import com.yasser.recipes.interfaces.RecipeViewModelListener
import com.yasser.recipes.model.Recipe
import com.yasser.recipes.ui.recipe_details.RecipeDetailsActivity
import com.yasser.recipes.utils.Coroutines
import com.yasser.recipes.utils.HideWithAnimation
import com.yasser.recipes.utils.NetworkUtil
import com.yasser.recipes.utils.showWithAnimation
import com.yasser.recipes.viewmodelfactory.RecipesViewModelFactory
import kotlinx.android.synthetic.main.activity_recipes.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class RecipesActivity : AppCompatActivity(), KodeinAware, RecipeItemListener,
    SearchView.OnQueryTextListener, RecipeViewModelListener, PopupMenu.OnMenuItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    override val kodein by kodein()
    private val factory: RecipesViewModelFactory by instance()
    lateinit var viewModel: RecipeViewModel
    lateinit var recipeAdapter: RecipeRecycleAdapter


    private val networkStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            Coroutines.main {
                p0?.let {
                    val status: Int = NetworkUtil.getConnectivityStatusString(it)
                    if (CONNECTIVITY_ACTION == p1!!.action) {
                        if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                            InternetConnection(false)
                        } else {
                            InternetConnection(true)
                            viewModel.checkIsDataAvailable()
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        viewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel::class.java)
        viewModel.listener = this
        swipeRefresh.setOnRefreshListener(this)
        recipeAdapter = RecipeRecycleAdapter(this, ArrayList())
        InitRecycleView()

        viewModel.GetRecipesData()


        viewModel.DisplayRecipes.observe(this, Observer {

            if (it.size>0){
                var items = ArrayList<Recipe>()
                items.addAll(it)
                recipeAdapter.UpdateList(items)
                no_data_layout.HideWithAnimation()
            }
            else{
                no_data_layout.showWithAnimation()
                recipeAdapter.clearData()
            }

        })

        SearchText.setOnQueryTextListener(this)

    }

    fun ShowFilterPop(v: View) {

        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.recipe_filter_menu)
        popup.show()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkStateReceiver, IntentFilter(CONNECTIVITY_ACTION))

    }

    override fun onPause() {
        unregisterReceiver(networkStateReceiver)
        super.onPause()
    }

    fun InitRecycleView() {
        recipeAdapter.listener = this
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        recyclerView.adapter = recipeAdapter
    }

    override fun OnItemTapped(data: Recipe) {
       val intent = Intent(this,RecipeDetailsActivity::class.java)
        intent.putExtra(RecipeDetailsActivity.RecipeIDINTENT,data.id)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        query?.let {
            viewModel.SearchWithText(it)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        newText?.let {
            viewModel.SearchWithText(it)
        }
        return false
    }

    override fun InternetConnection(IsConnected: Boolean) {

        runOnUiThread(object :Runnable{
            override fun run() {
                if (IsConnected) {

                    NoConnectionLayout.HideWithAnimation()
                } else {
                    NoConnectionLayout.showWithAnimation()
                }
            }

        })

    }

    override fun OnFilterSelected(filterItem: Int) {

        FilterData(filterItem)

    }

    override fun OnLoadingData() {
        swipeRefresh.isRefreshing = true
    }

    override fun OnLoadFinish() {
        swipeRefresh.isRefreshing = false
    }


    fun FilterData(filterItem: Int): Boolean {
        when (filterItem) {
            R.id.cal_filter -> {
                filter_layout.showWithAnimation()
                filterText.setText("${getString(R.string.filterBy)} ${getString(R.string.calories)}")
                viewModel.FilterByCaloris()
                viewModel.SaveFilterItem(R.id.cal_filter)
                return true
            }
            R.id.fat_filter -> {
                filter_layout.showWithAnimation()
                filterText.setText("${getString(R.string.filterBy)} ${getString(R.string.fat)}")
                viewModel.SaveFilterItem(R.id.fat_filter)
                viewModel.FilterByFats()
                return true
            }

            R.id.no_filter -> {
                filter_layout.HideWithAnimation()
                viewModel.SaveFilterItem(R.id.no_filter)
                viewModel.GetRecipesFrmDB()
                return true
            }

            else -> {

                return false
            }
        }
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        p0?.let {
            return FilterData(it.itemId)
        }

        return false
    }

    override fun onRefresh() {
        viewModel.GetRecipesData()
    }


}
