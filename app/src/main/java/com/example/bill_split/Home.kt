@file:Suppress("DEPRECATION")

package com.example.bill_split

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.example.bill_split.model.Sb_model
import com.example.bill_split.view.Sb_Adapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
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

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val DBHELPER = DBHelper(requireActivity())

        val GRV : GridView = view?.findViewById(R.id.idGRV_home)!!
        val TV1 : TextView = view.findViewById(R.id.DescriptionID)!!
        val TV2 : TextView = view.findViewById(R.id.User_NameID)!!
        val TV3 : TextView = view.findViewById(R.id.Owes_YouID)!!
        val tv : TextView = view.findViewById(R.id.tv_Msg_hone)!!
        val YouLent : TextView = view.findViewById(R.id.TV_YouLent)!!
        val YouBorrowed : TextView = view.findViewById(R.id.TV_YouBorrowed)!!

        val BillView = layoutInflater.inflate(R.layout.listmodel_for_sb, null)

//        val SpAmtTV : TextView =  BillView.findViewById(R.id.idTVsplitAmt_sb)


        val sharedPreferences = activity?.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val usrname = sharedPreferences?.getString("username", "")


        var billList: List<Sb_model>
        billList = ArrayList()

        DBHELPER.split_check()
        val res1 = DBHELPER.split_check_owes(usrname.toString())

        TV3.text = "Owes You"

        if (res1.count>0){
            TV1.isVisible = true
            TV2.isVisible = true
            TV3.isVisible = true
            tv.alpha = 0.0f
        }
        else{
            TV1.alpha = 0.0f
            TV2.alpha = 0.0f
            TV3.alpha = 0.0f
            tv.isVisible = true
        }
        YouLent.setTextColor(Color.GRAY)
        while (res1.moveToNext()) {
            val row1 = res1.getString(1)
            val row2 = res1.getString(6)
            val row3 = res1.getString(4)

            billList = billList + Sb_model(row1,row2,row3,R.drawable.baseline_currency_rupee_24)
        }
        val courseAdapter = Sb_Adapter(Billlist = billList, requireContext())
        GRV.adapter = courseAdapter
        GRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                activity,  "Splited Amount Is ₹"+billList[position].SplitAmt+"/-" ,
                Toast.LENGTH_SHORT
            ).show()
        }


        YouLent.setOnClickListener {
//            billList = emptyList()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout, Home())
            transaction.commit()

        }

        YouBorrowed.setOnClickListener {

            YouBorrowed.setTextColor(Color.GRAY)
            YouLent.setTextColor(Color.BLACK)
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout, Home1())
            transaction.commit()
        }
        // Inflate the layout for this fragment
        return view
    }
//
//    @SuppressLint("SetTextI18n")
//    private fun datago(view: View, usersNames: String?) {
//        val DBHELPER = DBHelper(requireActivity())
//        val GRV : GridView = view.findViewById(R.id.idGRV_home)!!
//        val TV1 : TextView = view.findViewById(R.id.DescriptionID)!!
//        val TV2 : TextView = view.findViewById(R.id.User_NameID)!!
//        val TV3 : TextView = view.findViewById(R.id.Owes_YouID)!!
//        val tv : TextView = view.findViewById(R.id.tv_Msg_hone)!!
//
//        val  res2 = DBHELPER.split_check_borrowed(usersNames)
//
//
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}