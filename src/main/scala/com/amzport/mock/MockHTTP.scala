package com.amzport.mock

import java.time.LocalDateTime

import akka.actor.ActorSystem
import akka.http.scaladsl.Http

import scala.concurrent.{ExecutionContextExecutor, Future}
import play.api.libs.json.{JsValue, Json, OFormat}
import akka.http.scaladsl.client.RequestBuilding.{Delete, Get, Head, Options, Patch, Post, Put}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpHeader, HttpResponse, RequestEntity, ResponseEntity}

import scala.concurrent.duration._

object MockHTTP {
  implicit val system: ActorSystem = MockSystem.system
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher


  // 基础方法

  def get(url: String, entity: RequestEntity = HttpEntity.Empty)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    Http().singleRequest(Get(url).withHeadersAndEntity(headers, entity)).map(res => Right(res)).recover{ case e: Throwable => Left(e) }

  def post(url: String, entity: RequestEntity = HttpEntity.Empty)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    Http().singleRequest(Post(url).withHeadersAndEntity(headers, entity)).map(res => Right(res)).recover{ case e: Throwable => Left(e) }

  def put(url: String, entity: RequestEntity = HttpEntity.Empty)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    Http().singleRequest(Put(url).withHeadersAndEntity(headers, entity)).map(res => Right(res)).recover{ case e: Throwable => Left(e) }

  def delete(url: String, entity: RequestEntity = HttpEntity.Empty)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    Http().singleRequest(Delete(url).withHeadersAndEntity(headers, entity)).map(res => Right(res)).recover{ case e: Throwable => Left(e) }

  def head(url: String, entity: RequestEntity = HttpEntity.Empty)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    Http().singleRequest(Head(url).withHeadersAndEntity(headers, entity)).map(res => Right(res)).recover{ case e: Throwable => Left(e) }

  def patch(url: String, entity: RequestEntity = HttpEntity.Empty)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    Http().singleRequest(Patch(url).withHeadersAndEntity(headers, entity)).map(res => Right(res)).recover{ case e: Throwable => Left(e) }

  def options(url: String, entity: RequestEntity = HttpEntity.Empty)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    Http().singleRequest(Options(url).withHeadersAndEntity(headers, entity)).map(res => Right(res)).recover{ case e: Throwable => Left(e) }


  // JSON格式发送

  def postJson(url: String, value: JsValue)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    post(url, HttpEntity(ContentTypes.`application/json`, Json.stringify(value)))(headers)

  def putJson(url: String, value: JsValue)(headers: Seq[HttpHeader] = Seq.empty): Future[Either[Throwable, HttpResponse]] =
    put(url, HttpEntity(ContentTypes.`application/json`, Json.stringify(value)))(headers)


  // 返回结果解析辅助

  def rspEntityString(entity: ResponseEntity): Future[String] = {
    entity.toStrict(5.seconds).map(i => i.data.utf8String)
  }

  def rspEntityJson(entity: ResponseEntity): Future[JsValue] = {
    entity.toStrict(5.seconds).map(i => Json.parse(i.data.toArray[Byte]))
  }


  // 认证相关

  case class AuthMeta(roles: List[String], accountId: Long, groupIds: List[Int], groupTrees: List[String], ts: LocalDateTime)
  implicit val AuthMetaFormat: OFormat[AuthMeta] = Json.format[AuthMeta]

}
