
package views.html.User

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.data._
import play.core.j.PlayFormsMagicForJava._
import scala.jdk.CollectionConverters._
/*1.2*/import java.util._

object userdetails extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(id : String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.15*/("""

"""),format.raw/*4.1*/("""<html>
    <head>
        <title>User Information</title>
        <link rel="stylesheet" media="screen" href='"""),_display_(/*7.54*/routes/*7.60*/.Assets.versioned("stylesheets/Search/main.css")),format.raw/*7.108*/("""'>
        <link rel="shortcut icon" type="image/png" href='"""),_display_(/*8.59*/routes/*8.65*/.Assets.versioned("images/favicon.png")),format.raw/*8.104*/("""'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    </head>
    <body>
        <p style="margin-top: 1%">
        <h2 style="text-align: center;color: #0077aa">Employer Details</h2> <br/>
        <p style="text-align: center" id="name"><b>Employer Name: </b></p>
        <p style="text-align: center" id="role"><b>Role: </b></p>
        <p style="text-align: center" id="rate"><b>Hourly Rate: </b></p>
        <p style="text-align: center" id="dName"><b>Display name: </b></p>
        <p style="text-align: center" id="rDate"><b>Registration Date: </b></p>
        <p style="text-align: center" id="cRole"><b>Chosen role: </b></p>
        <p style="text-align: center" id="country"><b>Country: </b></p>
        <p style="text-align: center" id="currency"><b>Primary Currency: </b></p>
        <p style="text-align: center" id="email"><b>Email verified: </b></p>
        <p style="text-align: center" id="lAccount"><b>Limited Account: </b></p>

        <table class="table table-hover">
            <thead class="dark-head">
                <th>#</th>
                <th>Owner ID</th>
                <th>Time Submitted</th>
                <th>Title</th>
                <th>Type</th>
                <th>Skills</th>

            </thead>
            <tbody id="projects">

            </tbody>

        </table>

        <script src=""""),_display_(/*42.23*/routes/*42.29*/.Assets.versioned("javascripts/User/main.js")),format.raw/*42.74*/("""" type="text/javascript"></script>
    </body>
</html>"""))
      }
    }
  }

  def render(id:String): play.twirl.api.HtmlFormat.Appendable = apply(id)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (id) => apply(id)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/User/userdetails.scala.html
                  HASH: a0484dbd111a31a4dbc74e3af078030c3342a707
                  MATRIX: 615->1|944->22|1052->35|1082->39|1222->153|1236->159|1305->207|1393->269|1407->275|1467->314|3040->1861|3055->1867|3121->1912
                  LINES: 23->1|28->2|33->2|35->4|38->7|38->7|38->7|39->8|39->8|39->8|73->42|73->42|73->42
                  -- GENERATED --
              */
          