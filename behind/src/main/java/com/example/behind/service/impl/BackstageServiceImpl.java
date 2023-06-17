package com.example.behind.service.impl;

import com.example.behind.domain.Major;
import com.example.behind.domain.School;
import com.example.behind.domain.Tag;
import com.example.behind.repository.MajorRepository;
import com.example.behind.repository.SchoolRepository;
import com.example.behind.repository.TagRepository;
import com.example.behind.service.BackstageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BackstageServiceImpl implements BackstageService {
    TagRepository tagRepository;
    SchoolRepository schoolRepository;
    MajorRepository majorRepository;

    @Autowired
    public BackstageServiceImpl(TagRepository tagRepository, SchoolRepository schoolRepository,
                                MajorRepository majorRepository){
        this.tagRepository = tagRepository;
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public Long addTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName);
        if(tag != null) return 0L;
        return tagRepository.save(new Tag(0L, tagName)).getId();
    }

    @Override
    public Long addSchool(String schoolName) {
        School school = schoolRepository.findByName(schoolName);
        if(school != null) return 0L;
        return schoolRepository.save(new School(0L, schoolName)).getId();
    }

    @Override
    public Long addMajor(String schoolName, String majorName) {
        School school = schoolRepository.findByName(schoolName);
        if(school == null) return 0L;
        Major major = majorRepository.findByName(majorName);
        if(major != null) return 0L;
        return majorRepository.save(new Major(0L, majorName, school)).getId();
    }
}
