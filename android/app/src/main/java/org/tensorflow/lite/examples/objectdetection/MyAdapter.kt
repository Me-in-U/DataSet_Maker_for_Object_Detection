package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.objectdetection.data.Item

class MyAdapter(private var items: List<Item>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicID: TextView = view.findViewById(R.id.item_topicID)
        val title: TextView = view.findViewById(R.id.item_topic)
        val count: TextView = view.findViewById(R.id.item_count)
        val editButton: Button = view.findViewById(R.id.item_button_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.topicID.text = item.id.toString()
        holder.title.text = item.topic
        holder.count.text = item.count.toString()
        holder.editButton.setOnClickListener {
            // 새 액티비티로 이동
            val context = holder.itemView.context // Context 얻기
            val intent = Intent(context, MakePhoto::class.java)
            intent.putExtra("topicID", item.id) // topicID 값을 Intent에 넣음
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = items.size
    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
}
