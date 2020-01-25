def call() {
    sh (script: "git ls-remote https://git.savannah.gnu.org/git/guix.git refs/heads/master | cut -f 1",
        returnStdout: true).trim()
}
