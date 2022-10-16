
package views.html.WordStats

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

object globalstats extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(keyword : String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.20*/("""

"""),format.raw/*4.1*/("""<html lang="en">
    <head>
        <title>Project Words Stats</title>
        <link rel="stylesheet" media="screen" href='"""),_display_(/*7.54*/routes/*7.60*/.Assets.versioned("stylesheets/DescriptionReadability/main.css")),format.raw/*7.124*/("""'>
        <link rel="shortcut icon" type="image/png" href='"""),_display_(/*8.59*/routes/*8.65*/.Assets.versioned("images/favicon.png")),format.raw/*8.104*/("""'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    </head>
    <body>
        <div class="main">
            <h1>Project of Search Results """"),_display_(/*14.45*/keyword),format.raw/*14.52*/("""" </h1>

            <div class="alert alert-success">
                <h3 class="alert-heading">Global Words Stats</h3>

                <p id="tWords"><b>Total Words: </b></p>
                <p id="uWords"><b>Total Unique Words: </b></p>
                <p id="sentences"><b>Total Sentences: </b></p>
                <p id="wordsPerSent"><b>Words Per Sentences: </b></p>
                <p id="charsPerWord"><b>Characters Per Words: </b></p>
                <p id="charsPerSent"><b>Characters Per Sentences: </b></p>

                <div class="card text-center card-margin">
                    <div class="card-header">
                        Word Count
                    </div>
                </div>

                <table class="table table-hover">
                    <thead class="dark-head">
                        <th>Word</th>
                        <th>Count</th>
                    </thead>
                    <tbody id="stats">

                    </tbody>
                </table>

            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src=""""),_display_(/*45.23*/routes/*45.29*/.Assets.versioned("javascripts/Stats/global.js")),format.raw/*45.77*/("""" type="text/javascript"></script>
    </body>
</html>"""))
      }
    }
  }

  def render(keyword:String): play.twirl.api.HtmlFormat.Appendable = apply(keyword)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (keyword) => apply(keyword)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/WordStats/globalstats.scala.html
                  HASH: d5e775a78ef4ba2d2174307c9f8bd928ded0ef3a
                  MATRIX: 620->1|949->22|1062->40|1092->44|1245->171|1259->177|1344->241|1432->303|1446->309|1506->348|1856->672|1884->679|3224->1993|3239->1999|3308->2047
                  LINES: 23->1|28->2|33->2|35->4|38->7|38->7|38->7|39->8|39->8|39->8|45->14|45->14|76->45|76->45|76->45
                  -- GENERATED --
              */
          