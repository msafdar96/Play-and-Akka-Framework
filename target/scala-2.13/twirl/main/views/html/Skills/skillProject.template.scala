
package views.html.Skills

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

object skillProject extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(title: String, heading: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.34*/("""


"""),format.raw/*5.1*/("""<html lang="en">
    <head>
        <title>"""),_display_(/*7.17*/title),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href='"""),_display_(/*8.54*/routes/*8.60*/.Assets.versioned("stylesheets/Search/main.css")),format.raw/*8.108*/("""'>
        <link rel="shortcut icon" type="image/png" href='"""),_display_(/*9.59*/routes/*9.65*/.Assets.versioned("images/favicon.png")),format.raw/*9.104*/("""'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    </head>
    <body>
        <div class="main">
            <h1>"""),_display_(/*15.18*/heading),format.raw/*15.25*/("""</h1>
            <div>
                <p class="error"></p>
                <div id="searchResults">

                </div>
            </div>

        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src=""""),_display_(/*26.23*/routes/*26.29*/.Assets.versioned("javascripts/Skills/main.js")),format.raw/*26.76*/("""" type="text/javascript"></script>
    </body>
</html>"""))
      }
    }
  }

  def render(title:String,heading:String): play.twirl.api.HtmlFormat.Appendable = apply(title,heading)

  def f:((String,String) => play.twirl.api.HtmlFormat.Appendable) = (title,heading) => apply(title,heading)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/Skills/skillProject.scala.html
                  HASH: 80e01ad5d12c871338f01d21d4bd565372516a5e
                  MATRIX: 617->1|954->22|1081->54|1113->60|1185->106|1210->111|1299->174|1313->180|1382->228|1470->290|1484->296|1544->335|1867->632|1895->639|2498->1216|2513->1222|2581->1269
                  LINES: 23->1|28->2|33->2|36->5|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|46->15|46->15|57->26|57->26|57->26
                  -- GENERATED --
              */
          