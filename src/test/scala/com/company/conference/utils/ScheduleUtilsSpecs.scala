package com.company.conference.utils

import com.company.conference.model.Talks
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable

/**
  * Created by pati80 on 25/02/17.
  */

class ScheduleUtilsSpecs extends FlatSpec with Matchers {

  "getSchedule" should "return empty list" in {
    val conferenceDateTime = new DateTime(2017, 3, 25, 9, 0, 0, 0)
    val talkList0 = List(Talks("Ruby Errors from Mismatched Gem Versions", 40, Some(conferenceDateTime.toString)),
      Talks("Writing Fast Tests Against Enterprise Rails", 50, Some(conferenceDateTime.plusMinutes(40).toString)))
    ScheduleUtils.getSchedule(talkList0) shouldBe Some("2017-03-25T10:30:00.000+05:30")
  }

  "checkTalkDuration" should "return false for talk duration less than session" in {
    val talkList1 = List(Talks("Ruby Errors from Mismatched Gem Versions", 450, Some("09:00AM")),
      Talks("Writing Fast Tests Against Enterprise Rails", 40, Some("01:00PM")))
    ScheduleUtils.checkTalkDuration(talkList1, 45, 180) shouldBe false
  }

  it should "return true for talk duration less than session" in {
    val talkList2 = List(Talks("Ruby Errors from Mismatched Gem Versions", 50, Some("09:00AM")),
      Talks("Writing Fast Tests Against Enterprise Rails", 40, Some("01:00PM")))
    ScheduleUtils.checkTalkDuration(talkList2, 45, 180) shouldBe true
  }

  "checkSessionDuration" should "return false where as talks duration fits in session" in {
    val talkList3 = List(Talks("Ruby Errors from Mismatched Gem Versions", 50, Some("09:00AM")),
      Talks("Writing Fast Tests Against Enterprise Rails", 40, Some("01:00PM")))
    ScheduleUtils.checkSessionDuration(talkList3, 80) shouldBe false
  }

  it should "return true where as talks duration fits in session" in {
    val talkList3 = List(Talks("Ruby Errors from Mismatched Gem Versions", 50, Some("09:00AM")),
      Talks("Writing Fast Tests Against Enterprise Rails", 40, Some("01:00PM")))
    ScheduleUtils.checkSessionDuration(talkList3, 100) shouldBe true
  }

}