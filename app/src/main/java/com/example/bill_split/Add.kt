@file:Suppress("DEPRECATION")

package com.example.bill_split

import android.content.Context
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Add.newInstance] factory method to
 * create an instance of this fragment.
 */
class Add : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        // Inflate the layout for this fragment

        val desc = view.findViewById<TextInputEditText>(R.id.et_DescriptionOfBill_ab)
        val TotalAmt = view.findViewById<TextInputEditText>(R.id.et_TotalAmt_ab)
        val Persons = view.findViewById<TextInputEditText>(R.id.et_Persons_ab)
        val SplitAmt = view.findViewById<Button>(R.id.Btn_PerPersonAmt_ab)
        val Spinner_user = view.findViewById<Spinner>(R.id.spacer_add)
        val btn_add_list = view.findViewById<Button>(R.id.btn_add_Ad)
        val DBHELPER = DBHelper(requireActivity())

        var username_list: Array<String> = arrayOf("")
        var selected_username_list: Array<String> = arrayOf("")

        val df = DecimalFormat("#.##")

        val btn_split = view.findViewById<Button>(R.id.btn_Split_sp)
        val sharedPreferences = activity?.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", "")

        var l = 1

        val data_UN = DBHELPER.alldata
        while (data_UN.moveToNext()) {
            val row = data_UN.getString(5)
            if (row.toString() != username) {
                username_list += row
            }
        }

        val adp =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, username_list)
        Spinner_user.adapter = adp

//        NoofPer.setText(username_list.toString())

        btn_add_list.setOnClickListener {
            if (Spinner_user.selectedItem == "") {
                Toast.makeText(activity, "Empty", Toast.LENGTH_SHORT).show()
            } else {
                selected_username_list += Spinner_user.selectedItem.toString()
                var str: String = ""
                l = 1
                for (i in selected_username_list) {
                    if (i != "") {
                        str += "$i "
                        l++
                    }
                }
                Toast.makeText(activity, l.toString(), Toast.LENGTH_SHORT).show()
                Persons.setText(str)
                Spinner_user.setSelection(0)
            }
        }


        SplitAmt.setOnClickListener {

            if (TotalAmt.text.toString() == "") {
                Toast.makeText(activity, "PLEASE ENTER TOTAL AMOUNT", Toast.LENGTH_SHORT).show()
            } else if (Persons.text.toString() == "") {
                Toast.makeText(activity, "PLEASE ENTER USERNAME", Toast.LENGTH_SHORT).show()
            } else {
                val totamt = TotalAmt.text.toString().toDouble()
                val spa = df.format(totamt / l)
                Toast.makeText(activity, spa, Toast.LENGTH_SHORT).show()
            }

        }
        btn_split.setOnClickListener {

            if (desc.text.toString() == "") {
                Toast.makeText(activity, "PLEASE ENTER DESCRIPTION", Toast.LENGTH_SHORT).show()
            } else if (TotalAmt.text.toString() == "") {
                Toast.makeText(activity, "PLEASE ENTER TOTAL AMOUNT", Toast.LENGTH_SHORT).show()
            } else if (Persons.text.toString() == "") {
                Toast.makeText(activity, "PLEASE ENTER USERNAME", Toast.LENGTH_SHORT).show()
            } else {

                val totamt = TotalAmt.text.toString().toDouble()
                val spa = df.format(totamt / l)

                val res = DBHELPER.insertSplit(
                    desc.text.toString(),
                    TotalAmt.text.toString(),
                    l.toString(),
                    spa.toString(),
                    username.toString(),
                    Persons.text.toString()
                )
                if (res) {
                    val transaction: FragmentTransaction =
                        requireFragmentManager().beginTransaction()
                    transaction.replace(R.id.frameLayout, Home())
                    transaction.commit()
                }
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Add.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = Add().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}
