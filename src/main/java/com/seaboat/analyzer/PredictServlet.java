package com.seaboat.analyzer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seaboat.text.analyzer.training.Labels;
import com.seaboat.text.analyzer.training.SVMTrainer;

public class PredictServlet extends HttpServlet {

	private static SVMTrainer trainer;

	@Override
	public void init(ServletConfig config) throws ServletException {
		String vector_file = config.getInitParameter("vector_file");
		String tfidf_file = config.getInitParameter("tfidf_file");
		String model_file = config.getInitParameter("model_file");
		trainer = new SVMTrainer(model_file, vector_file, tfidf_file);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String input = request.getParameter("myTestText");
		input = new String(input.getBytes("iso-8859-1"), "utf-8");
		String callback = request.getParameter("callback");
		int flag = trainer.predict(trainer.getWordVector(input));
		String str = "";
		for (Labels s : Labels.values())
			if (flag == s.getId()) {
				str = s.getName();
				break;
			}

		String json = "{\"predict\":\" " + str + " \"}";
		out.print(callback + "(" + json + ")");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
