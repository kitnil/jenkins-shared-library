@NonCPS
def getNodeNames(List<String> labels) {
    jenkins.model.Jenkins.instance.nodes
            .findAll { node -> labels.contains(node.labelString) }
            .collect { node -> node.name }
}

def call(Map args = [:]) {
    assert args.nodeLabels : "No node labels provided"
    assert args.nodeLabels instanceof List<String> : "Node labels should be a list of strings"
    assert args.dir: "No directory provided"
    assert args.url: "No URL provided"

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
            }
        }
    }
    parallel nodes
}
