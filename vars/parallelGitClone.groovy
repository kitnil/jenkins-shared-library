def call(Map args = [:]) {
    assert args.nodeLabels : "No node labels provided"
    assert args.nodeLabels instanceof List<String> : "Node labels should be a list of strings"
    assert args.dir: "No directory provided"
    assert args.url: "No URL provided"

    def branch = args.branch ?: "master"
    def remote = args.remote ?: "origin"

    def nodes = [:]
    def names = getNodeNames(args.nodeLabels)
    for (int i=0; i<names.size(); ++i) {
        def nodeName = names[i];
        nodes[nodeName] = {
            node(nodeName) {
                if (fileExists (args.dir)) {
                    echo "${args.dir} alredy exists"
                } else {
                    sh "git clone ${args.url} ${args.dir}"
                }
                dir(args.dir) {
                    sh "git fetch ${remote}"
                    sh "git checkout ${branch}"
                    sh "git pull --rebase ${remote} ${branch}"
                }
            }
        }
    }
    parallel nodes
}
