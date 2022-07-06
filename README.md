# About the projects
Each folder holds the two separate pieces of the tasks: the frontend and backend. Each project `README.md` gives working instructions to run the projects.

## Notice about the frontend
Whenever you compile it, there shall be a warning about a redeclaration of a `parse-double`, that is due to `cljs-http` dependency that implements this function and the latest version of clojurescript started implementing it too. It's annoying, but that's all.
