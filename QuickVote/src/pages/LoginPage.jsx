
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { loginAdmin } from "../APIs/adminAPI";
import Loginfig from "../Assets/logingif1.png"
import { jwtDecode } from "jwt-decode";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
  e.preventDefault();
  try {
    const response = await loginAdmin(email, password);

    if (response.status === "success") {
      const token = response.token;
      localStorage.setItem("token", token); // Store JWT

      const decoded = jwtDecode(token);
      const role = decoded.role;

      // Navigate based on role
      if (role === "admin") {
        navigate("/admindashboard");
      } else if (role === "superAdmin") {
        navigate("/superadmindashboard");
      } else {
        setError("Unauthorized role");
      }
    } else {
      setError(response.message || "Invalid credentials.");
    }
  } catch (error) {
    setError("Something went wrong. Please try again.");
  }
};

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50 flex justify-center items-center px-4">
      <div className="flex bg-white rounded-3xl shadow-xl overflow-hidden w-full max-w-4xl">
        {/* Left Side - Form */}
        <div className="w-1/2 p-10">
          <h1 className="text-3xl font-extrabold text-blue-700 mb-4 text-center">
            LOGIN
          </h1>
          <p className="text-center text-gray-500 mb-6">
            Login to access your dashboard.
          </p>

          {/* Error Message */}
          {error && (
            <p className="text-red-500 bg-red-100 border-l-4 border-red-500 text-sm p-2 rounded-md mb-4">
              {error}
            </p>
          )}

          <form onSubmit={handleLogin} className="space-y-4">
            {/* Email Input */}
            <div className="relative">
              <input
                type="email"
                id="email"
                placeholder="Your email"
                className="w-full p-3 pl-10 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <span className="absolute left-3 top-3.5 text-gray-400">
                📧
              </span>
            </div>

            {/* Password Input */}
            <div className="relative">
              <input
                type="password"
                id="password"
                placeholder="Password"
                className="w-full p-3 pl-10 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              <span className="absolute left-3 top-3.5 text-gray-400">
                🔒
              </span>
            </div>

            {/* Forgot Password & Register Links */}
            <div className="flex justify-between items-center text-sm text-gray-600">
              
              <Link to="/register" className="hover:text-blue-500 transition">
                Not a user? Register now
              </Link>
            </div>

            {/* Login Button */}
            <button
              type="submit"
              className="w-full bg-gradient-to-r from-blue-500 to-purple-500 text-white font-bold py-3 rounded-xl hover:from-blue-600 hover:to-purple-600 transition-all duration-300"
            >
              Login Now
            </button>
          </form>

        </div>

        {/* Right Side - GIF/Animation */}
        <div className="w-1/2 bg-gradient-to-r from-blue-500 to-purple-500 flex justify-center items-center">
          <div className="relative w-3/4">
            <img
              src={Loginfig}
              alt="Person"
              className="rounded-2xl shadow-lg"
            />
            {/* Floating Elements */}
            <div className="absolute -top-4 -left-4 w-8 h-8 bg-yellow-400 rounded-full shadow-md animate-pulse"></div>
            <div className="absolute -bottom-4 -right-4 w-10 h-10 bg-white rounded-full shadow-md"></div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
