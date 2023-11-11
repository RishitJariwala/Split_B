package com.example.bill_split

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Account.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class Account : Fragment() {
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

    @SuppressLint("ApplySharedPref")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val userName1 = view?.findViewById<TextView>(R.id.txt_UserName_ac)
        val fname1 = view?.findViewById<TextInputEditText>(R.id.et_Fname_Ac)
        val lname1 = view?.findViewById<TextInputEditText>(R.id.et_Lname_Ac)
        val email1 = view?.findViewById<TextInputEditText>(R.id.et_email_Ac)
        val contact1 = view?.findViewById<TextInputEditText>(R.id.et_Contect_Ac)
        val changepass = view?.findViewById<TextView>(R.id.TV_changepass_AC)
        val deleteAcc = view?.findViewById<TextView>(R.id.TV_DeleteAcc_AC)

        val v = LayoutInflater.from(requireActivity()).inflate(R.layout.add_item_userside, null)

        val fname = v.findViewById<TextInputEditText>(R.id.et_Fname_AI)
        val lname = v.findViewById<TextInputEditText>(R.id.et_Lname_AI)
        val cno = v.findViewById<TextInputEditText>(R.id.et_Contect_AI)
        val email = v.findViewById<TextInputEditText>(R.id.et_email_AI)
        val uname = v.findViewById<TextInputEditText>(R.id.et_username_AI)

        val v1 = LayoutInflater.from(requireActivity()).inflate(R.layout.add_item_changepass, null)

        val oldpass = v1.findViewById<TextInputEditText>(R.id.et_OldPassword_CP)
        val newpass = v1.findViewById<TextInputEditText>(R.id.et_NewPassword_CP)
        val conpass = v1.findViewById<TextInputEditText>(R.id.et_ConfPassword_CP)

        val DBHELPER = DBHelper(requireActivity())

        val btn_edit = view?.findViewById<ImageView>(R.id.btn_edit_icon_Ac)
        val btn_Logout = view?.findViewById<Button>(R.id.btn_Logout_Ac)

        val sharedPreferences = activity?.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val usrname = sharedPreferences?.getString("username", null)
        val passWord = sharedPreferences?.getString("password", null)

        val res = DBHELPER.allAccountdata(usrname.toString())

        while (res.moveToNext()) {
            fname1?.setText(res.getString(1))
            lname1?.setText(res.getString(2))
            contact1?.setText(res.getString(3))
            email1?.setText(res.getString(4))
            userName1?.text = res.getString(5)


            fname.setText(res.getString(1))
            lname.setText(res.getString(2))
            cno.setText(res.getString(3))
            email.setText(res.getString(4))
            uname.setText(res.getString(5))
            break
        }

        changepass?.setOnClickListener {


            AlertDialog.Builder(requireActivity()).setView(v1)
                .setPositiveButton("Ok") { _, _ ->

                    if (oldpass.text.toString().isEmpty()) {
                        Toast.makeText(
                            requireActivity(), "Please Enter Old Password", Toast.LENGTH_SHORT
                        ).show()
                    } else if (newpass.text.toString().isEmpty()) {
                        Toast.makeText(
                            requireActivity(), "Please Enter New Password", Toast.LENGTH_SHORT
                        ).show()
                    } else if (conpass.text.toString().isEmpty()) {
                        Toast.makeText(
                            requireActivity(), "Please Enter Confirm Password", Toast.LENGTH_SHORT
                        ).show()
                    } else if (oldpass.text.toString() != passWord) {
                        Toast.makeText(
                            requireActivity(), "Old Password Does Not Match", Toast.LENGTH_SHORT
                        ).show()
                    } else if (newpass.text.toString() != conpass.text.toString()) {
                        Toast.makeText(
                            requireActivity(),
                            "New Password and Confirm Password Does Not Match",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val res1 = DBHELPER.pass_check(usrname, oldpass.text.toString())
                        if (res1.count > 0) {
                            val res2 = DBHELPER.Update_cp(usrname, newpass.text.toString())
                            if (res2) {
                                val transaction: FragmentTransaction =
                                    requireFragmentManager().beginTransaction()
                                transaction.replace(R.id.frameLayout, Account())
                                transaction.commit()
                            }
                        }
                    }
                }.setNegativeButton("cancle") { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }

        btn_edit?.setOnClickListener {


            AlertDialog.Builder(requireActivity()).setView(v).setPositiveButton("Ok") { _, _ ->
                if (fname.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireActivity(), "Please Enter First Name", Toast.LENGTH_SHORT
                    ).show()
                } else if (lname.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireActivity(), "Please Enter Last Name", Toast.LENGTH_SHORT
                    ).show()
                } else if (cno.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireActivity(), "Please Enter Contact Number", Toast.LENGTH_SHORT
                    ).show()
                } else if (email.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireActivity(), "Please Enter Email ID", Toast.LENGTH_SHORT
                    ).show()
                } else if (!isVatidEmait(email.text.toString())) {
                    Toast.makeText(
                        requireActivity(), "Please Enter Valid Email ID", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val data = DBHELPER.Update_Ac(
                        fname?.text.toString(),
                        lname?.text.toString(),
                        cno?.text.toString(),
                        email?.text.toString(),
                        usrname
                    )

                    if (data) {
//                            Toast.makeText(activity, "UPDATED", Toast.LENGTH_SHORT).show()
                        val transaction: FragmentTransaction =
                            requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.frameLayout, Account())
                        transaction.commit()
                    }
                }
            }.setNegativeButton("cancle") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
        }
        deleteAcc?.setOnClickListener {

            AlertDialog.Builder(requireActivity()).setTitle("Delete").setIcon(R.drawable.ic_warning)
                .setMessage("Are you sure you want to Delete this Account")
                .setPositiveButton("Yes") { dialog, _ ->
                    val data = DBHELPER.Delete_Ac(usrname.toString())

                    if (data) {
                        val editor = sharedPreferences!!.edit()
                        editor?.remove("username")
                        editor?.remove("password")
                        editor.commit()
                        val i = Intent(activity, LOGIN::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(activity, "NOT DELETED", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }.create().show()

        }
        btn_Logout?.setOnClickListener {
            val editor = sharedPreferences!!.edit()
            editor?.remove("username")
            editor?.remove("password")
            editor.apply()
            val i = Intent(activity, LOGIN::class.java)
            startActivity(i)
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun isVatidEmait(email: String): Boolean {
        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Account.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = Account().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}