package com.example.behind.service;

public interface BackstageService {
    Long addTag(String tagName);
    Long addSchool(String schoolName);
    Long addMajor(String schoolName, String majorName);
}
