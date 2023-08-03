package com.example.carrito.model

class User (var email: String?=null, var name: String?=null, var age : Int?=null, var address : String?=null)
{
    override fun toString(): String {
        return "Producto(Email=$email, Name=$name, Age=$age, Address=$address)"
    }
}