 
package com.lixuan.weixin.controller;  

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lixuan.weixin.service.CoreService;
import com.lixuan.weixin.util.SignUtil;
/**  
* ClassName:WeixinController   
* Date:     2016年1月21日 下午10:03:40  
* @author   CAOPENG  
* @version  1.0 
* @since    JDK 1.7  
* @right    Copyright (c) 2016, lixuan.com All Rights Reserved.   
* @see        
*/

@Controller
@RequestMapping("/weixinConnection")
public class WinxinController extends HttpServlet
{
	private static final long serialVersionUID = -7356158384123643554L;
	@Resource(name="coreService")
	private CoreService coreService;
	
	/**
	 * 微信接入
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
			 
	{
		/*注意事项：首先设置中文字符集  ：utf-8  gbk  gb2312*/
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		System.out.println("get-invoke-ok");
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = null;
		try 
		{
			out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) 
			{
				out.print(echostr);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			out.close();
			out = null;
		}
	}

	/**
	 * 核心业务
	 */
	//@RequestMapping(method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		System.out.println("============================================");
		System.out.println("进入核心业务");
		
		try 
		{
			request.setCharacterEncoding("UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		response.setCharacterEncoding("UTF-8");

		// 调用核心业务类接收消息、处理消息
		String respMessage = coreService.processRequest(request);

		// 响应消息
		PrintWriter out = null;
		try 
		{
			out = response.getWriter();
			out.print(respMessage);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			out.close();
			out = null;
		}
		
	}
}
 
