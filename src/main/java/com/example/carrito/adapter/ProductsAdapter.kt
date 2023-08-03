package com.example.carrito.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carrito.DialogImage
import com.example.carrito.FragmentProducts
import com.example.carrito.R
import com.example.carrito.model.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random

class ProductsAdapter (var fragment: FragmentProducts, var uid : String) : RecyclerView.Adapter<ProductsAdapter.MyHolder>()
{
    private lateinit var productList: ArrayList<Product>
    private lateinit var database: FirebaseDatabase
    private lateinit var view : View


    init {
        productList = ArrayList()
        database = FirebaseDatabase.getInstance("https://utadaem-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var productImage : ImageView
        var productTitle : TextView
        var productPrice : TextView
        var buyButton    : Button
        var detailButton : Button

        init {
            productTitle = itemView.findViewById(R.id.product_title_recycler)
            productImage = itemView.findViewById(R.id.product_image_recycler)
            productPrice = itemView.findViewById(R.id.product_price_recycler)
            buyButton    = itemView.findViewById(R.id.buy_button)
            detailButton = itemView.findViewById(R.id.detail_button)
        }
    }

    fun addProduct(product : Product) {
        productList.add(product)
        notifyItemInserted(productList.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        view= LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)

        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val colorDrawable = ColorDrawable(Color.BLACK)
        colorDrawable.alpha = 150

        holder.productTitle.text = productList[position].title
        holder.productPrice.text = "Price: " + productList[position].price.toString() + "$"
        Glide.with(fragment).load(productList[position].thumbnail).into(holder.productImage)

        holder.buyButton.setOnClickListener {
            val referencia : DatabaseReference = database.reference.child("Users")
                .child(uid)
                .child("Cart")
                .child(Random.nextInt(1, 1000).toString())

            //MAPEAMOS EL PRODUCTOP EN BD DESDE EL OBJECTO USUARIO CREADO
            referencia.setValue(
                Product(
                    productList[position].id,
                    productList[position].title,
                    productList[position].description,
                    productList[position].price,
                    productList[position].discountPercentage,
                    productList[position].rating,
                    productList[position].stock,
                    productList[position].brand,
                    productList[position].category,
                    productList[position].thumbnail,
                    null
                )
            )
        }

        holder.productImage.setOnClickListener {
            fragment.setSemiTransparentBackground(true)
            DialogImage(productList[position].thumbnail!!, fragment).show(fragment.requireActivity().supportFragmentManager, null)
        }

        holder.detailButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("user_uid", uid)
            bundle.putString("id", productList[position].id)
            bundle.putString("title", productList[position].title)
            bundle.putString("description", productList[position].description)
            bundle.putInt("price", productList[position].price!!)
            bundle.putInt("discountPercentage", productList[position].discountPercentage!!)
            bundle.putInt("rating", productList[position].rating!!)
            bundle.putInt("stock", productList[position].stock!!)
            bundle.putString("brand", productList[position].brand)
            bundle.putString("category", productList[position].category)
            bundle.putString("thumbnail", productList[position].thumbnail)
            bundle.putStringArray("images", Array<String>(productList[position].images!!.size)
                { i -> productList[position].images?.get(i) ?: "" })

            fragment.findNavController().navigate(R.id.action_ProductsFragment_to_DetailFragment, bundle)
        }
    }
}