package com.appninjas.recruiterheaven.presentation.adapter.model

import com.appninjas.domain.model.Applicant

data class ApplicantParentItem(val title: String,
                               val applicantsList: List<Applicant>,
                               var isExpanded: Boolean = false)
