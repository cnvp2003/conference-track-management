package com.company.conference.model

import scala.collection.mutable

/**
  * Created by pati80 on 25/02/17.
  */
case class Session(talks: mutable.ListBuffer[Talks], startTime: String, endTime: String) {

}