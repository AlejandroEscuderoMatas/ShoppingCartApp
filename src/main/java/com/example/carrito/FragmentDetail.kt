package com.example.carrito

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.carrito.databinding.FragmentDetailBinding
import com.example.carrito.model.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentDetail : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private lateinit var product  : Product
    private lateinit var context  : Context
    private lateinit var user_uid : String
    private lateinit var database : FirebaseDatabase

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    init {
        database = FirebaseDatabase.getInstance("https://utadaem-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context

        user_uid = arguments?.getString("user_uid") ?: "uid"

        product = Product(
            arguments?.getString("id") ?: "id",
           arguments?.getString("title") ?: "title",
           arguments?.getString("description") ?: "description",
           arguments?.getInt("price") ?: 0,
           arguments?.getInt("discountPercentage") ?: 0,
           arguments?.getInt("rating") ?: 0,
           arguments?.getInt("stock") ?: 0,
           arguments?.getString("brand") ?: "brand",
           arguments?.getString("category") ?: "category",
           arguments?.getString("thumbnail") ?: "",
           (arguments?.getStringArray("images")!!.asList())
        )
    }
    //arguments?.getStringArray("images") //?: ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                // need to return the number of items we have in the carousel
                return product.images!!.size
            }

            override fun populate(view: View, index: Int) {
                // need to implement this to populate the view at the given index
            }

            override fun onNewItem(index: Int) {
                // called when an item is set
            }
        })*/

        binding.productTitle.text = product.title
        binding.productDesc.text = product.description
        binding.productPrice.text = "$" + product.price.toString()
        binding.productDiscount.text = "-" + product.discountPercentage.toString() + "% !!!"
        binding.productRating.text = product.rating.toString() + "/5 based on users opinion"
        binding.productStock.text = "There are " + product.stock.toString() + " left"
        binding.productBrand.text = "From " + product.brand
        binding.productCategory.text = product.category

        Glide.with(context).load(product.thumbnail).into(binding.productImage)

        binding.buttonAddCart.setOnClickListener {
            Snackbar.make(view, "Se ha añadido al carrito", Snackbar.LENGTH_SHORT).show()

            val referencia : DatabaseReference = database.reference.child("Users")
                .child(user_uid)
                .child("Cart")
                .child(Random.nextInt(1, 1000).toString())

            referencia.setValue(
                product
            )
        }

        /*binding.imageSwitcher.setFactory {
            val imageView: ImageView
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setPadding(8, 8, 8, 8)
            imageView


            PARTE GRAFICA
    <ImageSwitcher
        android:id="@+id/imageSwitcher"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/guideline14"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageButton
        android:id="@+id/img_nav_before"
        android:layout_width="50dp"
        android:layout_height="149dp"
        app:layout_constraintBottom_toTopOf="@+id/guideline14"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:background="@android:color/transparent"
        android:src="@drawable/baseline_navigate_before_24"/>

    <ImageButton
        android:id="@+id/img_nav_next"
        android:layout_width="50dp"
        android:layout_height="149dp"
        app:layout_constraintBottom_toTopOf="@+id/guideline14"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:background="@android:color/transparent"
        android:src="@drawable/baseline_navigate_next_24"/>
        }*/

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_DetailFragment_to_ProductsFragment)
        }*/
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}