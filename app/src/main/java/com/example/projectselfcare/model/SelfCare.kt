package com.example.projectselfcare.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SelfCare(
    @StringRes val selfCareTip: Int,
    @StringRes val dayTip: Int,
    @DrawableRes val selfCareImageSource: Int,
    @StringRes val selfCareExplanation: Int
)

