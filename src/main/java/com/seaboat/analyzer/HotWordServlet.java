package com.seaboat.analyzer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seaboat.text.analyzer.hotword.HotWordExtractor;
import com.seaboat.text.analyzer.hotword.Result;
import com.seaboat.text.analyzer.hotword.TextIndexer;

public class HotWordServlet extends HttpServlet {

  private static HotWordExtractor extractor;

  @Override
  public void init(ServletConfig arg0) throws ServletException {
    extractor = new HotWordExtractor();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // response.setContentType("text/html");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String input = request.getParameter("myText");
    input = new String(input.getBytes("iso-8859-1"),"utf-8");
    String callback = request.getParameter("callback");
    int id = (int) TextIndexer.index(input.toString()) ;
    List<Result> list = extractor.extract(id, 20, true);
    String result = "";
    if (list != null) {for (Result s : list) {
      System.out.println(s.getTerm() + " : " + s.getFrequency() + " : " + s.getScore());
      result += s.getTerm() + " : " + s.getFrequency() + " : " + s.getScore();
      result += "|";
    }
    result = result.substring(0, result.length() - 1);
    }
    String json = "{\"hotword\":\" " + result + " \"}";
    out.print(callback + "(" + json + ")");
    out.flush();
    out.close();
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    this.doGet(request, response);
  }
}
