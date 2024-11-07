package com.example.firebasegroupapp1

data class CartItem(
    var dishName: String? = null,
    var price: Double = 0.0,
    var quantity: Int? = null,
    var totalPrice: Double = 0.0,
    var key: String? = null
)
