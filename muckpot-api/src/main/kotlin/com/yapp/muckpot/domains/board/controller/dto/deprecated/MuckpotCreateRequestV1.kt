package com.yapp.muckpot.domains.board.controller.dto.deprecated

import com.fasterxml.jackson.annotation.JsonFormat
import com.yapp.muckpot.common.Location
import com.yapp.muckpot.common.constants.AGE_MAX
import com.yapp.muckpot.common.constants.AGE_MIN
import com.yapp.muckpot.common.constants.APPLY_RANGE_INVALID
import com.yapp.muckpot.common.constants.CHAT_LINK_MAX
import com.yapp.muckpot.common.constants.CONTENT_MAX
import com.yapp.muckpot.common.constants.CONTENT_MAX_INVALID
import com.yapp.muckpot.common.constants.HHmm
import com.yapp.muckpot.common.constants.LINK_MAX_INVALID
import com.yapp.muckpot.common.constants.NOT_BLANK_COMMON
import com.yapp.muckpot.common.constants.TITLE_MAX
import com.yapp.muckpot.common.constants.TITLE_MAX_INVALID
import com.yapp.muckpot.common.constants.YYYYMMDD
import com.yapp.muckpot.domains.board.entity.Board
import com.yapp.muckpot.domains.user.entity.MuckPotUser
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Deprecated("V2 배포 후 제거")
@ApiModel(value = "먹팟 글 생성 V1")
data class MuckpotCreateRequestV1(
    @field:ApiModelProperty(notes = "만날 날짜", required = true, example = "2023-05-21")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYYMMDD)
    val meetingDate: LocalDate,
    @field:ApiModelProperty(notes = "만날 시간", required = true, example = "13:00")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = HHmm)
    val meetingTime: LocalTime,
    @field:ApiModelProperty(notes = "최대 참여 인원", required = true, example = "5")
    @field:Min(2, message = APPLY_RANGE_INVALID)
    val maxApply: Int = 2,
    @field:ApiModelProperty(notes = "최소 나이", required = false, example = "20")
    val minAge: Int? = null,
    @field:ApiModelProperty(notes = "최대 나이", required = false, example = "100")
    val maxAge: Int? = null,
    @field:ApiModelProperty(notes = "주소", required = true, example = "서울 성북구 안암동5가 104-30 캐치카페 안암")
    var locationName: String,
    @field:ApiModelProperty(notes = "주소 상세", required = false, example = "6층")
    var locationDetail: String? = null,
    @field:ApiModelProperty(notes = "도로명(지번) 주소", required = true, example = "경기 용인시 기흥구 구성로 102")
    var addressName: String? = null,
    @field:ApiModelProperty(notes = "x 좌표", required = true, example = "127.02970799701643")
    val x: Double,
    @field:ApiModelProperty(notes = "y 좌표", required = true, example = "37.58392327180857")
    val y: Double,
    @field:ApiModelProperty(notes = "제목", required = true, example = "같이 밥묵으실분")
    @field:Length(max = TITLE_MAX, message = TITLE_MAX_INVALID)
    @field:NotBlank(message = NOT_BLANK_COMMON)
    var title: String,
    @field:ApiModelProperty(notes = "내용", required = false, example = "내용 입니다.")
    @field:Length(max = CONTENT_MAX, message = CONTENT_MAX_INVALID)
    var content: String? = null,
    @field:ApiModelProperty(notes = "오픈채팅방 링크", required = true, example = "https://open.kakao.com/o/gSIkvvHc")
    @field:Length(max = CHAT_LINK_MAX, message = LINK_MAX_INVALID)
    @field:NotBlank(message = NOT_BLANK_COMMON)
    var chatLink: String
) {
    init {
        title = title.trim()
        content = content?.trim()
        chatLink = chatLink.trim()
        locationDetail = locationDetail?.trim()
    }

    fun toBoard(user: MuckPotUser): Board {
        return Board(
            user = user,
            title = title,
            content = content,
            location = Location(locationName = locationName, addressName = addressName, x, y),
            locationDetail = locationDetail,
            meetingTime = LocalDateTime.of(meetingDate, meetingTime),
            minAge = minAge ?: AGE_MIN,
            maxAge = maxAge ?: AGE_MAX,
            maxApply = maxApply,
            chatLink = chatLink,
            currentApply = 1
        )
    }
}
