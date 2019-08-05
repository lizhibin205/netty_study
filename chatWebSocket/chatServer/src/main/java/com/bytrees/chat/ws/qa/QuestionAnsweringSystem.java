package com.bytrees.chat.ws.qa;

import java.util.List;

import org.apdplat.qa.SharedQuestionAnsweringSystem;
import org.apdplat.qa.model.CandidateAnswer;
import org.apdplat.qa.model.Question;

public class QuestionAnsweringSystem {
	private static final String NO_ANSWER = "No answer for this question.";
	private QuestionAnsweringSystem() {}

	/**
	 * 输入问题，获取答案
	 * @param question
	 * @return
	 */
	public static String answer(final String question) {
		Question questionModel = SharedQuestionAnsweringSystem.getInstance().answerQuestion(question);
		if (questionModel == null) {
			return NO_ANSWER;
		}
		List<CandidateAnswer> candidateAnswers = questionModel.getTopNCandidateAnswer(1);
		if (candidateAnswers.isEmpty()) {
			return NO_ANSWER;
		}
		return candidateAnswers.get(0).getAnswer();
	}
}
