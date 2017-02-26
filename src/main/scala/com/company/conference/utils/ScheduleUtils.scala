package com.company.conference.utils

import com.company.conference.model.Talks
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

/**
  * Created by pati80 on 25/02/17.
  */
object ScheduleUtils {

  val format: DateTimeFormatter = DateTimeFormat.shortTime()

  def getSchedule(talksList:List[Talks]): Option[String] ={
    val lastSchedule = talksList.last.scheduleTime.get
    val lastDuration = talksList.last.duration

    val lastScheduleDateTime = DateTime.parse(lastSchedule)
    val newSchedule: DateTime = lastScheduleDateTime.plusMinutes(lastDuration)

    Some(newSchedule.toString)
  }

  /** check wheather talks duration fits in session or not*/
  def checkTalkDuration(talksList:List[Talks], duration:Int, sessionDuration: Int):Boolean={
    val remaingDuration = sessionDuration - talksList.map(_.duration).sum
    println(s"REE ${remaingDuration} sessionDuration ${sessionDuration} list ${talksList.map(_.duration).sum}")
    if(duration <= remaingDuration) true else false
  }

  def checkSessionDuration(talksList:List[Talks], sessionDuration: Int):Boolean= if(talksList.map(_.duration).sum <= sessionDuration) true else false

}