package com.example.carrito

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.carrito.databinding.FragmentRegisterBinding
import com.example.carrito.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentRegister : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var user_uid : String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://utadaem-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCompleteRegister.setOnClickListener {
            if(binding.registerEmail.text.isNotEmpty() && binding.registerPassword.text.isNotEmpty())
            {
                auth.createUserWithEmailAndPassword(
                    binding.registerEmail.text.toString(),
                    binding.registerPassword.text.toString()
                ).addOnCompleteListener {
                    if(it.isSuccessful){
                        Snackbar.make(binding.root, "Registro correcto", Snackbar.LENGTH_SHORT).show()
                        var referencia : DatabaseReference = database.reference.child("Users")
                            .child(auth.uid!!)
                            .child("Data")

                        user_uid = auth.uid!!

                        //MAPEAMOS EL USUARIO EN BD DESDE EL OBJECTO USUARIO CREADO
                        referencia.setValue(
                            User(binding.registerEmail.text.toString(),
                                binding.registerName.text.toString(),
                                binding.registerAge.text.toString().toInt(),
                                binding.registerAddress.text.toString())
                        )

                        val intent = Intent(context, SecondActivity::class.java)

                        val bundle: Bundle = Bundle()
                        bundle.putString("uid_user", auth.uid)

                        intent.putExtras(bundle)
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                        //Para mandar cosas adicionales a otra pantalla lo haremos a traves de una variable bundle
                        context?.startActivity(intent)
                    }
                    else {
                        Snackbar.make(binding.root, "Datos de usuario no válidos", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Snackbar.make(binding.root, "Datos de usuario no válidos", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.textLogin.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}