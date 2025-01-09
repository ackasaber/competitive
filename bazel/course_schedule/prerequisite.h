#pragma once

// Used both in the course_schedule.h public interface and course_graph.h implementation header.
struct Prerequisite {
  int course;
  int prereq;
};