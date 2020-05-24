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

        nameAgeText.text = getNameAgeStr(bundle)
        occupationText.text = getOccupationStr(bundle)
        descriptionText.text = getDescriptionStr(bundle)

        // Inflate the layout for this fragment
        return viewLayout
    }

    internal fun getNameAgeStr(bundle: Bundle?): CharSequence? {
        return if (bundle != null &&
            bundle.containsKey(Constants.KEY_NAME) &&
            bundle.containsKey(Constants.KEY_AGE)) {
            val nameAgeStr = StringBuilder()

            val name = bundle.getString(Constants.KEY_NAME)
            nameAgeStr.append(name)


            val age = bundle.getString(Constants.KEY_AGE)
            nameAgeStr.append(", ")
            nameAgeStr.append(age)

            nameAgeStr

        } else {
            "Error, could not get name age string"
        }
    }

    internal fun getOccupationStr(bundle: Bundle?): CharSequence? {
        return if (bundle != null &&
            bundle.containsKey(Constants.KEY_OCCUPATION)) {
            val occupationStr = StringBuilder()
            val occupation = bundle.getString(Constants.KEY_OCCUPATION)
            occupationStr.append(occupation)

            occupationStr
        } else {
            "Error, could not get occupation string"
        }
    }

    internal fun getDescriptionStr(bundle: Bundle?): CharSequence? {
        return if (bundle != null &&
            bundle.containsKey(Constants.KEY_DESCRIPTION)) {
            val descriptionStr = StringBuilder()
            val description = bundle.getString(Constants.KEY_DESCRIPTION)
            descriptionStr.append(description)

            descriptionStr
        } else {
            "Error, could not get description string"
        }
    }
}
