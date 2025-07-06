rootProject.name = "core"

// Include the top-level modules
include("api")
include("proxies")

// Include the new core sub-modules
include("core:common")
include("core:legacy")
include("core:modern")

include("launcher")