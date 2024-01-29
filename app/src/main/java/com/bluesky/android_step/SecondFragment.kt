package com.bluesky.android_step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bluesky.android_step.databinding.FragmentFirstBinding
import com.bluesky.android_step.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val args: SecondFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val action = SecondFragmentDirections.actionSecondFragmentToFirstFragment(90)
        binding.secondText.text =  args.argNumber.toString()

        binding.secondButton.setOnClickListener {
            findNavController().navigate(action)
//            findNavController().navigate(R.id.firstFragment) // Navigate to another fragment
//            findNavController().navigate(R.id.action_secondFragment_to_firstFragment, null)
        }
    }


}