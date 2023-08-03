package com.example.carrito.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carrito.FragmentCart
import com.example.carrito.R
import com.example.carrito.model.Product
import com.google.firebase.database.FirebaseDatabase

class CartAdapter(var fragment: FragmentCart, var uid: String) : RecyclerView.Adapter<CartAdapter.MyHolder>()
{
    private lateinit var productList: ArrayList<Product>
    private lateinit var database: FirebaseDatabase
    private var totalPrice : Int = 0;

    init {
        productList = ArrayList()
        database = FirebaseDatabase.getInstance("https://utadaem-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var productImage : ImageView
        var productTitle : TextView
        var productPrice : TextView

        init {
            productTitle = itemView.findViewById(R.id.product_title_recycler)
            productImage = itemView.findViewById(R.id.product_image_recycler)
            productPrice = itemView.findViewById(R.id.product_price_recycler)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_cart, parent, false)

        return MyHolder(view)
    }

    fun addProduct(product : Product) {
        productList.add(product)
        notifyItemInserted(productList.size - 1)
    }

    fun getTotalPrice(): Int {
        for(i in productList)
        {
            totalPrice = i.price!!
            Log.v("precio", totalPrice.toString())
        }
        return totalPrice
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun clearList() {
        productList.clear()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.productTitle.text = productList[position].title
        holder.productPrice.text = "Price: " + productList[position].price.toString() + "$"
        Glide.with(fragment).load(productList[position].thumbnail).into(holder.productImage)
    }
}