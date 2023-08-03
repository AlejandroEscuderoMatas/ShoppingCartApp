package com.example.carrito

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.carrito.adapter.ProductsAdapter
import com.example.carrito.databinding.FragmentProductsBinding
import com.example.carrito.model.Product

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentProducts : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private lateinit var productsAdapter : ProductsAdapter
    private lateinit var user_uid : String
    private var backgroundView: View? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user_uid = this.arguments?.getString("user_uid") ?: "defecto"
        productsAdapter = ProductsAdapter(this, user_uid)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundView = view.findViewById(R.id.fragment_product_view)

        binding.productsRecycler.adapter = productsAdapter
        binding.productsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.cartButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("user_uid", user_uid)

            this.findNavController().navigate(R.id.action_ProductsFragment_to_CartFragment, bundle)
        }

        // 1 - Crear la peticion para jsonObject
        val url = " https://dummyjson.com/products"
        val peticionJSON : JsonObjectRequest =
            JsonObjectRequest(url, {
                val productsArray = it.getJSONArray("products")

                for(i in 0 until productsArray.length()){
                    val product = productsArray.getJSONObject(i)

                    val imageList : ArrayList<String> = ArrayList()

                    for(j in 0 until product.getJSONArray("images").length()) {
                        imageList.add(product.getJSONArray("images")[j].toString())
                    }

                    //val imageListStatic : Array<String> = Array(imageList.size, )

                    val newProduct = Product(
                        product.getString("id"),
                        product.getString("title"),
                        product.getString("description"),
                        product.getInt("price"),
                        product.getInt("discountPercentage"),
                        product.getInt("rating"),
                        product.getInt("stock"),
                        product.getString("brand"),
                        product.getString("category"),
                        product.getString("thumbnail"),
                        imageList
                    )
                    productsAdapter.addProduct(newProduct)
                }
            }, {
                Log.v("respuesta", "Error en la respuesta")
            })

        // 2 - Consumir la peticion
        Volley.newRequestQueue(context).add(peticionJSON)
    }

    fun setSemiTransparentBackground(enabled: Boolean) {
        val color = if (enabled) {
            ContextCompat.getColor(requireContext(), R.color.semi_transparent) // Define el color semitransparente en tus recursos
        } else {
            Color.TRANSPARENT
        }
        backgroundView?.setBackgroundColor(color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}