package com.techiq.aplikasquiztechiq.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techiq.aplikasquiztechiq.R
import com.techiq.aplikasquiztechiq.frgamentt.LeaderboardFragment.Player

class LeaderboardAdapter(
    private val context: Context,
    private val list: List<Player>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtPlayerName)
        val txtScore: TextView = itemView.findViewById(R.id.txtPlayerScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_leaderboard_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = list[position]

        holder.txtName.text = "${position + 1}. ${player.name}"
        holder.txtScore.text = player.score.toString()

        // Default style (biar tidak ketimpa dari item sebelumnya)
        holder.itemView.setBackgroundResource(R.drawable.rounded_item)
        holder.txtName.setTextColor(Color.WHITE)
        holder.txtScore.setTextColor(Color.parseColor("#22D3EE"))

        // Highlight khusus untuk player
        if (player.name == "Skor Anda") {
            holder.itemView.setBackgroundResource(R.drawable.rounded_item_yellow)
            holder.txtName.setTextColor(Color.BLACK)
            holder.txtScore.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int = list.size
}
