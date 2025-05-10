import React from 'react';

const Header = () => {
  return (
    <header className="bg-gradient-to-r from-blue-700 to-purple-700 shadow-md fixed w-full z-50">
      {/* Sparkling Effect Overlay */}
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        {[...Array(6)].map((_, index) => (
          <div
            key={index}
            className="absolute w-[4px] h-[4px] bg-white opacity-60 rounded-full animate-ping"
            style={{
              top: `${Math.random() * 100}%`,
              left: `${Math.random() * 100}%`,
              animationDelay: `${Math.random() * 2}s`,
              animationDuration: `${1 + Math.random()}s`,
            }}
          />
        ))}
      </div>

      {/* Navigation */}
      <nav className="container mx-auto px-6 py-4 relative z-10">
        <ul className="flex justify-between items-center">
          {/* Logo */}
          <li>
            <a
              href="/"
              className="text-2xl font-extrabold text-white hover:text-blue-200 transition-all duration-300"
            >
              QuickVote
            </a>
          </li>

          {/* Navigation Links */}
          <div className="flex space-x-8">
            <li>
              <a
                href="#how-it-works"
                className="text-white font-semibold hover:text-[#FFD700] transition-all duration-300 hover:scale-110"
              >
                How it Works
              </a>
            </li>
            <li>
              <a
                href="#why-choose-us"
                className="text-white font-semibold hover:text-[#FFD700] transition-all duration-300 hover:scale-110"
              >
                Why Choose Us
              </a>
            </li>
            <li>
              <a
                href="/login"
                className="px-6 py-2 bg-blue-600 text-white font-semibold rounded-xl hover:bg-blue-700 transition-all duration-300 transform hover:scale-105 shadow-lg"
              >
                Login
              </a>
            </li>
          </div>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
