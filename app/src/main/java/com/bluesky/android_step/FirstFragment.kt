package com.bluesky.android_step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bluesky.android_step.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    val args: FirstFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(30)
        binding.firstText.text = args.argSeocndNumber.toString()

        binding.firstButton.setOnClickListener {
            findNavController().navigate(action)
//            findNavController().navigate(R.id.secondFragment) // Navigate to another fragment
//            findNavController().navigate(R.id.action_firstFragment_to_secondFragment) // 목적지가 아닌 액션을 매개변수로 전달
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}