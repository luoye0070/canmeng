class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
        "/"(view:"/index")
		"/index.html"(view:"/index")
		"500"(view:'/error')
        "/info"(view:'info')
	}
}
