package com.chgonzalez.nasaasteroid.asteroid

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chgonzalez.nasaasteroid.R
import com.chgonzalez.nasaasteroid.databinding.FragmentAsteroidBinding
import com.chgonzalez.nasaasteroid.network.DateFilters
import com.chgonzalez.nasaasteroid.util.AsteroidAdapter

class AsteroidFragment : Fragment() {

    private val viewModel: AsteroidViewModel by lazy {
        ViewModelProvider(this).get(AsteroidViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentAsteroidBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.asteroidViewModel = viewModel


        binding.asteroidList.adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.navigateToDetails(it)
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController()
                    .navigate(AsteroidFragmentDirections.actionAsteroidFragmentToDetailFragment(it))
                viewModel.displayDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
                when (item.itemId) {
                    R.id.show_week_asteroid -> DateFilters.ALL_WEEK
                    R.id.show_today_asteroid -> DateFilters.TODAY
                    else -> DateFilters.SAVED
                }
        )
        return true
    }
}