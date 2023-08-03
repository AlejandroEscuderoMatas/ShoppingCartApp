package com.example.carrito

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DialogImage (var image : String, var fragment : FragmentProducts) : DialogFragment()
{
    private lateinit var vista : View
    private lateinit var imageView : ImageView

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Vista
        vista = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_image, null)

        builder.setView(vista)

        return builder.create()
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        imageView = vista.findViewById(R.id.image_maximized)

        Glide.with(fragment).load(image).into(imageView)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        fragment.setSemiTransparentBackground(false)
    }
}