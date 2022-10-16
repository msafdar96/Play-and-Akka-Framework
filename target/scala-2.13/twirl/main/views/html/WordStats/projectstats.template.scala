
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

object projectstats extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(keyword: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.19*/("""

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
            <div class="alert alert-success" role="alert">
                <h4 class="alert-heading">Preview Description</h4>
                <p id="previewDescription"></p>
                <hr>
            </div>
            <div class="card text-center">
                <div class="card-header">
                    Project Words Stats
                </div>
                <div class="card-body">
                    <p id="tWords"><b>Total Words: </b></p>
                    <p id="uWords"><b>Total Unique Words: </b></p>
                    <p id="characters"><b>Total Characters: </b></p>
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
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src=""""),_display_(/*55.23*/routes/*55.29*/.Assets.versioned("javascripts/Stats/main.js")),format.raw/*55.75*/("""" type="text/javascript"></script>
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
                  SOURCE: app/views/WordStats/projectstats.scala.html
                  HASH: 18fc92a746b4c3336decf65a93ca6235ae789cc9
                  MATRIX: 620->1|950->22|1062->39|1092->43|1245->170|1259->176|1344->240|1432->302|1446->308|1506->347|1856->671|1884->678|3692->2460|3707->2466|3774->2512
                  LINES: 23->1|28->2|33->2|35->4|38->7|38->7|38->7|39->8|39->8|39->8|45->14|45->14|86->55|86->55|86->55
                  -- GENERATED --
              */
          