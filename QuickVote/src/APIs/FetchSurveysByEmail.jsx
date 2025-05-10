import axios from "axios";

export const fetchSurveysByEmail = async (email) => {
    try {
        const response = await axios.post('http://localhost:8080/api/surveys/filter-by-email', { email });
        return response.data;
    } catch (error) {
        console.error("Error fetching surveys:", error);
        return [];
    }
};
