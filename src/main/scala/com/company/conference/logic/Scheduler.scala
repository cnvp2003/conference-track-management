package com.company.conference.logic

import com.company.conference.utils.ScheduleUtils
import com.company.conference.model.{Conference, Session, Talks, Track}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by pati80 on 25/02/17.
  */

object Scheduler {

  /**
    * 1. Read data from file and create a list of String.
    * 2. make list of all talks
    * 3. create session (morning session total time 180 mins and eve. 240min max)
    * 4. assign schedule to talks
    * 5. combine session as track
    * 
    * */

  val pattern = "\\d\\dmin$".r
  val minPattern = "min"
  val lightningPattern = "lightning"

  var time = 0
  val morningSesssion   = 180
  val afternoonSesssion = 240

  var talksList = mutable.ListBuffer[Talks]()
  var trackList = mutable.ListBuffer[Track]()
  var sessionList = mutable.ListBuffer[Session]()
  var morningSessionTalksList = mutable.ListBuffer[Talks]()
  var afternoonSessionTalksList = mutable.ListBuffer[Talks]()

  val conferenceDateTime = new DateTime(2017, 3, 25, 9, 0, 0, 0)
  val lunchTime = new DateTime(2017, 3, 25, 12, 0, 0, 0)

  val format = DateTimeFormat.shortTime()


  def createConference(listOfTalks: List[String]):Unit ={

    createTalks(listOfTalks)

    val morningTalks = createSession(talksList, "morning")
    val noonTalks    = createSession(morningTalks._1, "evening")

    val lunch = ListBuffer(Talks("Writing Fast Tests Against Enterprise Rails", 60, Some(lunchTime.toString)))
    val lunchSession  = Session(lunch,lunchTime.toString, lunchTime.plusMinutes(60).toString)

    sessionList += morningTalks._2
    sessionList += lunchSession
    sessionList += noonTalks._2

    trackList += Track(sessionList, conferenceDateTime.toString)
    val conference = Conference(trackList,conferenceDateTime.toString)

    output(conference)
  }


  def createTalks(listOfTalks: List[String]):mutable.ListBuffer[Talks]={
    for(talk <- listOfTalks){

      val lastSpaceIndex = talk.lastIndexOf(" ")
      val name = talk.substring(0, lastSpaceIndex)
      val timeStr = talk.substring(lastSpaceIndex + 1)

      if(timeStr.endsWith(minPattern)) {
        val str = timeStr.indexOf(minPattern)
        time = timeStr.substring(0, str).toInt

        talksList += Talks(name,time)
      }else if(timeStr.endsWith(lightningPattern)) {
        time = 5
        talksList += Talks(name,time)
      }
    }
    talksList
  }

  def createSession(talksList: mutable.ListBuffer[Talks], session:String):(mutable.ListBuffer[Talks], Session)={
    var list = morningSessionTalksList
    var startDateTime = conferenceDateTime
    var endDateTime = startDateTime.plusMinutes(180)
    var sessionDuration = morningSesssion

    if (session != "morning") {
      list = afternoonSessionTalksList
      sessionDuration = afternoonSesssion
      startDateTime = conferenceDateTime.plusMinutes(240)
      endDateTime = startDateTime.plusMinutes(240)
    }

    for(tk <- talksList) {
      if(list.isEmpty){
        if(ScheduleUtils.checkTalkDuration(list.toList,tk.duration,sessionDuration)) {
          list += Talks(tk.title, tk.duration, Some(startDateTime.toString))
          talksList -= tk
        }
      }else{
        if(ScheduleUtils.checkSessionDuration(list.toList,sessionDuration)){
          if(ScheduleUtils.checkTalkDuration(list.toList,tk.duration,sessionDuration)){
            list+= Talks(tk.title,tk.duration,ScheduleUtils.getSchedule(list.toList))
            talksList -= tk
          }
        }
      }
    }
    (talksList, Session(list,startDateTime.toString, endDateTime.toString))
  }

  def output(conference: Conference)={
    conference.tracks.map { track =>
      track.sessions.map { session =>
        session.talks.map { talk=>
          val date = DateTime.parse(talk.scheduleTime.get)
          val schedule = date.toString(format)
          println(s"${talk.title} ${talk.duration} => ${schedule}")
        }
      }
    }
  }
}