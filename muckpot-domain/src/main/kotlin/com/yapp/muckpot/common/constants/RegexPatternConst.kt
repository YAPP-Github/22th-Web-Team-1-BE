package com.yapp.muckpot.common.constants

const val EMAIL = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"
const val PW_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d).{8,20}\$"

@Deprecated("V2 배포 후 제거")
const val ONLY_NAVER = "^[A-Za-z0-9._%+-]+@naver\\.com\$"
const val ONLY_SAMSUNG = "^[A-Za-z0-9._%+-]+@samsung\\.com\$"
const val YYYYMMDD = "yyyy-MM-dd"
const val HHmm = "HH:mm"

const val KR_MM_DD_E = "MM월 dd일 (E)"
const val KR_YYYY_MM_DD_E = "YYYY년 MM월 dd일 (E)"
const val KR_YYYY_MM_DD = "YYYY년 MM월 dd일"
const val a_hhmm = "a hh:mm"
