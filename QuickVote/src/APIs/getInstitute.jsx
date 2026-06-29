import axios from "axios";

import { API_BASE_URL } from "./config";

const getInstitute = async () => {
  const baseUrl = API_BASE_URL.replace(/\/api$/, "");

  const response = await axios.get(`${baseUrl}/auth/getInstitute`);

  // Clean and transform the data
  const cleaned = response.data
    .filter(name => name.trim() !== "")
    .map((name, index) => ({
      id: index,
      label: name,
      value: name.toLowerCase()
    }));

  return cleaned;
};

export default getInstitute;