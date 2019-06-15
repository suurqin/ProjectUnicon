package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;

/**
 * Created by bobo on 2017/12/18.
 */

public class FuncDetailResult extends Result {

    private FuncDetailBean data;

    public FuncDetailBean getData() {
        return data;
    }

    public void setData(FuncDetailBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FuncDetailResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class FuncDetailBean implements Serializable {
        /**
         * "answer": "好酷比",
         * "question": "开发程序"
         */
        private String answer;
        private String question;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        @Override
        public String toString() {
            return "FuncDetailBean{" +
                    "answer='" + answer + '\'' +
                    ", question='" + question + '\'' +
                    '}';
        }
    }
}
