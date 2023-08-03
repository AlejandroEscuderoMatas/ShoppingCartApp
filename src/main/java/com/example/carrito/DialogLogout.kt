package com.example.carrito

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment

class DialogLogout : DialogFragment()
{
    private lateinit var vista : View
    private lateinit var buttonYes : Button
    private lateinit var buttonNo : Button

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Vista
        vista = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_logout, null)

        builder.setView(vista)

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        buttonYes = vista.findViewById(R.id.button_logout_yes)
        buttonNo = vista.findViewById(R.id.button_logout_no)

        buttonYes.setOnClickListener {
            //val activity : Context = vista.context as Activity

            val activityContext = context as Activity

            activityContext.finish()

            dismiss() // Cierra el cuadro de diálogo
        }

        buttonNo.setOnClickListener {
            dismiss()
        }
    }
}