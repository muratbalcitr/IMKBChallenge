package com.murat.veripark.ui.main.stockDetail

import android.content.Context
import android.content.Intent
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.murat.veripark.R
import com.murat.veripark.core.BaseActivity
import com.murat.veripark.databinding.ActivityStockDetailBinding
import com.murat.veripark.ext.observe
import com.murat.veripark.network.responses.StockDetailResponse
import com.murat.veripark.network.responses.StockDetailResponse.GraphicData
import com.murat.veripark.utils.AESUtil


class StockDetailActivity : BaseActivity<ActivityStockDetailBinding, StockDetailViewModel>(
    layoutId = R.layout.activity_stock_detail
) {
    lateinit var aesUtil: AESUtil
    private var entry: ArrayList<Entry>? = null

    override fun viewModelClass() = StockDetailViewModel::class
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        observe(viewModel.stockDetails, ::onDataChange)
        setup()
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun setup() {
        setLineChartParams()
        aesUtil = AESUtil(
            viewModel.preferenceManager.handShake?.aesKey ?: "",
            viewModel.preferenceManager?.handShake?.aesIV ?: ""
        )
        getEncryptId(intent.getIntExtra(STOCK_ID, 1))?.let { viewModel.stockDetail(it) }

    }

    private fun onDataChange(stockList: StockDetailResponse) {
        viewBinding.toolbar.setTitle("DETAY: "+ stockList.symbol?.let { decryptSymbols(it) })

        stockList.graphicData?.let { setGraphicData(it) }
        viewBinding.symbolTextView.text = String.format(
            getString(R.string.symbol_detail),
            stockList.symbol?.let { decryptSymbols(it) })

    }

    private fun getEncryptId(id: Int): String? {
        var aesEncrypted: String? = null
        try {
            aesEncrypted = aesUtil.aesEncrypt(id.toString())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return aesEncrypted
    }

    lateinit var decryptedSymbol: String
    private fun decryptSymbols(symbol: String): String {
        try {
            decryptedSymbol = aesUtil.aesDecrypt(symbol)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return decryptedSymbol
    }

    private fun setLineChartParams() {
        entry = ArrayList<Entry>()
        viewBinding.lineChart.apply {
            setDrawGridBackground(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            xAxis.textSize = 15f
            axisLeft.textSize = 15f
            val xl: XAxis = xAxis
            xl.setAvoidFirstLastClipping(true)
            val leftAxis: YAxis = axisLeft
            leftAxis.isInverted = true
            val rightAxis: YAxis = axisRight
            rightAxis.isEnabled = false
            val l: Legend = legend
            l.form = Legend.LegendForm.LINE
        }
    }

    private fun setGraphicData(graphicDataList: List<GraphicData>) {
        for ((day, value) in graphicDataList) {
            entry?.add(Entry(day.toFloat(), value.toFloat()))
        }
        val set1 = LineDataSet(entry, "")
        set1.setColors(*ColorTemplate.COLORFUL_COLORS)
        set1.lineWidth = 1.5f
        set1.circleRadius = 4f
        val data = LineData(set1)
        viewBinding.lineChart.data = data
        viewBinding.lineChart.invalidate()
    }

    companion object {
        const val STOCK_ID = "stock_id"
        fun newIntent(context: Context, id: Int): Intent = Intent(
            context,
            StockDetailActivity::class.java
        ).apply {
            putExtra(STOCK_ID, id)
        }
    }
}