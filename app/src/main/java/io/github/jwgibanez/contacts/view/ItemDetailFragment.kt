package io.github.jwgibanez.contacts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.jwgibanez.contacts.databinding.FragmentItemDetailBinding
import android.view.*
import io.github.jwgibanez.contacts.viewmodel.ContactsViewModel
import io.github.jwgibanez.contacts.data.model.User
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import io.github.jwgibanez.contacts.R
import io.github.jwgibanez.contacts.io.github.jwgibanez.contacts.utils.toast


class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactsViewModel by activityViewModels()

    private val requestCallPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                call()
            } else {
                toast(requireContext(), getString(R.string.require_permission_call))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.user.value != null) {
            viewModel.user.observe(viewLifecycleOwner) { updateView(it) }
        }
        binding.apply {
            icCall?.setOnClickListener { checkCallPermission() }
            icEmail?.setOnClickListener { email() }
        }
    }

    private fun updateView(user: User?) {
        user?.let {
            binding.name!!.text = user.name
            binding.icEmail!!.visibility = if (!user.email.isNullOrEmpty()) VISIBLE else GONE
            val ad = InfoAdapter()
            binding.itemList?.apply {
                adapter = ad
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            ad.set(getValuePairs(user))
        }
    }

    private fun getValuePairs(user: User): ArrayList<InfoAdapter.ValuePair> {
        val pairs = ArrayList<InfoAdapter.ValuePair>()

        pairs.add(InfoAdapter.ValuePair("username", user.username))
        pairs.add(InfoAdapter.ValuePair("email", user.email))

        user.address?.let { address ->
            pairs.add(InfoAdapter.ValuePair("address.street", address.street))
            pairs.add(InfoAdapter.ValuePair("address.suite", address.suite))
            pairs.add(InfoAdapter.ValuePair("address.city", address.city))
            pairs.add(InfoAdapter.ValuePair("address.zipcode", address.zipcode))

            address.geo?.let { geo ->
                pairs.add(InfoAdapter.ValuePair("geo.lat", geo.lat))
                pairs.add(InfoAdapter.ValuePair("geo.lng", geo.lng))
            }
        }

        pairs.add(InfoAdapter.ValuePair("phone", user.phone))
        pairs.add(InfoAdapter.ValuePair("website", user.website))

        user.company?.let { company ->
            pairs.add(InfoAdapter.ValuePair("company.name", company.name))
            pairs.add(InfoAdapter.ValuePair("company.catchPhrase", company.catchPhrase))
            pairs.add(InfoAdapter.ValuePair("company.bs", company.bs))
        }

        return pairs
    }

    private fun checkCallPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                call()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE) -> {
                toast(requireContext(), getString(R.string.require_permission_call))
            }
            else -> {
                requestCallPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
        }
    }

    private fun call() {
        viewModel.user.value?.let {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + it.phone))
            startActivity(intent)
        }
    }

    private fun email() {
        viewModel.user.value?.let {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${it.email}"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hi")
            intent.putExtra(Intent.EXTRA_TEXT, "Hello")
            startActivity(Intent.createChooser(intent, "Email"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}