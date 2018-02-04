package russell.personal.demo.com.searchview_custom_demo.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import russell.personal.demo.com.searchview_custom_demo.Model.ProjectModel
import russell.personal.demo.com.searchview_custom_demo.R
import russell.personal.demo.com.searchview_custom_demo.Service.DataService

/**
 * Created by Vanilla on 03/02/2018.
 */
 class ProjectAdapter(val context: Context, val myDataList : ArrayList<ProjectModel>) : BaseAdapter(),Filterable {

    private var list : ArrayList<ProjectModel>? = myDataList
    private var listFull : ArrayList<ProjectModel>? = myDataList
    private var baseFilter : BaseFilter? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val customView : View
        val holder : ViewHolder

        if (convertView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.custom_row_item,null)
            holder = ViewHolder()

            holder.projectName = customView.findViewById(R.id.pnTextView)
            holder.clientName = customView.findViewById(R.id.cnTextView)

            customView.tag = holder
        }
        else{

            holder = convertView.tag as ViewHolder
            customView = convertView
        }

        holder.projectName?.text = "Project Name : ${list?.get(position)?.projectName}"
        holder.clientName?.text = "Client Name: ${list?.get(position)?.clientName}"

        return customView
    }

    override fun getItem(position: Int): Any {
        return list?.get(position) as ProjectModel
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list?.size as Int
    }

    // Reuse Row View
    private class ViewHolder{
        var projectName : TextView? = null
        var clientName : TextView? = null
    }

    // BaseAdapter extent Filterable Method
    override fun getFilter(): Filter {
        if (baseFilter == null) {
            baseFilter = BaseFilter()
        }
        return baseFilter as BaseFilter
    }

    // BaseAdapter Filter implement Methods
    inner class BaseFilter : Filter(){
        // Check and compare input info with list data
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var results : FilterResults = FilterResults()
            if (constraint != null && constraint.length > 0) {
                var localList : ArrayList<ProjectModel> = ArrayList<ProjectModel>()
                for (index in 0 until listFull?.size as Int){
                    if (listFull?.get(index)?.projectName?.contains(constraint.toString())as Boolean){
                        localList.add(listFull?.get(index) as ProjectModel)
                    }
                }
                results.values = localList
                results.count = localList.size
            }
            else{
                results.values = listFull
                results.count = listFull?.size as Int
            }
            return results
        }
        // Present search result and rebuild listView
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            list = results?.values as ArrayList<ProjectModel>

            notifyDataSetChanged()
        }
    }
}