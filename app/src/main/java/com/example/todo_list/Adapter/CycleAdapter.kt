package com.example.todo_list.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.Activity.DetailActivity
import com.example.todo_list.CycleViewModel
import com.example.todo_list.data.CycleEntity
import com.example.todo_list.databinding.RecyclerviewCycleItemBinding

class CycleAdapter (val context: Context, val viewModel: CycleViewModel) : RecyclerView.Adapter<CycleAdapter.MyViewHolder>() {
    private lateinit var binding : RecyclerviewCycleItemBinding
    private var list : MutableList<CycleEntity> = mutableListOf()

    // Controller
    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v){
        init {
            binding.root.setOnClickListener(View.OnClickListener {
                var pos : Int = adapterPosition

                val builder = AlertDialog.Builder(context)
                builder.setTitle("다음 내용을 삭제하시겠습니까?").setMessage(list[pos].title)
                builder.setPositiveButton("확인") { _, _ ->
                    viewModel.delete(list[pos].id)
                }
                builder.setNegativeButton("취소") {_,_ ->

                }
                builder.show()
            })
        }

        fun bind(cycleEntity: CycleEntity){
            binding.cycleEntity = cycleEntity
        }
    }

    // 여기서 set 설정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: CycleEntity = list[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = RecyclerviewCycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: List<CycleEntity>){
        list?.let {
//            val diffCallback = DiffUtilCallBack(this.list, list)
//            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.list.run {
                clear()
                addAll(list)
                notifyDataSetChanged()
            }
        }
    }

}