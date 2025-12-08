package com.techiq.aplikasquiztechiq.frgamentt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.techiq.aplikasquiztechiq.R

class LeaderboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        // Ambil skor player
        val prefs = requireActivity().getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
        val skorPlayer = prefs.getInt("total_score", 0)

        // Update skor utama
        val txtMainScore = view.findViewById<TextView>(R.id.txtMainScore)
        txtMainScore.text = skorPlayer.toString()

        // Dummy players
        val dummyPlayers = listOf(
            Player("Bimo Elang", 400),
            Player("Rijul", 300),
            Player("Alpin", 200),
            Player("Rapid", 100),
            Player("Wowo", 50),
            Player("Owi", 40)
        )

        // Gabungkan dummy player dan skor player
        val allPlayers = (dummyPlayers + Player("Skor Anda", skorPlayer))
            .sortedByDescending { it.score }

        // Container linear layout
        val container = view.findViewById<LinearLayout>(R.id.containerLeaderboard)
        val itemLayout = R.layout.item_leaderboard_row

        allPlayers.forEachIndexed { index, player ->
            val row = layoutInflater.inflate(itemLayout, container, false)

            val txtName = row.findViewById<TextView>(R.id.txtPlayerName)
            val txtScore = row.findViewById<TextView>(R.id.txtPlayerScore)

            txtName.text = "${index + 1}. ${player.name}"
            txtScore.text = player.score.toString()

            // Highlight row player
            if (player.name == "Skor Anda") {
                row.setBackgroundResource(R.drawable.rounded_item_yellow)
                txtName.setTextColor(resources.getColor(R.color.black))
                txtScore.setTextColor(resources.getColor(R.color.black))
            }

            container.addView(row)
        }

        return view
    }

    data class Player(val name: String, val score: Int)
}
