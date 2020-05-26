def build(Map args = [:]) {
    List<String> BUILD_PACKAGES = [
        "help2man",
        "guile-sqlite3",
        "guile-gcrypt"
    ]
    List<String> BUILD_SCRIPTS = [
        'make --jobs=$(nproc)',
        '(set -e -x; ./bootstrap; ./configure --localstatedir=/var --prefix=; make --jobs=$(nproc))',
        '(set -e -x; make clean-go; ./bootstrap; ./configure --localstatedir=/var --prefix=; make --jobs=$(nproc))'
    ]
    String BUILD_COMMAND = """
        guix environment --pure guix --ad-hoc ${BUILD_PACKAGES.join(' ')} \
          -- sh -c "${BUILD_SCRIPTS.join(' || ')}"
    """
    sh BUILD_COMMAND
}
