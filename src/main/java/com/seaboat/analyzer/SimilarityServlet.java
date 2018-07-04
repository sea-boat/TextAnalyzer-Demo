package com.seaboat.analyzer;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.seaboat.text.analyzer.util.Segment;
import com.seaboat.text.analyzer.word2vec.Word2Vec;

public class SimilarityServlet extends HttpServlet {
	private static Word2Vec vec = Word2Vec.getInstance(true);

	@Override
	public void init(ServletConfig config) throws ServletException {
		String word2vec_path = config.getInitParameter("word2vec_path");
		try {
			vec.loadGoogleModel(word2vec_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String type = request.getParameter("type");
		if (type.equals("word")) {
			String word1 = request.getParameter("word1");
			String word2 = request.getParameter("word2");
			word1 = new String(word1.getBytes("iso-8859-1"), "utf-8");
			word2 = new String(word2.getBytes("iso-8859-1"), "utf-8");
			String callback = request.getParameter("callback");
			String json = "{\"similarity\":\" " + vec.wordSimilarity(word1, word2) + " \"}";
			out.print(callback + "(" + json + ")");
			out.flush();
			out.close();
		} else if (type.equals("sentence")) {
			String sentence1 = request.getParameter("sentence1");
			String sentence2 = request.getParameter("sentence2");
			//      sentence1 = new String(sentence1.getBytes("iso-8859-1"),"utf-8");
			//      sentence2 = new String(sentence2.getBytes("iso-8859-1"),"utf-8");
			String callback = request.getParameter("callback");
			String chatset = Charset.defaultCharset().name();
			String json = "{\"similarity\":\" "
					+ vec.sentenceSimilarity(Segment.getWords(sentence1), Segment.getWords(sentence2)) + " \"}";
			out.print(callback + "(" + json + ")");
			out.flush();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
