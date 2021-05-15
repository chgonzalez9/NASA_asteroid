package com.chgonzalez.nasaasteroid.asteroid

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chgonzalez.nasaasteroid.R
import com.chgonzalez.nasaasteroid.databinding.FragmentAsteroidBinding
import com.chgonzalez.nasaasteroid.network.PictureOfDay
import com.chgonzalez.nasaasteroid.util.AsteroidAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_asteroid.*

class AsteroidFragment : Fragment() {

    private val viewModel: AsteroidViewModel by lazy {
        ViewModelProvider(this).get(AsteroidViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentAsteroidBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.asteroidViewModel = viewModel

        binding.asteroidList.adapter = AsteroidAdapter()

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