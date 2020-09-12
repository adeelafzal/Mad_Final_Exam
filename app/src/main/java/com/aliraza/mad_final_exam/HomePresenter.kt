package com.aliraza.mad_final_exam

 interface MyInterface {
    var mylist: ArrayList<Vehicle>



    fun saveData(list: ArrayList<Vehicle>) {
        mylist = list
    }

    fun getData(): ArrayList<Vehicle> {
        return mylist
    }

}