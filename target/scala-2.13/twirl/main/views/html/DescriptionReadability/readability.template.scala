
package views.html.DescriptionReadability

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

object readability extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(title : String, keyword : String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""<html lang="en">
    <head>
        <title>"""),_display_(/*5.17*/title),format.raw/*5.22*/("""</title>
        <link rel="stylesheet" media="screen" href='"""),_display_(/*6.54*/routes/*6.60*/.Assets.versioned("stylesheets/DescriptionReadability/main.css")),format.raw/*6.124*/("""'>
        <link rel="shortcut icon" type="image/png" href='"""),_display_(/*7.59*/routes/*7.65*/.Assets.versioned("images/favicon.png")),format.raw/*7.104*/("""'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body>
        <div class="main">
            <h1>Project of Search Results """"),_display_(/*12.45*/keyword),format.raw/*12.52*/("""" </h1>
            <div class="alert alert-success" role="alert">
                <h4 class="alert-heading">Preview Description</h4>
                <p id="previewDescription"></p>
"""),format.raw/*16.25*/("""
"""),format.raw/*17.72*/("""
"""),format.raw/*18.84*/("""
            """),format.raw/*19.13*/("""</div>
            <div class="card text-center">
                <div class="card-header">
                    Description Readability
                </div>
                <div class="card-body">
                    <h5 class="card-title">Flesch Readability Index</h5>
                   <p class="card-text" id="fleschReadabilityIndex"></p>
                   <h5 class="card-title">Flesch Kincaid Grade Level</h5>
                    <p class="card-text" id="fleschKincaidGradeLevel"></p>
                    <div class="alert alert-primary" role="alert">
                        Educational Level: <p id="educationalLevel"></p>
                    </div>
                </div>
            </div>
            <table class="table table-hover">
                <thead class="dark-head">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Flesch Index</th>
                        <th scope="col">Educational Level</th>
                        <th scope="col">Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>100+</td>
                        <td>Early</td>
                        <td>Relatively easy to understand.</td>
                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>>90 && <=100</td>
                        <td>5th grade</td>
                        <td>Very easy to read. Easily understood by an average 11-year-old student.</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>>80 && <=90</td>
                        <td>6th grade</td>
                        <td>Easy to read. Conversational English for consumers.</td>
                    </tr>
                    <tr>
                        <th scope="row">4</th>
                        <td>>70 && <=80</td>
                        <td>7th grade</td>
                        <td>Fairly easy to read.</td>
                    </tr>
                    <tr>
                        <th scope="row">5</th>
                        <td>>65 && <=70</td>
                        <td>8th grade</td>
                        <td>Plain English. Easily understood by 13-year-old students.</td>
                    </tr>
                    <tr>
                        <th scope="row">6</th>
                        <td>>60 && <=65</td>
                        <td>9th grade</td>
                        <td>Plain English. Easily understood by 15-year-old students.</td>
                    </tr>
                    <tr>
                        <th scope="row">7</th>
                        <td>>50 <=60</td>
                        <td>High School (10th grade to 12th grade)</td>
                        <td>Fairly difficult to read.</td>
                    </tr>
                    <tr>
                        <th scope="row">8</th>
                        <td>>30 && <=50</td>
                        <td>Some College </td>
                        <td>Difficult to read.</td>
                    </tr>
                    <tr>
                        <th scope="row">9</th>
                        <td>>10 && <=30</td>
                        <td>College Graduate</td>
                        <td>Very difficult to read. Best understood by university graduates.</td>
                    </tr>
                    <tr>
                        <th scope="row">10</th>
                        <td>(>0 or <0) && <=10</td>
                        <td>Law School Graduate / Professional</td>
                        <td>Extremely difficult to read. Best understood by university graduates.</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src=""""),_display_(/*109.23*/routes/*109.29*/.Assets.versioned("javascripts/DescriptionReadability/main.js")),format.raw/*109.92*/("""" type="text/javascript"></script>
    </body>
</html>"""))
      }
    }
  }

  def render(title:String,keyword:String): play.twirl.api.HtmlFormat.Appendable = apply(title,keyword)

  def f:((String,String) => play.twirl.api.HtmlFormat.Appendable) = (title,keyword) => apply(title,keyword)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/DescriptionReadability/readability.scala.html
                  HASH: 826df9ef5857bc57083664733a84741bbb8d2a94
                  MATRIX: 633->1|969->21|1097->56|1167->100|1192->105|1280->167|1294->173|1379->237|1466->298|1480->304|1540->343|1883->660|1911->667|2121->873|2150->945|2179->1029|2220->1042|6550->5345|6566->5351|6651->5414
                  LINES: 23->1|28->2|33->3|35->5|35->5|36->6|36->6|36->6|37->7|37->7|37->7|42->12|42->12|46->16|47->17|48->18|49->19|139->109|139->109|139->109
                  -- GENERATED --
              */
          