package com.example.carrito.model

class Product(
    var id: String?= null,
    var title: String?= null,
    var description: String?= null,
    var price: Int?= null,
    var discountPercentage: Int?= null,
    var rating: Int?= null,
    var stock: Int?= null,
    var brand: String?= null,
    var category: String?= null,
    var thumbnail: String?=null,
    var images: List<String>? =null
)
{
    override fun toString(): String {
        return "Product(id=$id, title=$title, description=$description, " +
                "price=$price, discountPercentage=$discountPercentage, rating=$rating, stock=$stock, brand=$brand, category=$category, " +
                "thumbnail=$thumbnail, images=$images)"
    }
}