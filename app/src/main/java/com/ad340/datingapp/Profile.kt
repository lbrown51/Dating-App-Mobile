package com.ad340.datingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile(val bundle: Bundle?) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewLayout = inflater.inflate(R.layout.fragment_profile, container, false)

        val nameAgeText = viewLayout.profile_name_age_text
        val occupationText = viewLayout.profile_occupation_text
        val descriptionText = viewLayout.profile_desc_text

        if (bundle != null) {
            val nameAgeStr = StringBuilder()
            if (bundle.containsKey(Constants.KEY_NAME)) {
                val name = bundle.getString(Constants.KEY_NAME)
                nameAgeStr.append(name)

                if (bundle.containsKey(Constants.KEY_AGE)) {
                    val age = bundle.getString(Constants.KEY_AGE)
                    nameAgeStr.append(", ")
                    nameAgeStr.append(age)
                }

                nameAgeText.text = nameAgeStr
            }

            val occupationStr = StringBuilder()
            if (bundle.containsKey(Constants.KEY_OCCUPATION)) {
                val occupation = bundle.getString(Constants.KEY_OCCUPATION)
                occupationStr.append(occupation)

                occupationText.text = occupationStr
            }

            val descriptionStr = StringBuilder()
            if (bundle.containsKey(Constants.KEY_DESCRIPTION)) {
                val description = bundle.getString(Constants.KEY_DESCRIPTION)
                descriptionStr.append(description)

                descriptionText.text = descriptionStr
            }
        }
        // Inflate the layout for this fragment
        return viewLayout
    }

}
