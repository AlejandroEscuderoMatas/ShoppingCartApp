package com.example.carrito

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrito.adapter.CartAdapter
import com.example.carrito.databinding.FragmentCartBinding
import com.example.carrito.model.Product
import com.google.firebase.database.*

class FragmentCart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private lateinit var context: Context
    private lateinit var user_uid : String
    private lateinit var database: FirebaseDatabase
    public lateinit var cartAdapter : CartAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root

    }

    init {
        database = FirebaseDatabase.getInstance("https://utadaem-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
        user_uid = arguments?.getString("user_uid") ?: "uid"
        cartAdapter = CartAdapter(this, user_uid)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var totalPrice : Int = 0

        binding.productsRecyclerCart.adapter = cartAdapter
        binding.productsRecyclerCart.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val referencia : DatabaseReference = database.reference.child("Users")
            .child(user_uid)
            .child("Cart")

        referencia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ( data in snapshot.children )
                {
                    val product = data.getValue(Product::class.java)

                    totalPrice += product!!.price ?: 0

                    Log.v(
                        "nodos_fb",
                        "${product!!.title} es de ${product!!.stock} PRECIO ${totalPrice}"
                    )
                    cartAdapter.addProduct(product)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        Log.v(
            "nodos_fb",
            "AQUI ESTOy"
        )


        binding.buttonPay.setOnClickListener {
            DialogBuy(this, totalPrice, user_uid).show(requireActivity().supportFragmentManager, null)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}