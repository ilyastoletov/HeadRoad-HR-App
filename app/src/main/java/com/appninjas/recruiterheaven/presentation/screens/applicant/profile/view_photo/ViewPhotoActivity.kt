package com.appninjas.recruiterheaven.presentation.screens.applicant.profile.view_photo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appninjas.recruiterheaven.databinding.ActivityViewPhotoBinding
import com.squareup.picasso.Picasso

class ViewPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        val extrasBundle = intent.extras!!
        val imageUrl = extrasBundle.getString("imageUrl")
        Picasso.get().load(imageUrl).into(binding.applicantProfilePhotoResizeble)

        binding.closeResizingActivityButton.setOnClickListener {
            finish()
        }
    }

}