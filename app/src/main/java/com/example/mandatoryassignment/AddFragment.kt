package com.example.mandatoryassignment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatoryassignment.databinding.FragmentAddBinding
import com.example.mandatoryassignment.models.Item
import com.example.mandatoryassignment.models.ItemViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val itemViewModel: ItemViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var item: Item




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var currentUser = auth.currentUser

        if (currentUser != null) {
            currentUser.email
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        if (currentUser != null) {
        binding.buttonAdd.setOnClickListener {
            val title = binding.title.text.trim().toString()
            val description = binding.description.text.trim().toString()
            val price = binding.price.text.trim().toString().toInt()
            val seller = currentUser?.email.toString()
            val date = System.currentTimeMillis()/1000

            val item = Item(
                id = id,
                title = title,
                description = description,
                price = price,
                seller = seller,
                date = date.toInt()
            )
            itemViewModel.add(item)
            findNavController().popBackStack()
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}