package com.hazizah.simplepagination.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hazizah.simplepagination.R
import com.hazizah.simplepagination.domain.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(product: Product?) {
        product?.let {
            itemView.txt_product_name.text = product.productName
            itemView.txt_product_price.text = PriceFormatter.format(product.productPrice)
            Glide
                .with(itemView.context)
                .load(product.images.smallUrl[0])
                .placeholder(R.drawable.placeholder_avatar)
                .into(itemView.img_product_thumbnail)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view)
        }
    }
}
