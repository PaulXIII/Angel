package com.androidacademy.angel.advertisementListPackage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.R


class AdvertisementFragmentList : Fragment() {

    private lateinit var parentActivity: AppCompatActivity
    private lateinit var adListAdapter: AdAdapter
    private lateinit var optionMenuViewModel: OptionMenuViewModel
    private lateinit var editText: EditText
    private lateinit var searchLayout: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.advertisement_list_fragment, container, false)
        val advertisementListView: RecyclerView = view.findViewById(R.id.advertisement_list_view)
        advertisementListView.adapter = adListAdapter
        AdListFragmentLifecycleObserver(viewLifecycleOwner) { list ->
            adListAdapter.ads = list
            adListAdapter.notifyDataSetChanged()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AppCompatActivity
        adListAdapter =
            AdAdapter(
                listOf(),
                R.drawable.logo_square
            )
        optionMenuViewModel = ViewModelProviders.of(context).get(OptionMenuViewModel::class.java)
        editText = context.findViewById(R.id.ad_list_search_edittext)
        searchLayout = context.findViewById(R.id.ad_list_search_group)
        var clearBtn: Button = context.findViewById(R.id.ad_list_clear_btn)
        clearBtn.setOnClickListener { view ->
            if (editText.text == null || editText.text.equals("")) {
                searchLayout.visibility = View.GONE
            } else {
                editText.text.clear()
            }
        }
        searchLayout.visibility = View.GONE

        optionMenuViewModel.menuClicked.observe(this, Observer {

            when (it) {
                MenuItemName.FILTER -> Toast.makeText(context, "Filter", Toast.LENGTH_SHORT).show()
                MenuItemName.SETTINGS -> Toast.makeText(
                    context,
                    "Settings",
                    Toast.LENGTH_SHORT
                ).show()
                MenuItemName.SEARCH -> searchLayout.visibility = View.VISIBLE
            }

        })
    }
}
/*
*
* */