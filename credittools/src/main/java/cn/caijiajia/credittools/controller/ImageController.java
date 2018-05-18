package cn.caijiajia.credittools.controller;

import cn.caijiajia.credittools.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:chendongdong
 * @Date:2018/5/14
 */
@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        imageService.getImage(request, response);
    }

}
