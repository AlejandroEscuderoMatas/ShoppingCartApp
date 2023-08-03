package com.example.carrito

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DialogBuy (var fragment: FragmentCart, var totalPrice : Int, var user_uid : String) : DialogFragment()
{
    private lateinit var vista : View
    private lateinit var text : TextView
    private lateinit var buttonYes : Button
    private lateinit var buttonNo : Button
    private lateinit var database: FirebaseDatabase

    init {
        database = FirebaseDatabase.getInstance("https://utadaem-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Vista
        vista = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_buy, null)

        builder.setView(vista)

        return builder.create()
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        val referencia : DatabaseReference = database.reference.child("Users")
            .child(user_uid)
            .child("Cart")

        text = vista.findViewById(R.id.text_total_price)
        buttonYes = vista.findViewById(R.id.button_logout_yes)
        buttonNo = vista.findViewById(R.id.button_logout_no)

        text.text = "Confirm paying $${totalPrice}?"

        buttonYes.setOnClickListener {
            Snackbar.make(vista, "The payment wes campleted successfully", Snackbar.LENGTH_SHORT).show()

            referencia.removeValue()
                .addOnSuccessListener {
                }.addOnFailureListener {
                        error ->

                }
            findNavController().navigate(R.id.fragment_products)
            fragment.cartAdapter.clearList()
            dismiss() // Cierra el cuadro de diálogo
        }

        buttonNo.setOnClickListener {
            dismiss()
        }
    }
}