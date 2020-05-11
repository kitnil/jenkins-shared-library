def call(Map args = [:]) {
    assert args.dir: "No directory provided"
    assert args.url: "No URL provided"
    String branch = args.branch ?: "master"
    String remote = args.remote ?: "origin"
    if (fileExists (args.dir)) {
        echo "${args.dir} alredy exists"
    } else {
        sh "git clone ${args.url} ${args.dir}"
    }
    dir(args.dir) {
        sh """
            git fetch ${remote}
            git checkout ${branch} || true # true if is already checked
            git reset --hard ${remote}/${branch}
        """
    }
}
