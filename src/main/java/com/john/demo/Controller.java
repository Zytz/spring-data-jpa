package com.john.demo;

import com.john.pojo.Resume;
import com.john.service.AccountService;
import com.john.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author:wenwei
 * @date:2020/03/15
 * @description:
 */

@org.springframework.stereotype.Controller
@RequestMapping
public class Controller {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/users/login",method = RequestMethod.GET)
    public String loginIn(String username, String password) {
        if (accountService.login(username, password)) {
            return "success";
        }
        return "error";

    }

    @RequestMapping(path = "/resume/all",method = RequestMethod.GET)
    @ResponseBody
    public List<Resume> listResumes() {

        List<Resume> all = resumeService.findAll();
        return  all;
    }

    @RequestMapping(path = "/resume/add",method = RequestMethod.POST)
    @ResponseBody
    public Resume addResumes(Resume resume, @RequestHeader("Authorization")String token) {
        if(!accountService.checkToken(token)){
            throw new RuntimeException();
        }

        return resumeService.addResume(resume);
    }

    @RequestMapping(path = "/resume/delete",method = RequestMethod.POST)
    @ResponseBody
    public boolean addResumes(int resumeId,@RequestHeader("Authorization")String token) {
        if(!accountService.checkToken(token)){
            throw new RuntimeException();
        }

        boolean b = resumeService.deleteResume(resumeId);
        return b;
    }
}
