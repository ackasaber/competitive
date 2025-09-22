cc_library(
    name = "unique_paths2",
    srcs = ["unique_paths2.cc"],
    hdrs = ["unique_paths2.h"],
    deps = ["@abseil-cpp//absl/types:span"],
)

cc_test(
    name = "unique_paths2_test",
    size = "small",
    srcs = ["unique_paths2_test.cc"],
    deps = [
        ":unique_paths2",
        "@googletest//:gtest_main",
    ],
)
