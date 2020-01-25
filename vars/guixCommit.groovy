def call() {
    sh (script: 'guix describe --format=recutils | recsel --expression=name=\\"guix\\" --print-values=commit',
        returnStdout: true).trim()
}
