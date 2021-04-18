package com.chgonzalez.nasaasteroid.asteroid

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.chgonzalez.nasaasteroid.R
import com.chgonzalez.nasaasteroid.databinding.FragmentAsteroidBinding

class AsteroidFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentAsteroidBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}