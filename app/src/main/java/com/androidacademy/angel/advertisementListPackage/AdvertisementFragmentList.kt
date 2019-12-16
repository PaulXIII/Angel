package com.androidacademy.angel.advertisementListPackage

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.MainActivity
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.network.Repository
import com.androidacademy.angel.prefs
import kotlinx.android.synthetic.main.advertisement_list_fragment.*

class AdvertisementFragmentList : Fragment(), OnAdvertClick {

    override fun onClick(ad: AdvertModel) {
        fragmentManager?.let {
            (activity as MainActivity).fragmentController.openDetails(ad)
        }
    }

    private lateinit var parentActivity: AppCompatActivity
    private lateinit var adListAdapter: AdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.advertisement_list_fragment, container, false)
        val advertisementListView: RecyclerView = view.findViewById(R.id.advertisement_list_view)
        advertisementListView.adapter = adListAdapter
        AdListFragmentLifecycleObserver(viewLifecycleOwner) { list ->
            adListAdapter.submitList(list)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        Repository.adverts().observe(viewLifecycleOwner,
            Observer<List<AdvertModel>> { data ->
                val search: SearchView = menu.findItem(R.id.itemSearch).actionView as SearchView
                search.setMaxWidth(Integer.MAX_VALUE);
                search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        query?.let {
                            adListAdapter.submitList(data?.filter {
                                it.title?.contains(query, ignoreCase = true) ?: false
                            })
                        } ?: adListAdapter.submitList(data)
                        return true
                    }
                })
                search.setOnCloseListener {
                    adListAdapter.submitList(data)
                    return@setOnCloseListener false
                }
            })

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBackButtons(false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.itemLogin)
        item.isVisible = prefs.idClient == null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemSearch -> {
                (activity as MainActivity).fragmentController.openLogin()
                true
            }
            R.id.itemLogin -> {
                (activity as MainActivity).fragmentController.openLogin()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AppCompatActivity
        adListAdapter =
            AdAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_new_person.setOnClickListener {
            (activity as MainActivity).fragmentController.run {
                if (prefs.idClient == null) {
                    openLogin()
                } else {
                    openAddNewPerson()
                }
            }
        }
    }
}