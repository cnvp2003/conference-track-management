package com.company.conference.logic

import com.company.conference.model.{Session, Talks}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by pati80 on 25/02/17.
  */

class SchedulerSpec extends FlatSpec with Matchers {

  "createTalks" should "return empty list" in {
    val talkList1 = List.empty
    Scheduler.createTalks(talkList1) shouldBe(mutable.ListBuffer())
  }

  it should "return list of talks" in {
    val talkList0 = List("Writing Fast Tests Against Enterprise Rails 60min",
      "Ruby Errors from Mismatched Gem Versions 45min")
    Scheduler.createTalks(talkList0) shouldBe(mutable.ListBuffer(Talks("Writing Fast Tests Against Enterprise Rails",60,null), Talks("Ruby Errors from Mismatched Gem Versions",45,null)))
  }

  "createSession" should "return tuple as with session and empty list of talks" in {
    val talkList1 = mutable.ListBuffer(Talks("Ruby Errors from Mismatched Gem Versions", 60, Some("09:00AM")))
    Scheduler.createSession(talkList1, "morning") shouldBe(mutable.ListBuffer(),Session(ListBuffer(Talks("Ruby Errors from Mismatched Gem Versions",60,Some("2017-03-25T09:00:00.000+05:30"))),"2017-03-25T09:00:00.000+05:30","2017-03-25T12:00:00.000+05:30"))
  }

  it should "return tuple as list of talks and session" in {
    val talkList2 = mutable.ListBuffer(Talks("Ruby Errors from Mismatched Gem Versions", 250, Some("")),
      Talks("Writing Fast Tests Against Enterprise Rails", 40, Some("01:00PM")))
    Scheduler.createSession(talkList2, "evening") shouldBe(ListBuffer(Talks("Ruby Errors from Mismatched Gem Versions",250,Some(""))),Session(ListBuffer(Talks("Writing Fast Tests Against Enterprise Rails",40,Some("2017-03-25T13:00:00.000+05:30"))),"2017-03-25T13:00:00.000+05:30","2017-03-25T17:00:00.000+05:30"))
  }


}