package com.murat.veripark.ui.main


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.murat.veripark.core.BaseListAdapter
import com.murat.veripark.core.BaseViewHolder
import com.murat.veripark.databinding.ViewHolderStocksItemBinding
import com.murat.veripark.network.responses.Stocks


class MainAdapter(
    private var stockList: ArrayList<Stocks.StocksResponse>,
    val viewModel: MainViewModel
) : BaseListAdapter<Stocks.StocksResponse>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
), Filterable {
    var stocksList = stockList
    var filteredList = stockList
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return CoinViewHolder(parent, inflater)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoinViewHolder -> {
                holder.bind(viewModel, filteredList?.get(position)!!)
            }
        }
    }


    override fun getItemCount(): Int {
        return filteredList?.size!!
    }

    class CoinViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ) : BaseViewHolder<ViewHolderStocksItemBinding>(
        binding = ViewHolderStocksItemBinding.inflate(inflater, parent, false)
    ) {
        @SuppressLint("SimpleDateFormat", "CheckResult")
        fun bind(
            viewModel: MainViewModel,
            stockResponse: Stocks.StocksResponse
        ) {
            binding.viewModel = viewModel
            binding.item = stockResponse
            binding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val searchString = charSequence.toString()
                if (searchString.isEmpty()) {
                    filteredList = stocksList
                } else {
                    val tempFilteredList: ArrayList<Stocks.StocksResponse> = ArrayList()
                    for (stocks in stocksList!!) {
                        // search for user name
                        if (stocks.symbol?.toUpperCase()?.contains(searchString.toUpperCase())!!) {
                            tempFilteredList.add(stocks)
                        }
                    }
                    filteredList = tempFilteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredList = filterResults.values as ArrayList<Stocks.StocksResponse>
                notifyDataSetChanged()
            }
        }
    }
}

