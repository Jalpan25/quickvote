import { API_BASE_URL } from "./config";

export const loginAdmin = async (email, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/admins/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Login API error:", error);
    throw new Error("Failed to connect to the server.");
  }
};
