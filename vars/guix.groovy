def build(String pack) {
    sh "guix package --show=${pack}"
    try {
        sh "guix build --no-grafts ${pack}"
    } catch (Exception error) {
        echo "Stage failed, but we still continue"
        slackSend color: '#1E90FF', message: "${pack} build failed"
    }
}
