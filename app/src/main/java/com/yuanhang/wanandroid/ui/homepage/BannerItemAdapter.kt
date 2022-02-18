package com.yuanhang.wanandroid.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.model.BannerItem
import com.yuanhang.wanandroid.ui.common.ImageLoader
import com.yuanhang.wanandroid.util.onClick

/**
 * created by yuanhang on 2022/2/18
 * description:
 */
class BannerItemAdapter(
    private val imageLoader: ImageLoader,
    private val bannerItemClick: ((BannerItem) -> Unit)? = null
) : RecyclerView.Adapter<BannerItemAdapter.BannerItemViewHolder>() {

    private val bannerItemList = ArrayList<BannerItem>()

    inner class BannerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerImage: ImageView = itemView.findViewById(R.id.ivBanner)
        val bannerTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val bannerDesc: TextView = itemView.findViewById(R.id.tvDesc)

        fun bind(bannerItem: BannerItem) {
            imageLoader.load(bannerItem.imagePath, bannerImage)
            bannerTitle.text = bannerItem.title
            bannerDesc.text = bannerItem.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_banner, parent, false)
        val bannerItemViewHolder = BannerItemViewHolder(itemView)
        itemView.onClick {
            val adapterPosition = bannerItemViewHolder.adapterPosition
            bannerItemClick?.invoke(bannerItemList[adapterPosition])
        }
        return bannerItemViewHolder
    }

    override fun onBindViewHolder(holder: BannerItemViewHolder, position: Int) {
        val bannerItem = bannerItemList[position]
        holder.bind(bannerItem)
    }

    override fun getItemCount(): Int = bannerItemList.size

    fun submitData(newBannerItem: List<BannerItem>) {
        bannerItemList.clear()
        bannerItemList.addAll(newBannerItem)
        notifyItemRangeChanged(0, newBannerItem.size)
    }
}