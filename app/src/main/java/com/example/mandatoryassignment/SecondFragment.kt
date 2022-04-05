package com.example.mandatoryassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatoryassignment.databinding.FragmentSecondBinding
import com.example.mandatoryassignment.models.ItemViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val itemViewModel: ItemViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser


        val bundle = requireArguments()
        val secondFragmentArgs: SecondFragmentArgs = SecondFragmentArgs.fromBundle(bundle)
        val position = secondFragmentArgs.position
        val item = itemViewModel[position]
        if (item == null) {
            binding.textviewMessage.text = "No item here!"
            return
        }
        binding.editTextTitle.setText(item.title)
        binding.editTextDescription.setText(item.description)
        binding.editTextPrice.setText(item.price.toString())
        binding.editTextSeller.setText(item.seller)
        binding.editTextDate.setText(item.date.toString())

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        if (item.seller == currentUser?.email) {
            binding.buttonDelete.setOnClickListener {
                itemViewModel.delete(item.id)
                findNavController().popBackStack()
            }
        } else {
            binding.buttonDelete.setVisibility(View.GONE)
        }

        binding.buttonDelete.setOnClickListener {
            itemViewModel.delete(item.id)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}