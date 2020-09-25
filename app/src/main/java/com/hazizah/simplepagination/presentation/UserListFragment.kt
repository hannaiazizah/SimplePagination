package com.hazizah.simplepagination.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazizah.simplepagination.databinding.FragmentUserListBinding
import com.hazizah.simplepagination.domain.State
import com.hazizah.simplepagination.presentation.adapter.UserListAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class UserListFragment : Fragment() {
    private val viewModel by viewModel<UserViewModel>()
    private lateinit var usersAdapter: UserListAdapter
    private lateinit var binding: FragmentUserListBinding
    private var hasSubmit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usersAdapter = UserListAdapter { viewModel.retry() }

        viewModel.networkState.observe(viewLifecycleOwner, Observer { state ->
            usersAdapter.setState(state)
            if (hasSubmit) { // handle first data
                when(state) {
                    State.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.errorLayout.visibility = View.GONE
                        binding.listUsers.visibility = View.GONE
                    }
                    State.ERROR -> {
                        hasSubmit = false
                        binding.progressBar.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.listUsers.visibility = View.GONE
                    }
                    State.DONE -> {
                        hasSubmit = false
                        binding.progressBar.visibility = View.GONE
                        binding.errorLayout.visibility = View.GONE
                        binding.listUsers.visibility = View.VISIBLE
                    }
                    else -> {
                        hasSubmit = false
                        binding.progressBar.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.listUsers.visibility = View.GONE
                    }
                }
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.users.observe(viewLifecycleOwner, Observer { users->
            usersAdapter.submitList(users)
        })

        viewModel.totalCount.observe(viewLifecycleOwner, Observer { totalCount ->
            if (totalCount == 0) {
                binding.txtNoUserFound.visibility = View.VISIBLE
            } else {
                binding.txtNoUserFound.visibility = View.GONE
            }
        })

        binding.listUsers.apply {
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
            adapter = usersAdapter

        }

        binding.btnRetry.setOnClickListener {
            viewModel.retry()
            hasSubmit = true
        }

        binding.viewSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    binding.progressBar.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE
                    binding.listUsers.visibility = View.GONE
                    binding.txtNoUserFound.visibility = View.GONE
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchUser(query)
                    hasSubmit = true
                }
                return false
            }
        })
    }
}