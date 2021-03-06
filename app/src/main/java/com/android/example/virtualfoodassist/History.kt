package com.android.example.virtualfoodassist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.example.virtualfoodassist.DB.DBHandler

class History : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.history, container, false)

        setupActionBar()

        val txtShowNote = view.findViewById<TextView>(R.id.txtShowNotes)
        val data = DBHandler(this.context!!).readData()
        txtShowNote.text = ""
        for (i in 0 until data.size) {
            txtShowNote.append(data[i].name + "\n")
        }
         txtShowNote.textSize = 30F

        val btnclear = view.findViewById<Button>(R.id.btn_clear_history)
        btnclear.setOnClickListener {
            doubleCheckBuilder()
        }
        return view
    }

    private fun doubleCheckBuilder() {
        val txtShowNote = view!!.findViewById<TextView>(R.id.txtShowNotes)
        //var data = DBHandler(this.context!!).readData()
        val contxt = this.context
        val builder = AlertDialog.Builder(contxt!!)
        builder.setTitle(R.string.clear_history)
        builder.setMessage(R.string.clear_history_scanned)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            DBHandler(this.context!!).deleteData()
            txtShowNote.text = ""
        }
        builder.setNegativeButton(R.string.no) { dialog, _ ->
            dialog.cancel()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).setTitle(R.string.history)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home
            -> {
                val i = Intent(activity, MainActivity::class.java)
                startActivity(i)
                (activity as AppCompatActivity).setTitle(R.string.app_name)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}