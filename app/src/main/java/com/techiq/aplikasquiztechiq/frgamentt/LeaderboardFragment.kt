package com.techiq.aplikasquiztechiq.frgamentt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techiq.aplikasquiztechiq.R
import com.techiq.aplikasquiztechiq.adapter.LeaderboardAdapter

class LeaderboardFragment : Fragment() {

    data class Player(
        val name: String,
        val score: Int
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        // Ambil skor player
        val prefs = requireActivity().getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
        val skorPlayer = prefs.getInt("total_score", 0)

        // Tampilkan skor utama
        val txtMainScore = view.findViewById<TextView>(R.id.txtMainScore)
        txtMainScore.text = skorPlayer.toString()

        // Dummy data leaderboard
        val dummyPlayers = listOf(
            Player("Bimo Elang", 400),
            Player("Rijul", 300),
            Player("Alpin", 200),
            Player("Rapid", 100),
            Player("Wowo", 50),
            Player("Owi", 40)
        )

        // Gabungkan dengan skor user
        val allPlayers = (dummyPlayers + Player("Skor Anda", skorPlayer))
            .sortedByDescending { it.score }

        // Setup RecyclerView
        val rvLeaderboard = view.findViewById<RecyclerView>(R.id.rvLeaderboard)
        rvLeaderboard.layoutManager = LinearLayoutManager(requireContext())
        rvLeaderboard.adapter = LeaderboardAdapter(requireContext(), allPlayers)

        return view
    }
}
