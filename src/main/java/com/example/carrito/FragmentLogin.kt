package com.example.carrito

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import com.example.carrito.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentLogin : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCompleteLogin.setOnClickListener {
            if(binding.loginEmail.text.isNotEmpty() && binding.loginPassword.text.isNotEmpty())
            {
                auth.signInWithEmailAndPassword(
                    binding.loginEmail.text.toString(),
                    binding.loginPassword.text.toString()
                ).addOnCompleteListener {
                    if(it.isSuccessful){
                        Snackbar.make(binding.root, "Login correcto", Snackbar.LENGTH_SHORT).show()

                        val intent = Intent(requireContext(), SecondActivity::class.java)

                        val bundle: Bundle = Bundle()
                        bundle.putString("uid_user", auth.uid)

                        intent.putExtras(bundle)
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                        //Para mandar cosas adicionales a otra pantalla lo haremos a traves de una variable bundle
                        startActivity(intent)
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

        binding.textRegister.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}