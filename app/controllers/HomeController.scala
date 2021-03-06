package controllers

import daos.ProduktDAO
import model.Produkt

import javax.inject._
import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject() (produktDao: ProduktDAO, controllerComponents: ControllerComponents)
                               (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {

  implicit val produktFormat: Format[Produkt] = Json.format[Produkt]

  def index() = Action.async {
    produktDao.all().map { case (produkte) => Ok(views.html.index(produkte)) }
    //Ok(views.html.index(produktDao.all()))
  }

  def env() = Action { implicit request: Request[AnyContent] =>
    Ok("Nothing to see here")
    //Ok(System.getenv("JDBC_DATABASE_URL"))
  }

  def json() = Action.async {
    produktDao.all().map{ case (produkte) => Ok(Json.toJson(produkte)) }
  }

  val produktForm = Form(
    mapping(
      "name" -> text(),
      "price" -> number())(Produkt.apply)(Produkt.unapply))

  def insertProdukt = Action.async { implicit request =>
    val produkt: Produkt = produktForm.bindFromRequest.get
    produktDao.insert(produkt).map(_ => Redirect(routes.HomeController.index))
  }
}
