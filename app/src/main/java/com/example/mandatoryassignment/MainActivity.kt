package com.example.mandatoryassignment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.ui.onNavDestinationSelected
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.mandatoryassignment.databinding.ActivityMainBinding
import com.example.mandatoryassignment.databinding.AlertDialogBinding
import com.example.mandatoryassignment.models.Item
import com.example.mandatoryassignment.models.ItemViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var alertBinding: AlertDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.fab.setOnClickListener { v ->
//            findNavController().navigate(R.id.action_FirstFragment_to_addFragment)
//            dialog()
//        }
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            //R.id.action_settings -> true
            R.id.action_settings -> true
            R.id.action_signout -> {
                if (Firebase.auth.currentUser != null) {
                    Firebase.auth.signOut()
                    val navController = findNavController(R.id.nav_host_fragment_content_main)
                    navController.popBackStack(R.id.FirstFragment, false)
                    // https://developer.android.com/codelabs/android-navigation#6
                } else {
                    Snackbar.make(binding.root, "Cannot sign out", Snackbar.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
//    private fun dialog() {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//
//        builder.setTitle("Add item")
//        val inputFieldTitle = alertBinding.title.text.toString().trim()
//        //EditText(applicationContext)
////        inputFieldTitle.hint = "Enter a title"
////        inputFieldTitle.inputType = InputType.TYPE_CLASS_TEXT
//        //builder.setView(inputFieldTitle)
//        val inputFieldDescription = EditText(this)
//        inputFieldDescription.hint = "Enter a description"
//        inputFieldDescription.inputType = InputType.TYPE_CLASS_TEXT
//        val inputFieldPrice = EditText(this)
//        inputFieldPrice.inputType = InputType.TYPE_CLASS_NUMBER
//        val inputFieldSeller = EditText(this)
//        inputFieldSeller.inputType = InputType.TYPE_CLASS_TEXT
//        val inputFieldDate = EditText(this)
//        inputFieldDate.inputType = InputType.TYPE_CLASS_DATETIME
//
//        builder.setView(alertBinding.title)
//
//        builder.setPositiveButton("OK") { dialog, which ->
//
//            val title = inputFieldTitle
//            val description = inputFieldDescription.text.toString().trim()
//            val price = inputFieldPrice.text.toString().trim()
//            val seller = inputFieldSeller.text.toString().trim()
//            val date = inputFieldDate.text.toString().trim()
//
//            when {
//                title.isEmpty() ->
//                    Snackbar.make(binding.root, "No title", Snackbar.LENGTH_LONG).show()
//                description.isEmpty() ->
//                    Snackbar.make(binding.root, "No description", Snackbar.LENGTH_LONG).show()
//                price.isEmpty() ->
//                    Snackbar.make(binding.root, "No price", Snackbar.LENGTH_LONG).show()
//                seller.isEmpty() ->
//                    Snackbar.make(binding.root, "No seller", Snackbar.LENGTH_LONG).show()
//                date.isEmpty() ->
//                    Snackbar.make(binding.root, "No date", Snackbar.LENGTH_LONG).show()
//                else -> {
//                    val model: ItemViewModel by viewModels()
//                    val itemObject = Item(title, description, price.toInt(), seller, date.toInt())
//                    model.add(itemObject)
//                }
//            }
//        }
//        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
//        builder.show()
//    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}