package com.company.conference.app

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, Locale}

import com.company.conference.logic.Scheduler
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.io.Source

/**
  * Created by pati80 on 25/02/17.
  */
object Application {

  def main(args: Array[String]) = {
    val listOfTalks = Source.fromFile("input.txt").getLines.toList
    Scheduler.createConference(listOfTalks)
  }
}
