package com.yapp.muckpot.domains.board.controller.dto

import com.yapp.muckpot.common.TimeUtil
import com.yapp.muckpot.common.constants.KR_YYYY_MM_DD
import com.yapp.muckpot.common.constants.KR_YYYY_MM_DD_E
import com.yapp.muckpot.common.constants.a_hhmm
import com.yapp.muckpot.domains.board.dto.ParticipantReadResponse
import com.yapp.muckpot.domains.board.entity.Board
import com.yapp.muckpot.domains.user.controller.dto.UserResponse

data class MuckpotDetailResponse(
    val boardId: Long,
    val prevId: Long?,
    val nextId: Long?,
    val title: String,
    val content: String? = null,
    val chatLink: String,
    val status: String,
    val meetingDate: String,
    val meetingTime: String,
    val createDate: String,
    val maxApply: Int,
    val currentApply: Int,
    var minAge: Int? = null,
    var maxAge: Int? = null,
    val locationName: String,
    val addressName: String,
    val x: Double,
    val y: Double,
    val locationDetail: String? = null,
    val views: Int,
    val userAge: Int?,
    var participants: List<ParticipantReadResponse>,
    val isOutOfDate: Boolean
) {
    init {
        if (participants.isNotEmpty()) {
            participants.first().writer = true
        }
    }

    fun sortParticipantsByLoginUser(loginUserInfo: UserResponse?) {
        if (participants.size > 1) {
            loginUserInfo?.let { userInfo ->
                val mutableParticipants = participants.toMutableList()
                val myParticipantIndex = mutableParticipants.indexOfFirst { it.userId == userInfo.userId }
                if (myParticipantIndex != -1) {
                    val participant = mutableParticipants.removeAt(myParticipantIndex)
                    participants = listOf(participant) + mutableParticipants
                }
            }
        }
    }

    private fun changeIsNotAgeLimit() {
        this.minAge = null
        this.maxAge = null
    }

    companion object {
        fun of(
            board: Board,
            participants: List<ParticipantReadResponse>,
            prevId: Long? = null,
            nextId: Long? = null,
            userAge: Int? = null
        ): MuckpotDetailResponse {
            val response = MuckpotDetailResponse(
                boardId = board.id ?: 0,
                prevId = prevId,
                nextId = nextId,
                title = board.title,
                content = board.content,
                chatLink = board.chatLink,
                status = board.status.korNm,
                meetingDate = TimeUtil.localeKoreanFormatting(board.meetingTime, KR_YYYY_MM_DD_E),
                meetingTime = TimeUtil.localeKoreanFormatting(board.meetingTime, a_hhmm),
                createDate = TimeUtil.localeKoreanFormatting(board.createdAt, KR_YYYY_MM_DD),
                maxApply = board.maxApply,
                currentApply = board.currentApply,
                minAge = board.minAge,
                maxAge = board.maxAge,
                locationName = board.location.locationName,
                addressName = board.location.addressName,
                x = board.getX(),
                y = board.getY(),
                locationDetail = board.locationDetail,
                views = board.views,
                userAge = userAge,
                participants = participants,
                isOutOfDate = board.isOutOfDate()
            )
            if (board.isNotAgeLimit()) {
                response.changeIsNotAgeLimit()
            }
            return response
        }
    }
}
