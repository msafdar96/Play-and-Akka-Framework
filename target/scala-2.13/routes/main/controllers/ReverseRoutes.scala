// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:4
package controllers {

  // @LINE:4
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:4
    def index: Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:26
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:26
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:7
  class ReverseSearchController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def globalStats(keyword:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/search/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("keyword", keyword)) + "/globalstats")
    }
  
    // @LINE:10
    def descriptionReadability(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/descriptionReadability")
    }
  
    // @LINE:15
    def projectStats(keyword:String, index:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/search/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("keyword", keyword)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("index", index)) + "/projectstats")
    }
  
    // @LINE:12
    def showReadability(keyword:String, index:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/search/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("keyword", keyword)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("index", index)) + "/readability")
    }
  
    // @LINE:22
    def userDetails(id:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/userdetails/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("id", id)))
    }
  
    // @LINE:21
    def user(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/user")
    }
  
    // @LINE:14
    def stats(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/stats")
    }
  
    // @LINE:8
    def search(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/search")
    }
  
    // @LINE:18
    def skills(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/skills")
    }
  
    // @LINE:7
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot")
    }
  
    // @LINE:19
    def projectSkills(skill:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "freelancelot/projects/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("skill", skill)))
    }
  
  }


}
