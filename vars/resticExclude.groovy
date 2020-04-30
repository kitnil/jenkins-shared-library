def call(Map args = [:]) {
    assert args.users instanceof List<String> : "Users should be a list of strings"
    assert args.dirs instanceof List<String> : "Dirs should be a list of strings"

    def rootDirs = [
        "/root/.cache",
        "/root/.guix-profile",
        "/root/.nix-profile",
        "/root/Downloads",
        "/root/GNS3",
        "/root/Videos",
        "/root/archive",
        "/root/majordomo",
        "/root/src",
        "/root/vm"
    ]

    def usersDirs = args.users.collect {
        [
            "/home/$it/.cache",
            "/home/$it/.guix-profile",
            "/home/$it/.nix-profile",
            "/home/$it/Downloads",
            "/home/$it/GNS3",
            "/home/$it/Videos",
            "/home/$it/majordomo",
            "/home/$it/vm"
        ]
    }.inject([]) {head, tail -> head + tail}

    (rootDirs + usersDirs + args.dirs).collect{"--exclude $it"}.join(" ")
}
