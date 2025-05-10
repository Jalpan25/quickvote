import React from "react";
import logo from "../assets/user.png";

const Sidebar = ({ admin, onLogout }) => {
    console.log("Admin Object:", admin);  // 🔍 Debug: Check what’s being passed
    console.log("Stored Role:", localStorage.getItem("adminRole")); // 🔍 Debug: Ensure correct value is in localStorage

    return (
        <aside className="sidebar">
            <div className="brand">
                <img src={logo} alt="Admin logo" className="userlogo" width="150" height="150" />
                <div className="user-info">
                    <p className="email">{admin.email || "Admin"}</p>
                    <p className="role">Role: {admin.role || "Loading..."}</p>
                    <button className="logout-button" onClick={onLogout}>Logout</button>
                </div>
            </div>
        </aside>
    );
};


export default Sidebar;
