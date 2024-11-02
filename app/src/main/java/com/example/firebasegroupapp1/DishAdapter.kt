package com.example.firebasegroupapp1

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class DishAdapter(options: FirebaseRecyclerOptions<Dish>) :
    FirebaseRecyclerAdapter<Dish, DishAdapter.MyViewHolder>(options) {
}


