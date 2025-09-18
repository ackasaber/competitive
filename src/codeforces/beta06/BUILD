load("@contrib_rules_jvm//java:defs.bzl", "java_junit5_test")
load("@rules_jvm_external//:defs.bzl", "artifact")

java_library(
    name = "lnb",
    srcs = [
        "CastSet.java",
        "LnB.java",
        "LnB2.java",
        "LnBGame.java",
    ],
)

java_junit5_test(
    name = "LnBTest",
    srcs = ["LnBTest.java"],
    test_class = "codeforces.beta06.LnBTest",
    runtime_deps = [
        artifact("org.junit.jupiter:junit-jupiter-engine"),
    ],
    deps = [
        ":lnb",
        artifact("org.junit.jupiter:junit-jupiter-api"),
        artifact("org.junit.platform:junit-platform-launcher"),
        artifact("org.junit.platform:junit-platform-reporting"),
        artifact("org.junit.jupiter:junit-jupiter-params"),
    ],
)
