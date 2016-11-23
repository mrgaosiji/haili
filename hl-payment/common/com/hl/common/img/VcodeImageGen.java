package com.hl.common.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class VcodeImageGen {
	
	private static Color getRanColor(int fc,int bc){
        Random random=new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

	public static String generateVcodeImg(String randomValue,OutputStream os)throws IOException{
		int width=60;
		int height=20;
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g =image.getGraphics();
		Random  random=new Random();
		g.setColor(getRanColor(220,250));
		g.fillRect(0,0,width,height);
		g.setFont(new Font("Times New Roman",Font.PLAIN,18));
		g.setColor(getRanColor(220,250));
		
		for(int i=0;i<155;i++)
		{
			int x=random.nextInt(width);
			int y=random.nextInt(height);
			int xl=random.nextInt(12);
			int yl=random.nextInt(12);
			g.drawLine(x,y,x+xl,y+yl);
		}
		String sRand="";
		for(int i=0;i<4;i++)
		{
			String rand=String.valueOf(random.nextInt(10));
			if(randomValue != null && !"".equals(randomValue)){
				rand = randomValue.substring(i,i+1);
			}
			sRand+=rand;
			g.setColor(new Color(20+random.nextInt(110),40+random.nextInt(110),60+random.nextInt(110)));
			g.drawString(rand,13*i+6,16);
		}
		g.dispose();
		
	   ImageIO.write(image,"JPEG",os);
	   
	   return sRand;
		
	}
}
