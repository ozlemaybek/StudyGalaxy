package com.ozlem.studygalaxy.view

import android.view.View

// Burada listedeki herhangi bir hedefe tıklanınca ne olacak bunu yazacağız ve bize bir view verecek yani nereden geldiğimizi verecek.
interface GoalClickListener {
    fun goalClicked(view : View)
}