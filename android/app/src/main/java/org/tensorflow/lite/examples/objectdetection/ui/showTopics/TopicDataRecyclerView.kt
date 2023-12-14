package org.tensorflow.lite.examples.objectdetection.ui.showTopics

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.data.Item
import org.tensorflow.lite.examples.objectdetection.ui.editPhoto.MakePhoto
import org.tensorflow.lite.examples.objectdetection.ui.showPhotos.ShowPhotos

class TopicDataRecyclerView(private var items: List<Item>) : RecyclerView.Adapter<TopicDataRecyclerView.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicID: TextView = view.findViewById(R.id.item_topicID)
        val title: TextView = view.findViewById(R.id.item_topic)
        val count: TextView = view.findViewById(R.id.item_count)
        val showPhotos: LinearLayout = view.findViewById(R.id.show_photos)
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
        holder.showPhotos.setOnClickListener(){
            // 새 액티비티로 이동
            val context = holder.itemView.context // Context 얻기
            val intent = Intent(context, ShowPhotos::class.java)
            intent.putExtra("topicID", item.id) // topicID 값을 Intent에 넣음
            intent.putExtra("topic", item.topic) // topicID 값을 Intent에 넣음
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = items.size
    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
}
