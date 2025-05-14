import axios from "axios";

// Helper function to get JWT token from localStorage
const getToken = () => {
  return localStorage.getItem("token");
};

export const fetchSurveysByEmail = async (email) => {
  const token = getToken();
  if (!token) {
    console.error("JWT token not found in localStorage.");
    return [];
  }

  try {
    const response = await axios.post(
      'http://localhost:8080/api/surveys/filter-by-email',
      { email },
      {
        headers: {
          Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
          'Content-Type': 'application/json', // Assuming you are sending JSON data
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching surveys:", error);
    // You might want to handle different error scenarios here,
    // such as unauthorized access (401), etc.
    return [];
  }
};