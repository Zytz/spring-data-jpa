package com.john.service;

import com.john.dao.ResumeDao;
import com.john.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:wenwei
 * @date:2020/03/15
 * @description:
 */
@Service
public class ResumeService {

    @Autowired
    ResumeDao resumeDao;

    public List<Resume> findAll(){
        List<Resume> all = resumeDao.findAll();
        return all;
    }

    public Resume addResume(Resume resume){
       return resumeDao.save(resume);
    }


    public boolean deleteResume(Integer id){
         resumeDao.deleteById(id);
         return true;
    }



}
