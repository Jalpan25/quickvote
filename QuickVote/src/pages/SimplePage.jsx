import React, { useState } from 'react';
import { Home, User, Settings, Mail, Phone } from 'lucide-react';

const SimplePage = () => {
  const [activeTab, setActiveTab] = useState('home');

  const navigationTabs = [
    { id: 'home', icon: Home, label: 'Home' },
    { id: 'profile', icon: User, label: 'Profile' },
    { id: 'settings', icon: Settings, label: 'Settings' },
  ];

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Header */}
      <header className="bg-white shadow-md">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center">
          <div className="flex items-center space-x-4">
            <img 
              src="/api/placeholder/50/50" 
              alt="Logo" 
              className="h-10 w-10 rounded-full"
            />
            <h1 className="text-2xl font-bold text-gray-800">MyApp</h1>
          </div>
          <nav className="hidden md:flex space-x-4">
            <a href="#" className="text-gray-600 hover:text-blue-600 transition-colors">Home</a>
            <a href="#" className="text-gray-600 hover:text-blue-600 transition-colors">About</a>
            <a href="#" className="text-gray-600 hover:text-blue-600 transition-colors">Contact</a>
          </nav>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-grow container mx-auto px-4 py-8 grid md:grid-cols-4 gap-6">
        {/* Sidebar Navigation */}
        <aside className="md:col-span-1 bg-white rounded-xl shadow-md p-4">
          <nav className="space-y-2">
            {navigationTabs.map((tab) => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`
                  w-full flex items-center space-x-3 px-4 py-2 rounded-lg transition-colors
                  ${activeTab === tab.id 
                    ? 'bg-blue-500 text-white' 
                    : 'text-gray-600 hover:bg-blue-50 hover:text-blue-600'}
                `}
              >
                <tab.icon className="w-5 h-5" />
                <span>{tab.label}</span>
              </button>
            ))}
          </nav>
        </aside>

        {/* Content Area */}
        <div className="md:col-span-3 bg-white rounded-xl shadow-md p-6">
          {activeTab === 'home' && (
            <div>
              <h2 className="text-3xl font-bold mb-4 text-gray-800">Welcome Home</h2>
              <div className="grid md:grid-cols-3 gap-4">
                <div className="bg-blue-50 p-4 rounded-lg">
                  <h3 className="font-semibold text-blue-800 mb-2">Quick Stats</h3>
                  <p className="text-blue-600">Total Users: 1,234</p>
                  <p className="text-blue-600">Active Projects: 56</p>
                </div>
                <div className="bg-green-50 p-4 rounded-lg">
                  <h3 className="font-semibold text-green-800 mb-2">Recent Activity</h3>
                  <ul className="space-y-1">
                    <li className="text-green-600">New project created</li>
                    <li className="text-green-600">3 updates completed</li>
                  </ul>
                </div>
                <div className="bg-purple-50 p-4 rounded-lg">
                  <h3 className="font-semibold text-purple-800 mb-2">Notifications</h3>
                  <p className="text-purple-600">2 unread messages</p>
                  <p className="text-purple-600">1 pending invite</p>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'profile' && (
            <div>
              <h2 className="text-3xl font-bold mb-6 text-gray-800">User Profile</h2>
              <div className="flex items-center space-x-6 mb-6">
                <img 
                  src="/api/placeholder/150/150" 
                  alt="Profile" 
                  className="w-36 h-36 rounded-full object-cover"
                />
                <div>
                  <h3 className="text-2xl font-semibold text-gray-700">John Doe</h3>
                  <p className="text-gray-500">Software Developer</p>
                  <div className="mt-4 flex space-x-4">
                    <a href="#" className="text-blue-600 hover:text-blue-800 flex items-center">
                      <Mail className="mr-2 w-5 h-5" /> Email
                    </a>
                    <a href="#" className="text-green-600 hover:text-green-800 flex items-center">
                      <Phone className="mr-2 w-5 h-5" /> Contact
                    </a>
                  </div>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'settings' && (
            <div>
              <h2 className="text-3xl font-bold mb-6 text-gray-800">Settings</h2>
              <div className="space-y-4">
                <div className="bg-gray-50 p-4 rounded-lg">
                  <h3 className="font-semibold text-gray-700 mb-2">Account Preferences</h3>
                  <div className="flex items-center justify-between">
                    <span>Dark Mode</span>
                    <label className="flex items-center cursor-pointer">
                      <div className="relative">
                        <input type="checkbox" className="sr-only" />
                        <div className="w-10 h-4 bg-gray-400 rounded-full shadow-inner"></div>
                        <div className="dot absolute -left-1 -top-1 bg-white w-6 h-6 rounded-full shadow transition"></div>
                      </div>
                    </label>
                  </div>
                </div>
                <div className="bg-gray-50 p-4 rounded-lg">
                  <h3 className="font-semibold text-gray-700 mb-2">Notifications</h3>
                  <div className="space-y-2">
                    <label className="flex items-center">
                      <input 
                        type="checkbox" 
                        className="form-checkbox h-4 w-4 text-blue-600 mr-2"
                      />
                      <span>Email Notifications</span>
                    </label>
                    <label className="flex items-center">
                      <input 
                        type="checkbox" 
                        className="form-checkbox h-4 w-4 text-blue-600 mr-2"
                      />
                      <span>Push Notifications</span>
                    </label>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-white shadow-md">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 text-center">
          <p className="text-gray-600">© 2024 MyApp. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
};

export default SimplePage;