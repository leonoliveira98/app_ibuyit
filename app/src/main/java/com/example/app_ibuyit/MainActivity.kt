package com.example.app_ibuyit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.app_ibuyit.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var backPressedTime = 0L
    lateinit var backToast : Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)

        val homeFragment = HomeFragment()
        val storeFragment = StoreFragment()
        val listasFragment = ListasFragment()
        val carrinhoFragment = CarrinhoFragment()
        val perfilFragment = PerfilFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> makeCurrentFragment(homeFragment)
                R.id.nav_store -> makeCurrentFragment(storeFragment)
                R.id.nav_list -> makeCurrentFragment(listasFragment)
                R.id.nav_shop -> makeCurrentFragment(carrinhoFragment)
                R.id.nav_person -> makeCurrentFragment(perfilFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
}