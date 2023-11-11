package com.example.bill_split.view
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.bill_split.R
import com.example.bill_split.model.Sb_model

internal class Sb_Adapter(

    private val Billlist: List<Sb_model>,
    private val context: Context
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var DescriptionTV: TextView
    private lateinit var UserNameTV: TextView
    private lateinit var SpAmtTV: TextView
    private lateinit var BillIV: ImageView

    override fun getCount(): Int {
        return Billlist.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    @Suppress("NAME_SHADOWING")
    override fun getView(position: Int, BillView: View?, parent: ViewGroup?): View {
        var BillView = BillView
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (BillView == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            BillView = layoutInflater!!.inflate(R.layout.listmodel_for_sb, null)
        }
        // on below line we are initializing our course image view
        // and course text view with their ids.
        BillIV = BillView!!.findViewById(R.id.idIV_sb)
        DescriptionTV = BillView.findViewById(R.id.idTVDescription_sb)
        UserNameTV =  BillView.findViewById(R.id.idTVPerson_sb)
        SpAmtTV =  BillView.findViewById(R.id.idTVsplitAmt_sb)

        // on below line we are setting image for our course image view.
        BillIV.setImageResource(Billlist[position].Img)
        // on below line we are setting text in our course text view.
        DescriptionTV.text = Billlist[position].Description
        UserNameTV.text = Billlist[position].Username
        SpAmtTV.text = Billlist[position].SplitAmt
        // at last we are returning our convert view.
        return BillView
    }
}
