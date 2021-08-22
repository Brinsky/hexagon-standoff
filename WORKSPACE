load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

rules_kotlin_version = "v1.5.0-beta-3"
rules_kotlin_sha = "58edd86f0f3c5b959c54e656b8e7eb0b0becabd412465c37a2078693c2571f7f"
http_archive(
    name = "io_bazel_rules_kotlin",
    urls = ["https://github.com/bazelbuild/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" % rules_kotlin_version],
    sha256 = rules_kotlin_sha,
)

load("@io_bazel_rules_kotlin//kotlin:repositories.bzl", "kotlin_repositories")

KOTLIN_VERSION = "1.5.21"
KOTLINC_RELEASE_SHA = "f3313afdd6abf1b8c75c6292f4e41f2dbafefc8f6c72762c7ba9b3daeef5da59"
KOTLINC_RELEASE = {
    "urls": [
        "https://github.com/JetBrains/kotlin/releases/download/v{v}/kotlin-compiler-{v}.zip".format(v = KOTLIN_VERSION),
    ],
    "sha256": KOTLINC_RELEASE_SHA,
}
kotlin_repositories(compiler_release = KOTLINC_RELEASE)

load("@io_bazel_rules_kotlin//kotlin:core.bzl", "kt_register_toolchains")
kt_register_toolchains()
