package com.seaboat.analyzer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seaboat.text.analyzer.extractor.AddressExtractor;
import com.seaboat.text.analyzer.hotword.HotWordExtractor;
import com.seaboat.text.analyzer.hotword.Result;
import com.seaboat.text.analyzer.hotword.TextIndexer;

public class AddressServlet extends HttpServlet {

  private static HotWordExtractor extractor;

  @Override
  public void init(ServletConfig arg0) throws ServletException {
    extractor = new HotWordExtractor();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String input = request.getParameter("myAddressText");
    String callback = request.getParameter("callback");
    input = new String(input.getBytes("iso-8859-1"),"utf-8");
    AddressExtractor extractor = new AddressExtractor();
    List<String> list = extractor.extract(input);
    String result = "";
    if (list != null) {
      for (String s : list) {
        result += s;
        result += "|";
      }
      result = result.substring(0, result.length() - 1);
    }
    System.out.println(result);
    String json = "{\"address\":\" " + result + " \"}";
    out.print(callback + "(" + json + ")");
    out.flush();
    out.close();
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    this.doGet(request, response);
  }
}
