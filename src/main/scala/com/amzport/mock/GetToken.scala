package com.amzport.mock
import MockHTTP._
import play.api.libs.json.Json


object GetToken extends App {
  var uuid: String = ""
  var token: String = ""
  postJson("http://192.168.5.30:9000/api/auth/login",
    Json.obj("phoneNumber"->"110","loginPassword"->"111","logoutOthers"->true)
  )().map {
    case Left(e) =>
      println(111)
      println(e.getMessage)
    case Right(v) =>
      rspEntityJson(v.entity).map { res =>
        println(222)
        println(Json.stringify(res))
        val authMeta = (res \ "authMeta").as[AuthMeta]
        uuid = authMeta.accountId.toString
        val authenticationToken = (res \ "Authentication-Token").as[String]
        token = authenticationToken
        val authenticationRefreshToken = (res \ "Authentication-Refresh-Token").as[String]
        println(authMeta)
        println(authenticationToken, authenticationRefreshToken)

      }
  }



}
