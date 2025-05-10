import React from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
  Legend,
  PieChart,
  Pie,
  Cell,
} from "recharts";
import { COLORS } from "../Utils/constants";

const SurveyChart = ({ question, userResponses, chartType }) => {
  const chartData = question.options.map((option, index) => ({
    name: option.optionText,
    votes: option.frequency,
    color: COLORS[index % COLORS.length],
  }));

  const userResponse = userResponses.find((res) => Number(res.questionId) === Number(question.questionId));
  const selectedOptionId = userResponse?.selectedOptionId;

  return (
    <div className="question-container">
      <h2 className="question-text">{question.questionText}</h2>

      {selectedOptionId ? (
        <p className="selected-option">
          Your selected answer:{" "}
          <strong>
            {question.options.find((opt) => Number(opt.optionId) === Number(selectedOptionId))?.optionText || "Unknown"}
          </strong>
        </p>
      ) : (
        <p className="selected-option">You did not answer this question.</p>
      )}

      <ResponsiveContainer width="100%" height={300}>
        {chartType === "bar" ? (
          <BarChart data={chartData} margin={{ top: 20, right: 30, left: 40, bottom: 10 }}>
            <CartesianGrid strokeDasharray="3 3" stroke="#ddd" />
            <XAxis dataKey="name" stroke="#666" />
            <YAxis stroke="#666" />
            <Tooltip cursor={{ fill: "rgba(200, 200, 200, 0.3)" }} />
            <Legend />
            <Bar dataKey="votes" fill="#4CAF50" barSize={30} radius={[8, 8, 0, 0]} />
          </BarChart>
        ) : (
          <PieChart>
            <Pie data={chartData} dataKey="votes" nameKey="name" outerRadius={120} label>
              {chartData.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={entry.color} stroke="#fff" strokeWidth={2} />
              ))}
            </Pie>
            <Tooltip />
          </PieChart>
        )}
      </ResponsiveContainer>
    </div>
  );
};

export default SurveyChart;
