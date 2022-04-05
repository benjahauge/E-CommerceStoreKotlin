package com.example.mandatoryassignment

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mandatoryassignment.databinding.FragmentFirstBinding
import com.example.mandatoryassignment.models.Item
import com.example.mandatoryassignment.models.ItemViewModel
import com.example.mandatoryassignment.models.MyAdapter
import com.example.mandatoryassignment.repository.ResaleRepository
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val itemViewModel: ItemViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var resaleRepository: ResaleRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        resaleRepository = ResaleRepository()
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewUser.text = "Welcome " + Firebase.auth.currentUser?.email

        val currentUser = auth.currentUser

        if (currentUser != null) {
            binding.fab.setOnClickListener { v ->
                findNavController().navigate(R.id.action_FirstFragment_to_addFragment)
            }
        } else {
            binding.fab.setVisibility(View.GONE)
        }

        binding.filterPrice.setOnClickListener { v ->
            showDialog()
        }

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_loginFragment)
        }



        itemViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = MyAdapter(items) { position ->
                    val action =
                        FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(action)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        itemViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewFirst.text = errorMessage
        }

        itemViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            itemViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        binding.buttonSortPriceAsc.setOnClickListener {
            itemViewModel.sortByPrice()
        }

        binding.buttonSortPriceDesc.setOnClickListener {
            itemViewModel.sortByPriceDescinding()
        }


    }

    fun showDialog() {
        val builder: AlertDialog.Builder? = getActivity()?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Filter By Price")

        val layout = LinearLayout(activity)
        layout.orientation = LinearLayout.VERTICAL

        val MaxPriceInput = EditText(activity)
        MaxPriceInput.hint = "Max Price"
        MaxPriceInput.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(MaxPriceInput)

        val MinPriceInput = EditText(getActivity())
        MinPriceInput.hint = "min Price"
        MinPriceInput.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(MinPriceInput)

        builder?.setView(layout)

        builder?.setPositiveButton("OK") { dialog, which ->
            val filterMaxprice = MaxPriceInput.text.toString().trim()
            val filterMinprice = MinPriceInput.text.toString().trim()

            when {
                filterMaxprice.isEmpty() ->
                    Snackbar.make(binding.root, "No Max Price", Snackbar.LENGTH_LONG).show()
                filterMinprice.isEmpty() ->
                    Snackbar.make(binding.root, "No Min Price", Snackbar.LENGTH_LONG).show()
                else -> {
                    itemViewModel.filterByPrice(filterMaxprice.toInt(), filterMinprice.toInt())
                }
            }
        }
        builder?.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder?.show()
    }

//    private fun setupTaskRecyclerView(itemlist: MutableList<LiveData<List<Item>>>){
//        itemViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
//            binding.progressbar.visibility = View.GONE
//            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
//            if (items != null) {
//                val adapter = MyAdapter(items) { position ->
//                    val action =
//                        FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
//                    findNavController().navigate(action)
//                }
//                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
//                binding.recyclerView.setHasFixedSize(true)
//                itemlist.sortedBy { it.value.}
//                binding.recyclerView.adapter = adapter
//            }
//        }
//
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_login -> {
                //Skal direct til LoginFragment somehow!!
                true
            }
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

