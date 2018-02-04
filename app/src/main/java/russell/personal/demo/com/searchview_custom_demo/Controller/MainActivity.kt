package russell.personal.demo.com.searchview_custom_demo.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import russell.personal.demo.com.searchview_custom_demo.Adapter.ProjectAdapter
import russell.personal.demo.com.searchview_custom_demo.Model.ProjectModel
import russell.personal.demo.com.searchview_custom_demo.R
import russell.personal.demo.com.searchview_custom_demo.Service.DataService

class MainActivity : AppCompatActivity() {

    lateinit var mListView : ListView
    lateinit var mSearchView : SearchView
    lateinit var adapter : ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        processView()
        processController()

    }

    fun processController(){
        adapter = ProjectAdapter(this,DataService.myDataList)
        mListView.adapter = adapter

        // Set SearchView method
        mSearchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        // Set Click Row Method
        mListView.setOnItemClickListener { parent, view, position, id ->
            val selectItem : ProjectModel = adapter.getItem(position) as ProjectModel
            Toast.makeText(this,"${selectItem.projectName}",Toast.LENGTH_SHORT).show()
        }
    }

    fun processView(){
        mListView = findViewById(R.id.listView)
        mSearchView = findViewById(R.id.searchView)
    }
}
