package com.murat.veripark.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.murat.veripark.R
import com.murat.veripark.core.BaseActivity
import com.murat.veripark.data.PeriodTypeEnum
import com.murat.veripark.databinding.ActivityMainBinding
import com.murat.veripark.ext.observe
import com.murat.veripark.ext.observeEvent
import com.murat.veripark.network.responses.StockRequest
import com.murat.veripark.network.responses.Stocks
import com.murat.veripark.ui.main.stockDetail.StockDetailActivity
import com.murat.veripark.utils.AESUtil


class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var aesUtil: AESUtil
    lateinit var mainAdapter: MainAdapter
    override fun viewModelClass() = MainViewModel::class

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        observeEvent(viewModel.event, ::onViewEvent)
        observe(viewModel.stocks, ::onDataChange)
        setupView()
        aesUtil = AESUtil(
            viewModel.preferenceManager.handShake?.aesKey ?: "",
            viewModel.preferenceManager?.handShake?.aesIV ?: ""
        )
        viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_DECREASING))

    }

    private fun setupView() {
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            viewBinding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        setSupportActionBar(viewBinding.toolbar)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        viewBinding.toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START,true)
        }
    }

    private fun onViewEvent(event: MainViewEvent) {
        when (event) {
            is MainViewEvent.NavigateToDetail -> {
                startActivity(event.item.id?.let { StockDetailActivity.newIntent(this, it) })
            }
        }
    }

    private fun onDataChange(stocks: Stocks) {
        mainAdapter = MainAdapter(decryptSymbols(stocks), viewModel)
        viewBinding.recyclerView.apply {
            this.setHasFixedSize(true)
            this.adapter = mainAdapter
        }
    }

    private fun decryptSymbols(list: Stocks): ArrayList<Stocks.StocksResponse> {
        val listStock = ArrayList<Stocks.StocksResponse>()
        for (item in list.stock) {
            try {
                val decryptedSymbol: String? = aesUtil.aesDecrypt(item.symbol)
                if (decryptedSymbol != null) {

                    listStock.add(item.copy(symbol = decryptedSymbol))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return listStock
    }

    private fun getStockRequest(periodType: PeriodTypeEnum): StockRequest? {
        var aesEncrypted: String? = null
        try {
            aesEncrypted = aesUtil?.aesEncrypt(periodType.period)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return aesEncrypted?.let { StockRequest(it) }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.stochIndex -> {
                viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_ALL))
                drawerLayout.closeDrawer(GravityCompat.START)

                return true
            }
            R.id.imkbDown -> {
                viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_DECREASING))
                drawerLayout.closeDrawer(GravityCompat.START)
                return true

            }
            R.id.imkbUp -> {
                viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_INCREASING))
                drawerLayout.closeDrawer(GravityCompat.START)
                return true

            }
            R.id.imkbOne -> {
                viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_VOLUME30))
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.imkbTwo -> {
                viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_VOLUME50))
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.imkbThree -> {
                viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_VOLUME100))
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            else -> {
                viewModel.getCurrentStocksList(getStockRequest(PeriodTypeEnum.PERIOD_ALL))
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
        }
    }

    private var searchView: SearchView? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.menu_search)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainAdapter.filter.filter(newText)

                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(
            context,
            MainActivity::class.java
        )
    }

}
