def gitRev(String rev) {
    sh (returnStdout: true, script: "git rev-parse $rev")
}

def push(Map args = [:]) {
    assert args.name instanceof String
    String remote = "git@github.com:" + Constants.githubOrganization + "/" + args.name + ".git"
    if (gitRemote.getRemote("github") == null) {
        sh "git remote add github $remote"
    }
    if (gitRemote.getRemote("github").url != remote) {
        sh "git remote set-url github $remote"
    }
    if (gitRev("origin/master") == gitRev("HEAD")) {
        sh 'sshpass -Ppassphrase -p"$(pass show github/ssh/id_rsa_github)" git push github HEAD:refs/heads/master'
    } else {
        throw "Git origin/master hash doesn't match HEAD hash."
    }
}
